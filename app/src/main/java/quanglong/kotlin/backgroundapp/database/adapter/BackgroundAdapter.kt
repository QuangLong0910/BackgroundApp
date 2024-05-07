package quanglong.kotlin.backgroundapp.database.adapter

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import quanglong.kotlin.backgroundapp.MainActivity
import quanglong.kotlin.backgroundapp.R
import quanglong.kotlin.backgroundapp.model.Background


class BackgroundAdapter(private val context: Context) :
    RecyclerView.Adapter<BackgroundAdapter.ImageViewHolder>() {
    private var background: List<Background> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemview, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(context)
            .load(background[position].image)
            .into(holder.imageView)
        holder.itemView.setOnClickListener {

            // Lấy bitmap từ URL
            val bitmap = getBitmapFromUrl(background[position].image)
            if (bitmap != null) {
                (context as MainActivity).showSetWallpaperConfirmationDialog(bitmap)
            }

        }

    }

    override fun getItemCount(): Int {
        return background.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.background)
    }

    private fun getBitmapFromUrl(imageUrl: String): Bitmap? {
        try {
            // Sử dụng Glide để tải bitmap từ URL
            val futureTarget = Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .apply(RequestOptions().override(300, 300))
                .submit()

            // Trả về bitmap từ FutureTarget
            return futureTarget.get()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
    fun setData(background: List<Background>) {
        this.background = background
        Log.d("Sai",background.toString())
        notifyDataSetChanged()
    }

//

}