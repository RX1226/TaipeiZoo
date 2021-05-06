package com.rx.application.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.rx.application.MainActivity
import com.rx.application.MainViewModel
import com.rx.application.R
import com.rx.application.databinding.FragmentHouseDetailBinding
import com.rx.application.model.House

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_house_detail, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)
//        viewModel.houseData.observe(viewLifecycleOwner, Observer {
//            viewModel.houseData.value?.let { (binding.recycler.adapter as HouseFragment.Adapter).updateData(it) }
//            Log.d("TAG", viewModel.houseData.value.toString())
//        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.recycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
//        binding.recycler.adapter = activity?.let { HouseFragment.Adapter(it) }
//        binding.recycler.setHasFixedSize(true)
//        binding.recycler.setItemAnimator(DefaultItemAnimator())
//        binding.recycler.addItemDecoration(
//            DividerItemDecoration(
//                binding.recycler.context,
//                RecyclerView.VERTICAL
//            )
//        )
    }

}