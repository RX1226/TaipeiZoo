package com.rx.application

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.opencsv.CSVReader
import com.rx.application.databinding.ActivityMainBinding
import com.rx.application.model.House
import com.rx.application.model.Plant
import com.rx.application.ui.fragment.HouseDetailFragment
import com.rx.application.ui.fragment.HouseFragment
import com.rx.application.ui.fragment.PlantDetailFragment
import com.rx.application.viewmodel.MainViewModel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.include.imgMenu.setOnClickListener{onBackPressed()}

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.currentTitle.observe(this, {
            binding.include.txtTitle.text = it
            when(it){
                getString(R.string.title_house) -> {
                    binding.include.imgMenu.visibility = View.GONE
                }
                else -> {
                    binding.include.imgMenu.setImageResource(R.drawable.ic_back)
                    binding.include.imgMenu.visibility = View.VISIBLE
                }
            }
        })
        Thread(){
            val houseData = readHouseData()
            runOnUiThread {
                viewModel.houseData.value = houseData
            }
            val plantData = readPlantData()
            runOnUiThread {
                viewModel.plantData.value = plantData
            }
        }.start()

        openHousePage()
    }

    private fun readHouseData() : MutableList<House> { //use read line and split to read
        val houseData = arrayListOf<House>()
        val inputStreamReader = InputStreamReader(
            assets.open("館區介紹_20210326.csv"), StandardCharsets.UTF_8
        )
        val reader = BufferedReader(inputStreamReader)
        reader.readLine()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
//            line?.let { Log.d("TAG", it) }
            val result = line?.split(",")?.toTypedArray()
            houseData.add(
                House(
                    result?.get(0)?.toInt(), // E_no
                    result?.get(1), // E_Category
                    result?.get(2), // E_Name
                    result?.get(3), // E_Pic_URL
                    result?.get(4), // E_Info
                    result?.get(5), // E_Memo
                    result?.get(6), // E_Geo
                    result?.get(7) // E_URL
                )
            )
        }
//        Log.d("TAG", viewModel.houseData.value.toString())
        inputStreamReader.close()
        reader.close()
        return houseData;
    }

    private fun readPlantData() : MutableList<Plant>{ //use open csv to read
        val plantData = arrayListOf<Plant>()
        val reader = CSVReader(InputStreamReader(assets.open("用+植物資料+20170905+加座標檔_新增10種熱雨館植物資料1090818.csv")))

        val rows = reader.readAll()
        for (row in rows.stream().skip(1)) { //跳過第一筆標題
            plantData.add(
                Plant(
                    row[0], // F_Name_Ch
                    row[3], // F_AlsoKnown
                    row[4], // F_Geo
                    row[5], // F_Location
                    row[6], // F_Name_En
                    row[7], // F_Name_Latin
                    row[8], // F_Family
                    row[9], // F_Genus
                    row[10], // F_Brief
                    row[11], // F_Feature
                    row[12], // F_Function&Application
                    row[14], // F_Pic01_ALT
                    row[15], // F_Pic01_URL
                    row[16], // F_Pic02_ALT
                    row[17], // F_Pic02_URL
                    row[33] //F_Update
                )
            )
        }
//        Log.d("TAG", viewModel.plantData.value.toString())
        reader.close()
        return plantData
    }

    fun openHousePage() {
        supportFragmentManager.beginTransaction().replace(
            R.id.content,
            HouseFragment.newInstance()
        ).commit()
    }

    fun openHouseDetailPage(house: House) {
        supportFragmentManager.beginTransaction().replace(
            R.id.content,
            HouseDetailFragment.newInstance(house)
        ).addToBackStack(house.name).commit()
    }

    fun openPlantDetailPage(plant: Plant) {
        supportFragmentManager.beginTransaction().add(
            R.id.content,
            PlantDetailFragment.newInstance(plant)
        ).addToBackStack(plant.name).commit()
    }
}