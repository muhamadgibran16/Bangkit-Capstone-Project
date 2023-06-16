package com.example.donorgo.activity.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donorgo.R
import com.example.donorgo.activity.dataStore
import com.example.donorgo.activity.detail_request.DetailRequestActivity
import com.example.donorgo.activity.donor.VoluntaryActivity
import com.example.donorgo.activity.event.EventActivity
import com.example.donorgo.activity.faq.FaqActivity
import com.example.donorgo.activity.lastdonor.LastDonorActivity
import com.example.donorgo.activity.maps.MapsRequestActivity
import com.example.donorgo.activity.news.News
import com.example.donorgo.activity.news.NewsActivity
import com.example.donorgo.activity.news.NewsData
import com.example.donorgo.activity.news.NewsHomeAdapter
import com.example.donorgo.activity.profile.ProfileActivity
import com.example.donorgo.activity.profile.ProfileViewModel
import com.example.donorgo.activity.request_form.RequestActivity
import com.example.donorgo.activity.stock.StockActivity
import com.example.donorgo.activity.table.TableActivity
import com.example.donorgo.databinding.ActivityHomeBinding
import com.example.donorgo.dataclass.BloodRequestItem
import com.example.donorgo.dataclass.UserProfileData
import com.example.donorgo.datastore.SessionViewModel
import com.example.donorgo.factory.RepoViewModelFactory
import com.example.storyapp.factory.SessionViewModelFactory

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    private val sessionViewModel: SessionViewModel by viewModels {
        SessionViewModelFactory.getInstance(dataStore)
    }
    private val profileViewModel: ProfileViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private val homeViewModel: HomeViewModel by viewModels {
        RepoViewModelFactory.getInstance(this)
    }
    private var myUsername: String = ""
    private var myToken: String = ""
    private var openFirstDialog: Boolean = false
    private var isUserVerified: Boolean = false
    private lateinit var userProfileData: UserProfileData
    private var userAction: Boolean = true
    private lateinit var adapterHome: ListHomeAdapter
    private lateinit var rvNewss: RecyclerView
    private val list = ArrayList<News>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        rvNewss = binding.rvNews
        rvNewss.setHasFixedSize(true)

        list.addAll(NewsData.listData)
        showRecyclerList()

        userProfileData = UserProfileData()
        // SessionViewModel
        sessionViewModel.getUsername().observe(this) {
            this.myUsername = it
            binding.greeting.text = resources.getString(R.string.welcome, myUsername)
        }
        sessionViewModel.getUserToken().observe(this) {
            this.myToken = it
            Log.w("token", it)
            profileViewModel.getUserProfile(myToken)
            homeViewModel.getBloodListRequest(myToken)

        }
        sessionViewModel.getIsOpenFirstDialog().observe(this) {
            this.openFirstDialog = it
            if (openFirstDialog) startActivity(
                Intent(
                    this@HomeActivity,
                    LastDonorActivity::class.java
                )
            )
        }

        // ProfileViewModel
        profileViewModel.userProfile.observe(this) { data ->
            this.openFirstDialog = data.lastDonor.isNullOrEmpty() == true
            this.userProfileData = data
            if (data.ktp && data.otp) {
                isUserVerified = true
            }
            Log.w("profil", "openFirstDialog? $openFirstDialog")
            if (openFirstDialog) startActivity(
                Intent(
                    this@HomeActivity,
                    LastDonorActivity::class.java
                )
            )
        }


        // HomeViewModel
        homeViewModel.listBloodRequest.observe(this) { list ->
            Log.w("home", list.toString())
            setDataToAdapter(list)
        }
        homeViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        homeViewModel.messageBloodRequest.observe(this) { message ->
            if (message != null) homeViewModel.isError?.value?.let { it1 ->
                showMessage(
                    message,
                    it1
                )
            }
        }
    }

    private fun showRecyclerList() {
        rvNewss.layoutManager =
            LinearLayoutManager(
                this@HomeActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        val academyAdapter = NewsHomeAdapter(list)
        rvNewss.adapter = academyAdapter
    }

    private fun setDataToAdapter(list: List<BloodRequestItem>) {
        if (list.isNotEmpty()) {
            adapterHome = ListHomeAdapter(list) { item ->
                val intent = Intent(this@HomeActivity, DetailRequestActivity::class.java)
                intent.putExtra(DetailRequestActivity.DETAIL_DATA, item)
                startActivity(intent)
            }
            binding.rvBlood.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(
                    this@HomeActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = adapterHome
            }
        }
    }

    private fun init() {
        with(binding) {
            // Button Navigation
            homeBtn.setOnClickListener(this@HomeActivity)
            eventBtn.setOnClickListener(this@HomeActivity)
            listRequestMapsBtn.setOnClickListener(this@HomeActivity)
            newsBtn.setOnClickListener(this@HomeActivity)
            profileBtn.setOnClickListener(this@HomeActivity)

            // Button Category
            btnStock.setOnClickListener(this@HomeActivity)
            btnTable.setOnClickListener(this@HomeActivity)
            btnDonate.setOnClickListener(this@HomeActivity)
            btnRequest.setOnClickListener(this@HomeActivity)
            btnFaq.setOnClickListener(this@HomeActivity)

            notifIcon.setOnClickListener(this@HomeActivity)
            bloodMore.setOnClickListener(this@HomeActivity)
            imgEvent.setOnClickListener(this@HomeActivity)
            eventMore.setOnClickListener(this@HomeActivity)
            newsMore.setOnClickListener(this@HomeActivity)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            // Button Navigation
            R.id.home_btn -> {}
            R.id.event_btn -> {
                ////////
                startActivity(Intent(this@HomeActivity, EventActivity::class.java))
            }
            R.id.list_request_maps_btn -> {
                //////// Pagination
                startActivity(Intent(this@HomeActivity, MapsRequestActivity::class.java))
            }
            R.id.news_btn -> {
                ////////
                startActivity(Intent(this@HomeActivity, NewsActivity::class.java))
            }
            R.id.profile_btn -> {
                val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
                intent.putExtra(ProfileActivity.EXTRA_DATA, userProfileData)
                startActivity(intent)
            }

            // Button Category
            R.id.btn_stock -> {
                ////////
                startActivity(Intent(this@HomeActivity, StockActivity::class.java))
            }
            R.id.btn_table -> {
                startActivity(Intent(this@HomeActivity, TableActivity::class.java))
            }
            R.id.btn_request -> {
                startActivity(Intent(this@HomeActivity, RequestActivity::class.java))
            }
            R.id.btn_donate -> {
                if (isUserVerified) {
                    val intent = Intent(this@HomeActivity, VoluntaryActivity::class.java)
                    intent.putExtra(VoluntaryActivity.EXTRA_DONOR, userProfileData)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, getString(R.string.cant_donate), Toast.LENGTH_LONG).show()
                }
            }
            R.id.btn_faq -> {
                startActivity(Intent(this@HomeActivity, FaqActivity::class.java))
            }

            R.id.notif_icon -> {}
            R.id.blood_more -> {
                //////// Pagination
                startActivity(Intent(this@HomeActivity, MapsRequestActivity::class.java))
            }
            R.id.event_more -> {
                ////////
                startActivity(Intent(this@HomeActivity, EventActivity::class.java))
            }
            R.id.img_event -> {
                ////////
                startActivity(Intent(this@HomeActivity, EventActivity::class.java))
            }
            R.id.news_more -> {
                ////////
                startActivity(Intent(this@HomeActivity, NewsActivity::class.java))
            }
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
                if (message == "News retrieved successfully!"
                    || message == "Requests retrieved successfully!"
                ) {
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

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

}