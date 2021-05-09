<h1 align="center">台北動物園館區-植物資料 </h1>

<p align="center">
  <a target="_blank" href="https://www.paypal.me/RX1226" title="Donate using PayPal"><img src="https://img.shields.io/badge/paypal-donate-yellow.svg" /></a>
</p>


## 簡介
1. 已公開資料**臺北市立動物園_館區簡介**與**臺北市立動物園_植物資料**做練習
```
臺北市立動物園_館區簡介
https://data.taipei/#/dataset/detail?id=1ed45a8a-d26a-4a5f-b544-788a4071eea2
臺北市立動物園_植物資料
https://data.taipei/#/dataset/detail?id=48c4d6a7-4b09-4d1f-9739-ee837d302bd1
```
2. 由於資料是每季與每年更新, 故採用下載csv的方式, 未來在新增網路下載API查詢
```
臺北市立動物園_館區簡介csv
https://data.taipei/api/getDatasetInfo/downloadResource?id=1ed45a8a-d26a-4a5f-b544-788a4071eea2&rid=5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a
臺北市立動物園_植物資料csv
https://data.taipei/api/getDatasetInfo/downloadResource?id=48c4d6a7-4b09-4d1f-9739-ee837d302bd1&rid=f18de02f-b6c9-47c0-8cda-50efad621c14
```

3. 由於資料內圖片網址CA中path2有問題, 導致web與iOS環境可信任, Android不行的狀況
```
CA檢測方法可以藉由一些網站來測試, 像是: https://www.ssllabs.com/ssltest/ 等等
可以看出www.zoo.gov.tw是由中華電信所簽訂, path1正常, path2有誤
一般處理方法有
1. 寫信給該公司，請對方檢查更信憑證，根本性的解決
2. 因種種因素，在可以確認對方是安全無慮下，手動添加信任
此案因憑證是中華電信所發，確認無安全疑慮下採用方法2來處理
```
4. App內用到的icon皆合法, 請放心使用

```
icon產生器
https://romannurik.github.io/AndroidAssetStudio/index.html
Google icon
https://fonts.google.com/icons?selected=Material+Icons
```



## 架構簡介

使用Kotlin並使用MVC架構, 所用的第三方Lib皆有確認授權方式

1. 採用Activity內用Fragment的方式呈現, 未來會導入Navigation方式切換Activity

```
    fun openHousePage() {
        supportFragmentManager.beginTransaction().replace(
            R.id.content,
            HouseFragment.newInstance()
        ).commit()
    }
```
2. 資料採用opencsv與傳統方式來處理csv資料

```
    private fun readHouseData(){ //use read line and split to read
        val houseData = arrayListOf<House>()
        val inputStreamReader = InputStreamReader(
            assets.open("館區介紹_20210326.csv"), StandardCharsets.UTF_8
        )
        val reader = BufferedReader(inputStreamReader)
        reader.readLine()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
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
                    result?.get(7)  // E_URL
                )
            ) 
        }
        viewModel.houseData.value = houseData)
        inputStreamReader.close()
        reader.close()
    }
```
3. 用ViewModel的方式溝通Activity與Fragment資料, 並用LiveData更新

```
class MainViewModel : ViewModel() {
    var currentTitle: MutableLiveData<String> = MutableLiveData()
    var houseData: MutableLiveData<MutableList<House>> = MutableLiveData()
    var plantData: MutableLiveData<MutableList<Plant>> = MutableLiveData()
}
```
4. 用xml方式添加信任憑證

```
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config>
<!-- https://www.ssllabs.com/ssltest/analyze.html-->
<!-- www.zoo.gov.tw 的CA是中華電信簽的 path1合法但path2有問題 , web與ios皆可過, 只有android不行-->
<!-- 判斷無安全議題故於中華電信更新前先加入信任-->
        <domain includeSubdomains="true">www.zoo.gov.tw</domain>
        <trust-anchors>
            <certificates src="@raw/zoo"/>
        </trust-anchors>
    </domain-config>
</network-security-config>
```
5. 以Glide來下載網路圖片

```
 context?.let { Glide.with(it).load(plant?.pic1Url).into(binding.img) }
```

## License
	Copyright 2021 RX1226
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	   http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.