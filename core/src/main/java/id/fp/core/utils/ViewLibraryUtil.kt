package id.fp.core.utils

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.shimmer.ShimmerFrameLayout
import id.fp.core.R

//show shimmer
fun showShimmer(view: ShimmerFrameLayout, hide: View) {
    with(view) {
        show()
        startShimmer()
    }
    with(hide) {
        hide()
    }
}

//hide shimmer
fun hideShimmer(view: ShimmerFrameLayout, show: View) {
    with(view) {
        hide()
        stopShimmer()
    }
    with(show) {
        show()
    }
}

//load image
fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.loading_anim)
        .error(R.drawable.ic_broken_image)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .override(150, 100)
        .thumbnail(0.25f)
        .into(this)
}

//load image
fun ImageView.loadImageFull(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.loading_anim)
        .error(R.drawable.ic_broken_image)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .thumbnail(0.25f)
        .into(this)
}

//load image youtube
fun ImageView.loadImageYt(url: String) {
    val ytId = extractYoutubeVideoId(url)
    val load = tumbnailYt(ytId.toString())
    Glide.with(this)
        .load(load)
        .placeholder(R.drawable.loading_anim)
        .error(R.drawable.ic_broken_image)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .thumbnail(0.25f)
        .centerCrop()
        .into(this)
}

//load image resource
fun ImageView.loadImageRs(context: Context, assetName: String) {
    Glide.with(this)
        .load(resources.getIdentifier(assetName, "drawable", context.packageName))
        .centerCrop()
        .into(this)
}

//laod image
fun ImageView.loadColor(context: Context, color: Int) {
    Glide.with(this)
        .load("")
        .placeholder(ColorDrawable(ContextCompat.getColor(context, color)))
        .into(this)
}