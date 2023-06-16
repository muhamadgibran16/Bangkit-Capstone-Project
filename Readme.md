# MOBILE DEVELOPMENT
The MD section is responsible for creating a functional android application. The DonorGo app will have 5 main menus. These menus are Home, Search Requests, Events, Articles, and profile.

On the home page, there will be 5 main features namely, Stock, Blood match table, Blood Donor, Create Donor Request and FaQ (Frequently Asked Questions). On the stock page, users will see information on the type of blood type and its rhesus along with the number of blood bag stocks available at a hospital. On the blood donor page, there will be an option to Look for Blood Donor Request or donate blood for the hospital.

On the profile page, there is an ID card scan menu to complete personal data, a history of blood donor requests to manage if the request has been fulfilled, an about us menu which includes information about the development team and the parties who are stakeholders in this application.

### Home
- Voluntary Blood Donation
- Upload ID Card
- Profile
- Edit Profile
- Stock
- Donor by Request
- Blood Request
- News
- Events
- Webview
- FAQ
- Request History
- Table (displayed as a pop-up image)

### LOGO
To align with the purpose of our app, we created a logo that involves drops of blood.
![logon and icon](https://github.com/muhamadgibran16/Bangkit-Capstone-Project/assets/91604932/4c1dc42d-1100-4980-8f80-46f7739a786a)
Deliverable Logo
So that the logo has its own meaning, starting from the blood droplets that are bound with a curved line that follows the shape of the blood droplets which means the Symbolism of life, that red blood droplets can symbolize life, energy, and vitality. The rope that binds the blood drops symbolizes the Symbolism of sacrifice where a strong bond or commitment to certain values or goals may require sacrifice or struggle. It also has a symbolic meaning of solidarity or unity which symbolizes diversity, unity, and complementarity.

### User Experinces
To ensure that users have the best experience when using our application, I designed user flows and use cases. There are two actors in the DonorGo system: blood donors and individuals in need of blood donation. Blood donors are volunteers who are willing to donate their blood, while individuals in need of blood donation are those who require blood donation.

For more details, please click on this Figma link. [FIGMA DONORGO](https://www.figma.com/file/XGrvSqCM5Gk5rqVHZTAOwp/Donor-Go---Blood-Donation-Bangkit-Capstone-Project?type=design&node-id=0%3A1&t=R5oENuinMlfvXMHA-1)

### USER INTERFACE
For this part, most of the work I did in [FIGMA](https://www.figma.com/file/XGrvSqCM5Gk5rqVHZTAOwp/Donor-Go---Blood-Donation-Bangkit-Capstone-Project?type=design&node-id=0%3A1&t=R5oENuinMlfvXMHA-1), so please check the link for fashion details. Below you can see a preview of the design I first created.
Next I move on to the design, which is the centerpiece of this application
Blood Request Form            Request Details	  	        Find Request
![request page](https://github.com/muhamadgibran16/Bangkit-Capstone-Project/assets/91604932/e265b51b-c89a-4531-aa3d-a21ef46db48e)
Stock			   Donate Blood From
![stok dan donasi](https://github.com/muhamadgibran16/Bangkit-Capstone-Project/assets/91604932/9a39be12-858a-437b-b350-a5114b973c06)

### RESOURCES
Here are all the resources I used in our application. For assets that I didn't create myself, I've attached below the links where I got them. 
Component Photo
- https://www.svgrepo.com/unsplash.com
- https://fontawesome.com/
- https://www.flaticon.com/

### DATA
In our application, we need some data such as FaQ data, funfacts data about blood donation in general, blood requirements along with patient data which is our case study. Here is the link where I got the data:
Sample Patient Data
- https://web.facebook.com/groups/195327283847883

Blood Donor Requirements
- https://ayodonor.pmi.or.id/
- https://www.bola.com/ragam/read/5123411/macam-macam-hal-yang-harus-diperhatikan-sebelum-donor-darah
- https://www.cnnindonesia.com/gaya-hidup/20220614150204-260-808810/5-persiapan-wajib-sebelum-kamu-donor-darah
- https://www.halodoc.com/artikel/7-syarat-umum-yang-harus-dipenuhi-sebelum-donor-darah

Blood Type Compatibility Table
- https://www.cedars-sinai.org/programs/blood-donor-services/about-donation.html
- https://www.blood.ca/en/stories/blood-type-compatibility-which-blood-types-are-compatible-each-other

Fun Facts
- https://ayodonor.pmi.or.id/
- https://www.rhesusnegatif.com/index.php

FAQ
- https://www.rhesusnegatif.com/index.php
- https://ayodonor.pmi.or.id/?page=faq

APPLICATION DEVELOPMENT
For application development, I used Android Studio as the IDE and wrote all the code in Kotlin. You can directly check the commits I made in this repository for all the processes I went through in creating this application.

Dependencies
Here are all the dependencies I used in the DonorGo application:
```
implementation 'androidx.core:core-ktx:1.10.1'
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.9.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
implementation 'com.google.firebase:firebase-auth-ktx:22.0.0'
implementation 'androidx.navigation:navigation-fragment-ktx:2.5.3'
implementation 'androidx.navigation:navigation-ui-ktx:2.5.3'
testImplementation 'junit:junit:4.13.2'
androidTestImplementation 'androidx.test.ext:junit:1.1.5'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
```

Image
These are four libraries I used as image loaders.
```
implementation 'de.hdodenhof:circleimageview:3.1.0'
implementation 'com.github.bumptech.glide:glide:4.15.1'
androidTestImplementation project(path: ':app')
kapt 'com.github.bumptech.glide:compiler:4.15.1'
```
DataStore
```
implementation 'androidx.datastore:datastore-preferences:1.0.0'
```
Coroutines
```
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1'
``` 
Android lifecycle
```
implementation 'androidx.activity:activity-ktx:1.7.2'
implementation 'androidx.fragment:fragment-ktx:1.5.7'
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1'
implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.6.1'
```    
Room
After a user successfully logs in, the user data, fun fact data, and FAQ data will be saved in a Room database. This way, the application doesn't have to fetch the data from Firebase every time it is needed.
```
implementation 'androidx.room:room-ktx:2.5.1'
kapt 'androidx.room:room-compiler:2.5.1'
```
O-Auth
```
implementation 'com.google.android.gms:play-services-auth:20.5.0'
implementation 'com.google.firebase:firebase-auth-ktx:22.0.0'
```
Google Play Service Location
```
implementation 'com.google.android.gms:play-services-maps:18.1.0'
implementation 'com.google.android.gms:play-services-maps:18.1.0'
implementation 'com.google.android.gms:play-services-location:21.0.1'
```
Retrofit
For province, city, and donor data, the CC team provided me with endpoints, so I used Retrofit as the REST client. Additionally, we use an API to send the email address to get an OTP code and to send the ID card image for verification.
```
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation "com.squareup.retrofit2:converter-gson:2.9.0"
implementation 'com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11'
implementation 'com.google.code.gson:gson:2.9.0'
```
That's the translation of the provided text. Let me know if you need any further assistance!
