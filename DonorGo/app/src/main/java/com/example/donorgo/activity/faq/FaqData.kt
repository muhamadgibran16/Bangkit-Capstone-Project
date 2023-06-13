package com.example.donorgo.activity.faq

object FaqData {
    private val faqTitle = arrayOf(
        "Why do we need to donate blood?",
        "What should we prepare before donating?",
        "Requirements for blood nonation",
        "Do not donate blood if",
        "Is it healthy to donate blood?",
        "When to delay donating blood?",
        "What is platelet donor apheresis?",
        "What are platelets called?",
        "What is Rhesus?",
        "What is the importance of knowing your blood Rhesus?",
        "What is Rhesus Negative?",
        "What happens if you do not know your blood",
        "About Humanity"
    )

    private val faqDesc = arrayOf(
        "The need is great: Every eight seconds, there is one person who needs a blood transfusion in Indonesia.\n" +
                "Free health check: Before donating blood, the officer will check your body temperature, pulse, blood pressure and hemoglobin level.\n" +
                "Painless: Yes you will feel pain. However, the pain is minor and will go away quickly.",

        "We need a good night's sleep the night before donating, breakfast or lunch before donating. Drink plenty of juice and milk before donating. Relax while donating, and drink a lot after donating. We can continue our activities after donating, just avoid strenuous physical activity.",

        "• Physical condition must be in good health, both physically and mentally.\\n• Aged 17-60 years old.\\n• Body weight at least 45 kilograms.\\n• No infectious disease\\n• No recent surgery\\n• No recent vaccination.\\n• Frequency of blood donation maximum five times a year or at least three months apart.",

        "Having heart and lung disease; suffering from cancer; suffering from high blood pressure (hypertension); suffering from diabetes (diabetes militus); having a tendency to abnormal bleeding or other blood disorders; suffering from epilepsy and frequent seizures; suffering or have suffered from hepatitis B or C; having syphilis; drug dependence; addicted to alcoholic beverages; having or high risk of HIV/AIDS; doctors recommend not to donate blood for health reasons.",

        "Yes, when a person donates blood, his or her body replaces the blood volume within 48 hours after the donation, and all lost red blood cells are completely replaced within four to eight weeks with new red blood cells. The process of forming new red blood cells will help the body stay healthy and work more efficiently and productively.",

        "When sick with fever or influenza, wait 1 week after recovery; after tooth extraction, wait 5 days after recovery; after minor surgery, wait 6 months; after major surgery, wait 1 year; after transfusion, wait 1 year; after tattooing, piercing, needling, and transplantation, wait 1 year; if in close contact with a hepatitis patient, wait 1 year; if pregnant, wait 6 after giving birth; if breastfeeding, wait 3 months after stopping breastfeeding. after being sick with malaria, wait 3 years after being free of malaria symptoms; after visiting from a malaria endemic area, wait 1 year; if living in a malaria endemic area for 5 consecutive years, wait 3 years after leaving the area; if sick with typhoid, wait 6 months after recovery; after vaccines, wait 8 weeks; if there are allergic symptoms, wait 1 week after recovery; if there is a skin infection in the area to be pierced, wait 1 week after recovery.",

        "Apheresis adalah metode baru yang aman dalam pengkoleksian komponen darah, mudah dan lebih efektif daripada cara konvensional. Dalam donasi darah pada metode apheresis, hanya komponen darah tertentu yang dikoleksi (seperti trombosit, plasma atau sel darah merah) sehingga komponen yang tidak diperlukan dikembalikan kedalam tubuh pendonor. Satu kantong donor trombosit apheresis setara dengan 6 - 10 kantong donor trombosit biasa. Sebuah transfusi apheresis dari donor tunggal sangat baik mengurangi resiko terjadinya reaksi sistem imun pada proses transfusi dan juga mengurangi resiko terinfeksinya bakteri karena hanya menerima darah dari donor tunggal sedangkan cara konvensional berasal dari banyak pendonor.",

        "Platelets are components in the blood that play an important role in blood clotting. (Often called platelets).",

        "Rhesus is a blood classification system in the form of POSITIVE / NEGATIVE after A / B / AB / O which we generally know.",

        "Rhesus Negative is a rare blood. Rhesus negative blood voters are only 15% in the world and Indonesia only has 1% of them",

        "The importance of knowing rhesus is for blood transfusion activities, with the small number of negative rhesus making obstacles in maintaining blood stock availability. In the case of rhesus negative blood requests, both the donor and the patient are rhesus negative.",

        "If your blood is rhesus negative and you don't know it, if there is an emergency and you need a blood transfusion, it will be difficult to find volunteers with negative rhesus.",

        "Purely helping selflessly and not buying and selling blood. It is expected that the family does not give money or rewards to the donor. By helping each other, hopefully it can establish friendship and add to the brotherhood."
    )


    val listData: ArrayList<Faq>
        get() {
            val list = arrayListOf<Faq>()
            for (position in faqTitle.indices) {
                val faq = Faq()
                faq.title = faqTitle[position]
                faq.desc = faqDesc[position]
                list.add(faq)
            }
            return list
        }
}