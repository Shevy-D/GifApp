package com.shevy.gifapp.presentation.detail

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.shevy.gifapp.data.models.database.Favorite
import com.shevy.gifapp.databinding.ActivityDetailBinding
import com.shevy.gifapp.presentation.favorite.FavoriteViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import kotlin.properties.Delegates

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var url: String
    private lateinit var previewUrl: String
    private lateinit var favorite: Favorite

    private var downloadId by Delegates.notNull<Long>()
    private lateinit var downloadManager: DownloadManager
    var status by Delegates.notNull<Int>()

    private val favoriteViewModel by viewModel<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailImageView = binding.detailImageView
        val downloadButton = binding.downloadButton
        //val checkBoxFavorite = binding.cbFavorite

        favoriteViewModel.initDatabase()
        url = intent.getStringExtra("url").toString()
        previewUrl = intent.getStringExtra("previewUrl").toString()
        //Glide.with(this@DetailActivity).load(url).into(detailImageView)
        Glide.with(this@DetailActivity).load(previewUrl).into(detailImageView)

        downloadButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                askPermissions()
            } else {
                downloadImage(url)
            }
        }

/*        lifecycleScope.launch {
            favorite = async {
                return@async favoriteViewModel.getFavoriteByUrl(previewUrl)
            }.await() ?: Favorite(downsized = previewUrl, original = url)
            checkBoxFavorite.isChecked = favorite.liked
        }*/

        //setupFavoriteToggle(checkBoxFavorite)

        registerReceiver(
            broadcastReceiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    override fun onResume() {
        super.onResume()

        val checkBoxFavorite = binding.cbFavorite

        lifecycleScope.launch {
            favorite = async {
                return@async favoriteViewModel.getFavoriteByUrl(previewUrl)
            }.await() ?: Favorite(downsized = previewUrl, original = url)
            checkBoxFavorite.isChecked = favorite.liked
        }

        setupFavoriteToggle(checkBoxFavorite)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    private fun setupFavoriteToggle(checkBoxFavorite: CheckBox) {
        checkBoxFavorite.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> {
                    lifecycleScope.launch {
                        favorite.liked = true
                        favoriteViewModel.insertFavorite(favorite)
/*                        Toast.makeText(
                            this@DetailActivity,
                            "Item added to Wishlist",
                            Toast.LENGTH_SHORT
                        ).show()*/
                    }
                }
                false -> {
                    favoriteViewModel.deleteFavoriteByUrl(previewUrl)
/*                    Toast.makeText(
                        this@DetailActivity,
                        "Item removed to Wishlist",
                        Toast.LENGTH_SHORT
                    ).show()*/
                }
            }
        }
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

/*          val reference = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            val action = intent?.action
            while (DownloadManager.ACTION_DOWNLOAD_COMPLETE != action) {*/

            var msg = ""
            Log.d("Status", status.toString())

            msg = when (status) {
                DownloadManager.STATUS_FAILED -> "Download has been failed, please try again"
                DownloadManager.STATUS_PAUSED -> "Paused"
                DownloadManager.STATUS_PENDING -> "Pending"
                DownloadManager.STATUS_RUNNING -> "Downloading..."
                DownloadManager.STATUS_SUCCESSFUL -> "Image downloaded successfully in"
                else -> "There's nothing to download"
            }
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadStatus(cursor: Cursor): String {

/*
        val cursor: Cursor = downloadManager.query(query)
        cursor.moveToFirst()
        if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL)
*/

        //val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
        val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

        //val columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON)
        val reason = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))

        var statusText = ""
        var reasonText = ""

        when (status) {
            DownloadManager.STATUS_FAILED -> {
                statusText = "STATUS_FAILED"
                when (reason) {
                    DownloadManager.ERROR_CANNOT_RESUME -> reasonText = "ERROR_CANNOT_RESUME"
                    DownloadManager.ERROR_DEVICE_NOT_FOUND -> reasonText =
                        "ERROR_DEVICE_NOT_FOUND"
                    DownloadManager.ERROR_FILE_ALREADY_EXISTS -> reasonText =
                        "ERROR_FILE_ALREADY_EXISTS"
                    DownloadManager.ERROR_FILE_ERROR -> reasonText = "ERROR_FILE_ERROR"
                    DownloadManager.ERROR_HTTP_DATA_ERROR -> reasonText =
                        "ERROR_HTTP_DATA_ERROR"
                    DownloadManager.ERROR_INSUFFICIENT_SPACE -> reasonText =
                        "ERROR_INSUFFICIENT_SPACE"
                    DownloadManager.ERROR_TOO_MANY_REDIRECTS -> reasonText =
                        "ERROR_TOO_MANY_REDIRECTS"
                    DownloadManager.ERROR_UNHANDLED_HTTP_CODE -> reasonText =
                        "ERROR_UNHANDLED_HTTP_CODE"
                    DownloadManager.ERROR_UNKNOWN -> reasonText = "ERROR_UNKNOWN"
                }
            }
            DownloadManager.STATUS_PAUSED -> {
                statusText = "STATUS_PAUSED"
                when (reason) {
                    DownloadManager.PAUSED_QUEUED_FOR_WIFI -> reasonText =
                        "PAUSED_QUEUED_FOR_WIFI"
                    DownloadManager.PAUSED_UNKNOWN -> reasonText = "PAUSED_UNKNOWN"
                    DownloadManager.PAUSED_WAITING_FOR_NETWORK -> reasonText =
                        "PAUSED_WAITING_FOR_NETWORK"
                    DownloadManager.PAUSED_WAITING_TO_RETRY -> reasonText =
                        "PAUSED_WAITING_TO_RETRY"
                }
            }
            DownloadManager.STATUS_PENDING -> statusText = "STATUS_PENDING"
            DownloadManager.STATUS_RUNNING -> statusText = "STATUS_RUNNING"
            DownloadManager.STATUS_SUCCESSFUL -> {
                statusText = "Image Saved Successfully"
                //reasonText = "Filename:\n" + filename;
                Toast.makeText(
                    this@DetailActivity,
                    "Download Status:\n$statusText\n$reasonText",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return statusText + reasonText
    }

    private fun askPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("Permission required")
                    .setMessage("Permission required to save photos from the Web.")
                    .setPositiveButton("Accept") { dialog, id ->
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                        )
                        finish()
                    }
                    .setNegativeButton("Deny") { dialog, id -> dialog.cancel() }
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                )
            }
        } else {
            downloadImage(url)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    downloadImage(url)
                }
                return
            }
            else -> {}
        }
    }

    var msg: String? = ""
    var lastMsg = ""

    @SuppressLint("Range")
    private fun downloadImage(url: String) {
        val directory = File(Environment.DIRECTORY_PICTURES)
        if (!directory.exists()) {
            directory.mkdirs()
        }

        downloadManager = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val request = DownloadManager.Request(Uri.parse(url)).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(url.substring(url.lastIndexOf("/") + 1))
                .setDescription("")
                .setDestinationInExternalPublicDir(
                    directory.toString(),
                    url.substring(url.lastIndexOf("/") + 1)
                )
        }

        downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)

        val cursor: Cursor = downloadManager.query(query)
        cursor.moveToFirst()
        status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
        Log.d("Status2", status.toString())

