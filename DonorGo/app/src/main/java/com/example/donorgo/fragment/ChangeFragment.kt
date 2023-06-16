package com.example.donorgo.fragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.donorgo.R
import com.example.donorgo.activity.camera.SelectImageActivity
import com.example.donorgo.activity.camera.SelectImageViewModel
import com.example.donorgo.databinding.FragmentChangeBinding
import java.io.File

class ChangeFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentChangeBinding? = null
    private lateinit var selectImageViewModel: SelectImageViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectImageViewModel =
            activity?.let { ViewModelProvider(it)[SelectImageViewModel::class.java] }!!
        selectImageViewModel.thisIsFilePhoto.observe(viewLifecycleOwner) { photo ->
            photo?.let { file ->
                binding.contentImage.setImageBitmap(BitmapFactory.decodeFile(file.path))
            } ?: run {
                binding.contentImage.setImageResource(R.drawable.picture_empty)
            }
        }

        // Action
        binding.btChange.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_change -> {
                val intent = Intent(context, SelectImageActivity::class.java)
                intent.putExtra(
                    SelectImageActivity.EXTRA_BOOL,
                    selectImageViewModel.isViewPhotoIsFill.value
                )
                intent.putExtra(
                    SelectImageActivity.EXTRA_FILE,
                    selectImageViewModel.thisIsFilePhoto.value?.path
                )
                Log.w("send", "change: ${selectImageViewModel.thisIsFilePhoto.value.toString()}")
                launcherIntentCamera.launch(intent)
            }
        }
    }

    @Suppress("DEPRECATION")
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.data != null) {
            if (it.resultCode == SelectImageActivity.CAMERA_RESULT) {
                val path = it.data?.getStringExtra(SelectImageActivity.EXTRA_FILE)
                val condition = it.data?.getBooleanExtra(SelectImageActivity.EXTRA_BOOL, false)
                if (condition != null) selectImageViewModel.setConditionOfViewPhoto(condition)
                if (!path.isNullOrEmpty()) File(path).let { file ->
                    selectImageViewModel.setMyFilePhoto(
                        file
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}