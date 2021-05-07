package com.rx.application.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rx.application.MainActivity
import com.rx.application.MainViewModel
import com.rx.application.R
import com.rx.application.databinding.FragmentHouseDetailBinding
import com.rx.application.model.House
import com.rx.application.model.Plant


private const val HOUSE = "HOUSE"

class HouseDetailFragment : Fragment() {
    private lateinit var binding : FragmentHouseDetailBinding
    private lateinit var viewModel: MainViewModel
    private var house: House? = null

    companion object {
        @JvmStatic
        fun newInstance(house: House) =
            HouseDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(HOUSE, house)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            house = it.getParcelable(HOUSE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_house_detail,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)
        viewModel.plantData.observe(viewLifecycleOwner, Observer {
            val plantInHouse = arrayListOf<Plant>()
            for(plan in viewModel.plantData.value!!){
                if(plan.location?.contains(house?.name.toString()) == true){
                    plantInHouse.add(plan)
                }
            }
            plantInHouse.let { (
                    binding.recycler.adapter as Adapter).updateData(it) }
            Log.d("TAG", viewModel.plantData.value.toString())
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { Glide.with(it).load(house?.pic).into(binding.img) }
        binding.txtDesc.setText(house?.info)
        binding.txtTime.setText(house?.memo)
        binding.txtHouse.setText(house?.name)
        binding.txtHref.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(house?.url)))
        }
        binding.recycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.recycler.adapter = activity?.let { Adapter(it) }
        binding.recycler.setHasFixedSize(true)
        binding.recycler.setItemAnimator(DefaultItemAnimator())
        binding.recycler.addItemDecoration(
            DividerItemDecoration(
                binding.recycler.context,
                RecyclerView.VERTICAL
            )
        )
    }

    private class Adapter(private val activity: FragmentActivity) : RecyclerView.Adapter<Adapter.ViewHolder>() {
        var datas : MutableList<Plant> = mutableListOf()
        fun updateData(datas: MutableList<Plant>){
            this.datas = datas
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_house_detail,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = datas.get(position)
            Glide.with(activity).load(data.pic1Url).into(holder.img)
            holder.txt_name.setText(data.name)
            holder.txt_desc.setText(data.alias)
            holder.btn_forward.setOnClickListener {
                (activity as MainActivity?)?.openPlantDetailPage(data)
            }
        }

        override fun getItemCount(): Int {
            return datas.size
        }

        private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val img : AppCompatImageView = itemView.findViewById(R.id.img)
            val txt_name : TextView = itemView.findViewById(R.id.txt_name)
            val txt_desc : TextView = itemView.findViewById(R.id.txt_desc)
            val btn_forward : AppCompatImageView = itemView.findViewById(R.id.btn_forward)
            init {
                txt_name.isSelected = true
            }
        }
    }
}