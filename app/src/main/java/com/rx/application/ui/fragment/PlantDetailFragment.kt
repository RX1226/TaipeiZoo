package com.rx.application.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rx.application.MainActivity
import com.rx.application.viewmodel.MainViewModel
import com.rx.application.R
import com.rx.application.databinding.FragmentPlantDetailBinding
import com.rx.application.model.Plant
import java.lang.StringBuilder

private const val PLANT = "plant"

class PlantDetailFragment : Fragment() {
    private lateinit var binding : FragmentPlantDetailBinding
    private lateinit var viewModel: MainViewModel
    private var plant: Plant? = null

    companion object {
        @JvmStatic
        fun newInstance(plant: Plant) =
            PlantDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PLANT, plant)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            plant = it.getParcelable(PLANT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_plant_detail,
            container,
            false
        )
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)
        viewModel.currentTitle.postValue(plant?.name)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { Glide.with(it).load(plant?.pic1Url).into(binding.img) }
        binding.txtName.text = plant?.name
        binding.txtNameEnglish.text = plant?.nameEnglish
        binding.txtAlias.text = plant?.alias
        binding.txtLocation.text = plant?.location
        binding.txtRecognizeMethod.text = plant?.feature
        binding.txtFunction.text = plant?.usefulness
        binding.txtLastUpdate.text = StringBuilder().append(getString(R.string.last_update)).append(plant?.lastUpdate)
    }
}