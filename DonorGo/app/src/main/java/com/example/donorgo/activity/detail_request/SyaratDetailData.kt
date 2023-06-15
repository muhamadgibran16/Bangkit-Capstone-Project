package com.example.donorgo.activity.detail_request

object SyaratDetailData {
    private val syaratTitle = arrayOf(
        "Requirements for blood nonation",
        "Do not donate blood if"

    )

    private val syaratDesc = arrayOf(
        "• Physical condition must be in good health, both physically and mentally.\\n• Aged 17-60 years old.\\n• Body weight at least 45 kilograms.\\n• No infectious disease\\n• No recent surgery\\n• No recent vaccination.\\n• Frequency of blood donation maximum five times a year or at least three months apart.",

        "Having heart and lung disease; suffering from cancer; suffering from high blood pressure (hypertension); suffering from diabetes (diabetes militus); having a tendency to abnormal bleeding or other blood disorders; suffering from epilepsy and frequent seizures; suffering or have suffered from hepatitis B or C; having syphilis; drug dependence; addicted to alcoholic beverages; having or high risk of HIV/AIDS; doctors recommend not to donate blood for health reasons."
    )


    val listData: ArrayList<SyaratDetail>
        get() {
            val list = arrayListOf<SyaratDetail>()
            for (position in syaratTitle.indices) {
                val syarat = SyaratDetail()
                syarat.title = syaratTitle[position]
                syarat.desc = syaratDesc[position]
                list.add(syarat)
            }
            return list
        }
}