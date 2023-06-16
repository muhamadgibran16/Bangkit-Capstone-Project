package com.example.donorgo.activity.maps

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.donorgo.R
import com.example.donorgo.activity.dataStore
import com.example.donorgo.activity.detail_request.DetailRequestActivity
import com.example.donorgo.activity.event.EventActivity
import com.example.donorgo.activity.home.HomeActivity
import com.example.donorgo.activity.home.ListHomeAdapter
import com.example.donorgo.activity.news.NewsActivity
import com.example.donorgo.activity.profile.ProfileActivity
import com.example.donorgo.activity.profile.ProfileViewModel
import com.example.donorgo.databinding.ActivityMapsRequestBinding
import com.example.donorgo.databinding.ToolbarMapsBinding
import com.example.donorgo.dataclass.BloodRequestItem
import com.example.donorgo.dataclass.UserProfileData
import com.example.donorgo.datastore.SessionViewModel
import com.example.donorgo.factory.RepoViewModelFactory
import com.example.donorgo.helper.DateFormater
import com.example.donorgo.helper.LocationConverter
import com.example.storyapp.factory.SessionViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task

class MapsRequestActivity : AppCompatActivity(), View.OnClickListener, OnMapReadyCallback {
    private lateinit var binding: ActivityMapsRequestBinding
    private lateinit var mMap: GoogleMap
    private lateinit var bindingInclude: ToolbarMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var adapterMaps: ListMapsAdapter
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory.getInstance(dataStore)
    }
    private val profileViewModel: ProfileViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private val mapsRequestViewModel: MapsRequestViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private var myToken: String = ""
    private var userAction: Boolean = true
    private lateinit var userProfileData: UserProfileData
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsRequestBinding.inflate(layoutInflater)
        bindingInclude = ToolbarMapsBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        val toolbar = findViewById<Toolbar>(R.id.toolbar_view)
        setSupportActionBar(toolbar)
        setContentView(binding.root)
        setupView()
        init()

        sessionViewModel.getUserToken().observe(this) { token ->
            this.myToken = token
            profileViewModel.getUserProfile(myToken)
            mapsRequestViewModel.getBloodListRequest(myToken)
        }
        // ProfileViewModel
        profileViewModel.userProfile.observe(this) { data ->
            this.userProfileData = data
            displayUserProfile(data)
        }

        // MapsRequestViewModel
        mapsRequestViewModel.listBloodRequest.observe(this) { list ->
            Log.w("home", list.toString())
            setDataToAdapter(list)
        }
        mapsRequestViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mapsRequestViewModel.messageBloodRequest.observe(this) { message ->
            if (message != null) mapsRequestViewModel.isError?.value?.let { it1 -> showMessage(message, it1) }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_maps) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun setDataToAdapter(list: List<BloodRequestItem>) {
        if (list.isNotEmpty()) {
            adapterMaps = ListMapsAdapter(list) { item ->
                val intent = Intent(this@MapsRequestActivity, DetailRequestActivity::class.java)
                intent.putExtra(DetailRequestActivity.DETAIL_DATA, item)
                startActivity(intent)
            }
            binding.rvBloodRequest.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(
                    this@MapsRequestActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = adapterMaps
            }
        }
    }

    private fun displayUserProfile(data: UserProfileData) {
        with(bindingInclude) {
            pageTitle.text = data.name
            data.name?.let { sessionViewModel.saveUsername(it) }
        }
        if (!data.photo.isNullOrEmpty()) {
            setUserPhotoProfile(data.photo, false)
        } else {
            if (data.gender == "Male") setUserPhotoProfile(R.drawable.avatar_cowok.toString(), true)
            if (data.gender == "Female") setUserPhotoProfile(R.drawable.avatar_cewek.toString(), true)
        }
    }

    private fun setUserPhotoProfile(photo: String, isDrawable: Boolean) {
        val placeholder = if (userProfileData.gender == "Male") R.drawable.avatar_cowok else R.drawable.avatar_cewek
        if (isDrawable) {
            val drawablePhoto = photo.toInt()
            Glide.with(this)
                .load(drawablePhoto)
                .placeholder(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // menggunakan cache untuk gambar
                .into(bindingInclude.profilUser)
        } else {
            Glide.with(this)
                .load(photo)
                .placeholder(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // menggunakan cache untuk gambar
                .into(bindingInclude.profilUser)
        }
    }

    private fun init() {
        with(binding) {
            // Button Navigation
//            homeBtn.setOnClickListener(this@MapsRequestActivity)
//            eventBtn.setOnClickListener(this@MapsRequestActivity)
//            listRequestMapsBtn.setOnClickListener(this@MapsRequestActivity)
//            newsBtn.setOnClickListener(this@MapsRequestActivity)
//            profileBtn.setOnClickListener(this@MapsRequestActivity)
        }
    }

    override fun onClick(v: View?) {
        // Button Navigation
        when(v?.id) {
            R.id.home_btn -> { startActivity(Intent(this@MapsRequestActivity, HomeActivity::class.java)) }
            R.id.event_btn -> { startActivity(Intent(this@MapsRequestActivity, EventActivity::class.java)) }
            R.id.list_request_maps_btn -> {}
            R.id.news_btn -> { startActivity(Intent(this@MapsRequestActivity, NewsActivity::class.java)) }
            R.id.profile_btn -> { startActivity(Intent(this@MapsRequestActivity, ProfileActivity::class.java)) }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isCompassEnabled = true
        getAndEnableMyLocation()
        setMapStyle()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    // Precise location access granted.
                    getAndEnableMyLocation()
                }
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    // Only approximate location access granted.
                    getAndEnableMyLocation()
                }
                else -> {
                    // No location access granted.
                }
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getAndEnableMyLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            mMap.isMyLocationEnabled = true
            initialDeviceLocation(fusedLocationClient.lastLocation)
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun initialDeviceLocation(lastLocationUser: Task<Location>) {
        lastLocationUser.addOnSuccessListener { location: Location? ->
            if (location != null) {
                showStartMarker(location)
            } else {
                Toast.makeText(
                    this@MapsRequestActivity,
                    "Location is not found. Try Again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showStartMarker(location: Location) {
        val startLocation = LatLng(location.latitude, location.longitude)
        val address = LocationConverter.getStringAddress(startLocation, this@MapsRequestActivity)
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                startLocation, 15f
            )
        )
        binding.tvAddress.text = address
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.maps_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String, isError: Boolean) {
        Log.w("check", "activity $isError")
        if (!isError) {
            if (userAction) {
                if (message == "Requests retrieved successfully!") {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            if (userAction) {
                Toast.makeText(
                    this,
                    "${getString(R.string.error_message_tag)} $message",
                    Toast.LENGTH_LONG
                ).show()
                Log.w("home", message)
            }
        }
        userAction = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Suppress("DEPRECATION")
    private fun setupView() {
        // Ubah warna Status Bar pada Android 5.0 Lollipop atau di atasnya
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = Color.WHITE

        // Sembunyikan ikon navigasi pada Status Bar dan tampilkan dengan warna hitam
        val flags =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        window?.decorView?.systemUiVisibility = flags
    }

    //use live template logt to create this
    companion object {
        private const val TAG = "MapsActivity"
    }

}