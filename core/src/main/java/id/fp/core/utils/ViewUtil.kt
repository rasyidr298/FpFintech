package id.fp.core.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

//toast
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

//snacbar
fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("OK") {
            snackbar.dismiss()
        }
    }.show()
}

//show view
fun View.show() {
    visibility = View.VISIBLE
}

//hide view
fun View.hide() {
    visibility = View.GONE
}

//clear input layout
fun List<View>.clearErrorInputLayout() {
    forEach { vi ->
        if (vi is TextInputLayout) {
            vi.isErrorEnabled = false
        }
    }
}

//get tag context
fun tag(context: Context): String {
    return context.javaClass.simpleName
}

//get id link youtubr
fun extractYoutubeVideoId(ytUrl: String?): String? {
    var vId: String? = null
    val pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
    val compiledPattern: Pattern = Pattern.compile(pattern)
    val matcher: Matcher = compiledPattern.matcher(ytUrl)
    if (matcher.find()) {
        vId = matcher.group()
    }
    return vId
}

//load tumbnail youtube
fun tumbnailYt(ytId: String): String {
    return "https://img.youtube.com/vi/${ytId}/hqdefault.jpg"
}

//get device name
fun getDeviceName(): String {
    return Build.MODEL + " || " + "SDK " + Build.VERSION.SDK_INT
}

//transparans statusbar
fun hideStatusBar(activity: Activity) {
    activity.window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        statusBarColor = Color.TRANSPARENT
    }
}

fun isAboveAndroid11(): Boolean {
    return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
}

fun checkTheme(context: Context): Int? {
    return context.resources.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
}

val dateInString = getCurrentDateTime().toString("yyyy/MM/dd HH:mm:ss")

fun intentToWeb(context: Context, url: String) {
    val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))

    try {
        context.startActivity(i)
    } catch (e: ActivityNotFoundException) {
        Log.e(tag(context), e.message.toString())
    }
}

fun intentToYoutube(context: Context, urlYtb: String) {
    val ytId = extractYoutubeVideoId(urlYtb)
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://$ytId")))
    } catch (e: ActivityNotFoundException) {
        Log.e(tag(context), e.message.toString())
    }
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}