/*        lifecycleScope.launch {
            var downloading = true
            while (downloading) {
                cursor.moveToFirst()
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }
                withContext(Dispatchers.Main) {
*//*                    status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                    Log.d("Status4", status.toString())*//*
                    //msg = statusMessage(url, directory, status)
*//*                if (msg != lastMsg) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@DetailActivity, msg, Toast.LENGTH_LONG).show()
                    }
                    lastMsg = msg ?: ""
                    //}
                    //cursor.close()
                }*//*

                }
                delay(1000)
            }
        }*/
    }

    private fun statusMessage(url: String, directory: File, status: Int): String? {
        var msg = ""
        msg = when (status) {
            DownloadManager.STATUS_FAILED -> "Download has been failed, please try again"
            DownloadManager.STATUS_PAUSED -> "Paused"
            DownloadManager.STATUS_PENDING -> "Pending"
            DownloadManager.STATUS_RUNNING -> "Downloading..."
            DownloadManager.STATUS_SUCCESSFUL -> "Image downloaded successfully in $directory" + File.separator + url.substring(
                url.lastIndexOf("/") + 1
            )
            else -> "There's nothing to download"
        }
        return msg
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
        private const val LINK =
            "https://shwanoff.ru/wp-content/uploads/2018/03/URL-tagging-image.png"
    }
}