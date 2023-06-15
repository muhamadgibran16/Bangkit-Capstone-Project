package com.example.donorgo.factory

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.donorgo.activity.donor.VoluntaryViewModel
import com.example.donorgo.activity.edit_profile.EditProfileViewModel
import com.example.donorgo.activity.home.HomeViewModel
import com.example.donorgo.activity.lastdonor.LastDonorViewModel
import com.example.donorgo.activity.login.LoginViewModel
import com.example.donorgo.activity.otp.OtpViewModel
import com.example.donorgo.activity.profile.ProfileViewModel
import com.example.donorgo.activity.register.RegisterViewModel
import com.example.donorgo.activity.request_form.RequestViewModel
import com.example.donorgo.activity.uploud_photo.UploudViewModel
import com.example.donorgo.di.Injection
import com.example.donorgo.repository.ViewModelRepository

class RepoViewModelFactory private constructor
    (private val mViewModelRepository: ViewModelRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(mViewModelRepository) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(mViewModelRepository) as T
        } else if (modelClass.isAssignableFrom(OtpViewModel::class.java)) {
            return OtpViewModel(mViewModelRepository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(mViewModelRepository) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(mViewModelRepository) as T
        } else if (modelClass.isAssignableFrom(RequestViewModel::class.java)) {
            return RequestViewModel(mViewModelRepository) as T
        } else if (modelClass.isAssignableFrom(LastDonorViewModel::class.java)) {
            return LastDonorViewModel(mViewModelRepository) as T
        } else if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            return EditProfileViewModel(mViewModelRepository) as T
        } else if (modelClass.isAssignableFrom(UploudViewModel::class.java)) {
            return UploudViewModel(mViewModelRepository) as T
        } else if (modelClass.isAssignableFrom(VoluntaryViewModel::class.java)) {
            return VoluntaryViewModel(mViewModelRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: RepoViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): RepoViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RepoViewModelFactory(Injection.provideRepository(context))
            }.also { INSTANCE = it }
    }
}