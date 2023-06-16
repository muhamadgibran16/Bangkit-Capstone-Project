# MOBILE DEVELOPMENT
The Mobile Development (MD) team is responsible for creating a functional Android application. The DonorGo application will have five main menus: Home, Search Requests, Events, Articles, and Profile.

On the Home page, there will be five main features: Stock, Blood Type Compatibility Table, Blood Donor, Create Donation Request, and FAQ (Frequently Asked Questions). In the Stock page, users will see information about blood types, Rh factors, and the available stock of blood bags in a hospital. On the Blood Donor page, users can choose to "Look for Blood Donor Request" or "Donate Blood for the Hospital."

The Profile page will include a menu for scanning ID cards to complete personal data, a history of blood donor requests to manage fulfilled requests, and an About Us section that provides information about the development team and stakeholders involved in the application.

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
To align with the purpose of our application, we created a logo that incorporates a blood drop.
--photo--
Deliverable Logo
After several design and revision processes, below is the final logo design along with the application icon design.
--photo--
The logo carries its own meaning, starting with the blood drop connected by curved lines that follow the shape of the blood drop. This symbolizes the symbolism of life, where the red blood drop can represent life, energy, and vitality. The tying of the blood drop represents the symbolism of sacrifice, where strong commitment or bonds to certain values or goals may require sacrifices or struggles. It also signifies the symbolism of solidarity or unity, representing diversity, unity, and complementarity.

### User Experinces
To ensure that users have the best experience when using our application, I designed user flows and use cases. There are two actors in the DonorGo system: blood donors and individuals in need of blood donation. Blood donors are volunteers who are willing to donate their blood, while individuals in need of blood donation are those who require blood donation.

For more details, please click on this Figma link. [FIGMA DONORGO](https://www.figma.com/file/XGrvSqCM5Gk5rqVHZTAOwp/Donor-Go---Blood-Donation-Bangkit-Capstone-Project?type=design&node-id=0%3A1&t=R5oENuinMlfvXMHA-1)

### USER INTERFACE
For this part, most of my work was done in Figma, so please check the link for detailed modes. Here, I will briefly explain the design process. First, I created low-fidelity designs for the Home, Blood Donor, Blood Donation Event, and Donation Request pages. Below, you can see the designs.
--photo--
After that, I immediately moved on to the next step, which is creating high-fidelity designs to save time. The first three pages I designed were the splash screen, login screen, and registration screen. Initially, I intended to create designs with a soft tone, as seen below. After considering for a while, I decided to redesign these pages because the soft tone color palette did not match our logo design.
--photos before major changes--
--photos before major changes--

### RESOURCES
Here are all the resources I used in our application. For assets that I did not create myself, I have provided links to where I obtained them.
Component Photo
- https://www.svgrepo.com/unsplash.com
- https://fontawesome.com/
- https://www.flaticon.com/

### DATA
In our application, we need various data such as FAQ data, fun facts about blood donation or blood in general, and blood donation requests. Here are the links where I obtained this data:

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

Image
These are four libraries I used as image loaders.
```
implementation 'de.hdodenhof:circleimageview:3.1.0'
implementation 'com.github.bumptech.glide:glide:4.15.1'
androidTestImplementation project(path: ':app')
kapt 'com.github.bumptech.glide:compiler:4.15.1'
```
Maps
In our app, especially on the detail donation event page, you can see a mini-map showing the event location. To enable this feature, I used the Google Maps API and implemented these dependencies.
```
implementation 'com.google.android.gms:play-services-location:19.0.1'
implementation 'com.google.android.gms:play-services-places:17.0.0'
implementation 'com.google.android.gms:play-services-maps:18.0.2'
```
Toast
To show fancy toasts, I used this dependency.
implementation 'com.github.Spikeysanju:MotionToast:1.3.3.4'

Room
After a user successfully logs in, the user data, fun fact data, and FAQ data will be saved in a Room database. This way, the application doesn't have to fetch the data from Firebase every time it is needed.
```
annotationProcessor 'androidx.room:room-compiler:2.4.2'
implementation 'androidx.room:room-ktx:2.4.2'
implementation 'androidx.room:room-runtime:2.4.2'
kapt 'androidx.room:room-compiler:2.4.2'
```
Retrofit
For province, city, and donor data, the CC team provided me with endpoints, so I used Retrofit as the REST client. Additionally, we use an API to send the email address to get an OTP code and to send the ID card image for verification.
```
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation "com.squareup.retrofit2:converter-gson:2.9.0"
implementation 'com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11'
implementation 'com.google.code.gson:gson:2.9.0'
```
Loader
I used this dependency to show animation loading.
implementation "com.airbnb.android:lottie:3.4.0"

That's the translation of the provided text. Let me know if you need any further assistance!
