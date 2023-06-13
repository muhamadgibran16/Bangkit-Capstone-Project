package com.example.donorgo.activity.camera

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import com.example.donorgo.R
import com.example.donorgo.databinding.ActivitySelectImageBinding
import com.example.donorgo.helper.createCustomTempFile
import com.example.donorgo.helper.rotateFile
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class SelectImageActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySelectImageBinding
    private val selectImageViewModel: SelectImageViewModel by viewModels()
    private lateinit var currentPhotoPath: String
    private lateinit var uriPhoto: Uri
    private var myFile: File? = null
    private var isFilled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        init()

        val condition = intent.getBooleanExtra(EXTRA_BOOL, false)
        val path = intent.getStringExtra(EXTRA_FILE)
        selectImageViewModel.setConditionOfViewPhoto(condition)
        if (!path.isNullOrEmpty()) File(path).let { file -> selectImageViewModel.setMyFilePhoto(file) }

        selectImageViewModel.thisIsFilePhoto.observe(this) { photo ->
            photo?.let { file ->
                this.myFile = file
                binding.contentImage.setImageBitmap(BitmapFactory.decodeFile(file.path))
            } ?: run {
                binding.contentImage.setImageResource(R.drawable.picture_empty)
            }
        }

        selectImageViewModel.isViewPhotoIsFill.observe(this) {
            binding.apply {
                btnContinue.isEnabled = it != false
                this@SelectImageActivity.isFilled = it != false
            }
        }

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

    }

    private fun init() {
        binding.buttonCamera.setOnClickListener(this)
        binding.buttonGallery.setOnClickListener(this)
        binding.btnContinue.setOnClickListener(this)
    }

    // Camera
    @Suppress("DEPRECATION")
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            this.myFile = myFile

            myFile.let { file ->
                // Silakan gunakan kode ini jika mengalami perubahan rotasi
                val exifInterface = ExifInterface(file.path)
                val orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED
                )
                var isBackCamera = true
                val isPhotrait =
                    orientation == ExifInterface.ORIENTATION_ROTATE_90 || orientation == ExifInterface.ORIENTATION_ROTATE_270
                if (orientation == ExifInterface.ORIENTATION_ROTATE_90) isBackCamera = true
                if (orientation == ExifInterface.ORIENTATION_ROTATE_270) isBackCamera = false
                if (isPhotrait) rotateFile(file, isBackCamera)
                binding.contentImage.setImageBitmap(BitmapFactory.decodeFile(file.path))
                selectImageViewModel.setConditionOfViewPhoto(true)
            }
        }
    }

    // Gallery
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                uriPhoto = uri
                val myFile = uriToFile(uri, this@SelectImageActivity)
                this.myFile = myFile
                binding.contentImage.setImageBitmap(BitmapFactory.decodeFile(myFile.path))
                selectImageViewModel.setConditionOfViewPhoto(true)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_camera -> {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.resolveActivity(packageManager)
                createCustomTempFile(application).also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this@SelectImageActivity,
                        "com.example.donorgo.activity.camera",
                        it
                    )
                    uriPhoto = photoURI
                    currentPhotoPath = it.absolutePath
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    launcherIntentCamera.launch(intent)
                }
            }
            R.id.button_gallery -> {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                val chooser = Intent.createChooser(intent, "Choose a Picture")
                launcherIntentGallery.launch(chooser)
            }
            R.id.btn_continue -> {
                selectImageViewModel.setMyFilePhoto(myFile)
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_FILE, myFile?.path)
                resultIntent.putExtra(EXTRA_BOOL, isFilled)
                setResult(CAMERA_RESULT, resultIntent)
                finish()
            }
        }
    }

    // Mengubah URI menjadi File
    fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    getString(R.string.did_not_get_permit),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    @Suppress("DEPRECATION")
    private fun setupView() {
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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressedDispatcher
        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_FILE, myFile?.path)
        resultIntent.putExtra(EXTRA_BOOL, isFilled)
        setResult(CAMERA_RESULT, resultIntent)
        finish()
    }

    companion object {
        const val CAMERA_RESULT = 200
        const val EXTRA_FILE = "extra_file"
        const val EXTRA_BOOL = "extra_bool"

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}