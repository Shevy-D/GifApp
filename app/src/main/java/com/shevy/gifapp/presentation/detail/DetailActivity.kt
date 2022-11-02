package com.shevy.gifapp.presentation.detail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.shevy.gifapp.R
import com.shevy.gifapp.data.models.database.Favorite
import com.shevy.gifapp.databinding.ActivityDetailBinding
import com.shevy.gifapp.presentation.favorite.FavoriteViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.*
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var url: String
    private lateinit var previewUrl: String
    private lateinit var favorite: Favorite
    lateinit var progressBar: ProgressBar

    private val favoriteViewModel by viewModel<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailImageView = binding.detailImageView

        progressBar = binding.progressBar
        progressBar.visibility = View.INVISIBLE

        favoriteViewModel.initDatabase()
        url = intent.getStringExtra("url").toString()
        previewUrl = intent.getStringExtra("previewUrl").toString()
        Glide.with(this@DetailActivity).load(previewUrl).into(detailImageView)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.download_menu -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    askPermissions()
                } else {
                    callGlideToSaveGif()
                }
                progressBar.visibility = View.VISIBLE
            }

            R.id.share_menu
            -> {
/*                Toast.makeText(
                    this@DetailActivity,
                    "You clicked on Share Button",
                    Toast.LENGTH_SHORT
                ).show()*/

                shareGifTestFun()
                //shareGif()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareGifTestFun() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, url)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, "Share GIF"))

        //sendIntent.putExtra(Intent.EXTRA_STREAM, url)
        //sendIntent.type = "image/gif"
        //startActivity(sendIntent)
    }

    private fun shareGif() {
        Glide.with(this@DetailActivity).asFile()
            .load(url)
            .apply(RequestOptions().format(DecodeFormat.PREFER_ARGB_8888))
            .into(object : SimpleTarget<File?>() {
                override fun onResourceReady(resource: File, transition: Transition<in File?>?) {
                    shareUrlGifImage(resource)
                }
            })
    }

    private fun shareUrlGifImage(shareImage: File) {
        val location = File(
            "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)} ${
                resources.getString(R.string.app_name)
            }"
        )

        val shareFile = File(location.path + ".gif")

        try {
            val fileOutputStream = FileOutputStream(shareFile)
            val fileInputStream = FileInputStream(shareImage)

            val inputChannel = fileInputStream.channel
            val outputChannel = fileOutputStream.channel

            inputChannel.transferTo(0, inputChannel.size(), outputChannel)

            fileOutputStream.close()
            fileInputStream.close()

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            val uri = Uri.parse(shareFile.path)
            shareIntent.putExtra(Intent.EXTRA_TEXT, url)
            startActivity(Intent.createChooser(shareIntent, "Share Gif"))

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun callGlideToSaveGif() {
        Glide.with(this@DetailActivity).asFile()
            .load(url)
            .apply(RequestOptions().format(DecodeFormat.PREFER_ARGB_8888))
            .into(object : SimpleTarget<File?>() {
                override fun onResourceReady(resource: File, transition: Transition<in File?>?) {
                    saveUrlGifImage(resource)
                }
            })
    }

    private fun saveUrlGifImage(gifFile: File) {
        val directory = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "/GifApp"
        )

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file =
            File(directory.path + File.separator + Calendar.getInstance().timeInMillis + ".gif")

        try {
            val fileOutputStream = FileOutputStream(file)
            val fileInputStream = FileInputStream(gifFile)

            val inputChannel = fileInputStream.channel
            val outputChannel = fileOutputStream.channel

            inputChannel.transferTo(0, inputChannel.size(), outputChannel)

            fileOutputStream.close()
            fileInputStream.close()

            Toast.makeText(
                this@DetailActivity,
                "Gif Successfully Downloaded \n Check out GifApp Folder",
                Toast.LENGTH_SHORT
            ).show()

            val saveGifIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            saveGifIntent.data = Uri.fromFile(file)
            sendBroadcast(saveGifIntent)

            progressBar.visibility = View.GONE
        } catch (e: IOException) {
            e.printStackTrace()
        }
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
                    .setMessage("Permission required to save gif from the Web.")
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
            //downloadImage(url)
            callGlideToSaveGif()
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
                    callGlideToSaveGif()
                }
                return
            }
            else -> {}
        }
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }
}