package quanglong.kotlin.backgroundapp

import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import quanglong.kotlin.backgroundapp.database.adapter.BackgroundAdapter
import quanglong.kotlin.backgroundapp.databinding.ActivityMainBinding
import quanglong.kotlin.backgroundapp.model.Background
import quanglong.kotlin.backgroundapp.payment.PaymentActivity
import quanglong.kotlin.backgroundapp.viewmodel.BackgroundViewModel

import java.io.IOException

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {


    private val PICK_IMAGE_REQUEST = 1
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        setContentView(binding.root)

        val backgroundList = listOf(
            Background(
                "https://images.pexels.com/photos/22602074/pexels-photo-22602074/free-photo-of-red-mailbox-near-street.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                false
            ),
            Background(
                "https://images.pexels.com/photos/22602074/pexels-photo-22602074/free-photo-of-red-mailbox-near-street.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                false
            ),
            Background(
                "https://images.pexels.com/photos/22602074/pexels-photo-22602074/free-photo-of-red-mailbox-near-street.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                false
            ),
            // Thêm các đối tượng Background khác vào đây nếu cần
        )

        for (background in backgroundList) {

            backgroundViewModel.insertBackground(background)
        }


        val adapter = BackgroundAdapter(this)
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerview.adapter = adapter
        backgroundViewModel.getAll().observe(this, Observer {
            adapter.setData(it)
        })
        binding.buy.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        })


        val buttonSetWallpaper = binding.buttonSetWallpaper
        buttonSetWallpaper.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select wallpaper"), PICK_IMAGE_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val uri: Uri = data.data!!
            try {

                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                showSetWallpaperConfirmationDialog(bitmap)
//                setWallpaper(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Error when selecting image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun setWallpaper(bitmap: Bitmap) {
        val wallpaperManager = WallpaperManager.getInstance(applicationContext)

        try {
            wallpaperManager.setBitmap(bitmap)
            Toast.makeText(this, "The background image has been changed", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error when changing wallpaper", Toast.LENGTH_SHORT).show()
        }
    }

    fun showSetWallpaperConfirmationDialog(bitmap: Bitmap) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm wallpaper change")
        builder.setMessage("Do you want to change the background image to this photo?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            setWallpaper(bitmap)
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }


    private val backgroundViewModel: BackgroundViewModel by lazy {
        val application = this.application
        ViewModelProvider(
            this,
            BackgroundViewModel.BackgroundViewModelFactory(application)
        )[BackgroundViewModel::class.java]

    }
}
