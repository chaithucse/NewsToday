package com.techchai.newstoday.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility functions
 * @author Chaitanya
 */
class AppUtils {

    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val service = Context.CONNECTIVITY_SERVICE
            val manager = context.getSystemService(service) as ConnectivityManager?
            val network = manager?.activeNetworkInfo
            return (network?.isConnected) ?: false
        }

        fun shareArticles(url: String?, context: Context) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, url)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }

        fun formatDate(dateVal: String?): String? {
            return try {
                val input_DateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"
                val output_DateFormat = "E MMM dd hh:mm a"
                val inputDateFormat = SimpleDateFormat(input_DateFormat)
                inputDateFormat.timeZone = TimeZone.getTimeZone("UTC")
                val date: Date = inputDateFormat.parse(dateVal)
                val outputDateFormat = SimpleDateFormat(output_DateFormat)
                outputDateFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                dateVal
            }
        }

        fun launchMoreApps(context: FragmentActivity?) {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("market://search?q=pub:Techchai")
                context!!.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                context!!.showToast("Application not found!")
            }
        }

        fun shareApp(context: FragmentActivity?) {
            try {
                val playStoreLink =
                    "https://play.google.com/store/apps/details?id=" +
                            context!!.packageName
                val shareText = "NewsToday Application: $playStoreLink"
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareText)
                sendIntent.type = "text/plain"
                context.startActivity(Intent.createChooser(sendIntent, "Send To:"))
            } catch (a: ActivityNotFoundException) {
                context!!.showToast("Application not found!")
            }
        }

        fun shareFeedback(context: FragmentActivity?) {
            try {
                val emailIntent = Intent(Intent.ACTION_SENDTO)
                emailIntent.data = Uri.parse("mailto: techchaimobile@gmail.com")
                context!!.startActivity(Intent.createChooser(emailIntent, "Send feedback"))
            } catch (a: ActivityNotFoundException) {
                context!!.showToast("Application not found!")
            }
        }

        fun getPackageVersion(context: FragmentActivity?): String {
            try {
                val pInfo: PackageInfo =
                    context!!.packageManager.getPackageInfo(context.getPackageName(), 0)
                return pInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return ""
        }
    }
}