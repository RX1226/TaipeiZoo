package com.rx.application.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.rx.application.R
import com.rx.application.databinding.FragmentPlantDetailBinding
import com.rx.application.model.Plant
import java.lang.StringBuilder

private const val PLANT = "plant"

class PlantDetailFragment : Fragment() {
    private lateinit var binding : FragmentPlantDetailBinding
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { Glide.with(it).load(plant?.pic1Url).into(binding.img) }
        binding.txtName.setText(plant?.name)
        binding.txtNameEnglish.setText(plant?.nameEnglish)
        binding.txtAlias.setText(plant?.alias)
        binding.txtLocation.setText(plant?.location)
        binding.txtRecognizeMethod.setText(plant?.feature)
        binding.txtFunction.setText(plant?.usefulness)
        binding.txtLastUpdate.setText(StringBuilder().append(getString(R.string.last_update)).append(plant?.lastUpdate))
    }
}