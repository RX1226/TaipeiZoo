package com.rx.application.ui.fragment

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
import com.rx.application.viewmodel.MainViewModel
import com.rx.application.R
import com.rx.application.databinding.FragmentHouseBinding
import com.rx.application.model.House

class HouseFragment : Fragment() {
    private lateinit var binding : FragmentHouseBinding
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = HouseFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_house, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)
        viewModel.currentTitle.postValue(getString(R.string.title_house))
        viewModel.houseData.observe(viewLifecycleOwner, Observer {
            viewModel.houseData.value?.let { (binding.recycler.adapter as Adapter).updateData(it) }
            Log.d("TAG", viewModel.houseData.value.toString())
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        var datas : MutableList<House> = mutableListOf()
        fun updateData(datas: MutableList<House>){
            this.datas = datas
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_house,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = datas.get(position)
            Glide.with(activity).load(data.pic).into(holder.img)
            holder.txt_name.text = data.name
            holder.txt_desc.text = data.info
            holder.txt_time.text = data.memo
            holder.btn_forward.setOnClickListener {
                (activity as MainActivity?)?.openHouseDetailPage(data)
            }
        }

        override fun getItemCount(): Int {
            return datas.size
        }

        private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val img : AppCompatImageView = itemView.findViewById(R.id.img)
            val txt_name : TextView = itemView.findViewById(R.id.txt_name)
            val txt_desc : TextView = itemView.findViewById(R.id.txt_desc)
            val txt_time : TextView = itemView.findViewById(R.id.txt_time)
            val btn_forward : AppCompatImageView = itemView.findViewById(R.id.btn_forward)
            init {
                txt_name.isSelected = true
            }
        }
    }
}