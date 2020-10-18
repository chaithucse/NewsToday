package com.techchai.newstoday.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.techchai.newstoday.R
import com.techchai.newstoday.common.AppConstants
import com.techchai.newstoday.common.AppUtils

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener,
    Preference.OnPreferenceClickListener {

    lateinit var sharedPreferences: SharedPreferences
    var country_value = "us"
    var country_name = "United States (Default)"

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)
        sharedPreferences =
            activity!!.getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE)
        country_value = sharedPreferences.getString(AppConstants.PREF_COUNTRY_VALUE, "us")!!
        country_name =
            sharedPreferences.getString(AppConstants.PREF_COUNTRY_NAME, "United States (Default)")!!
        initPreferenceSummary()
    }

    private fun initPreferenceSummary() {
        val sharePreference: Preference = this.findPreference("share")!!
        val countryPreference: ListPreference = this.findPreference("country")!!
        countryPreference.summary = country_name
        countryPreference.onPreferenceChangeListener = this
        sharePreference.onPreferenceClickListener = this

        val appsPreference: Preference = this.findPreference("apps")!!
        appsPreference.onPreferenceClickListener = this

        val verPreference: Preference = this.findPreference("version")!!
        verPreference.summary = AppUtils.getPackageVersion(activity).toString()

        val apiPreference: Preference = this.findPreference("api")!!
        apiPreference.onPreferenceClickListener = this

        val feedbackPreference: Preference = this.findPreference("feedback")!!
        feedbackPreference.onPreferenceClickListener = this
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {

        if (preference?.key.equals("country")) {
            val listPreference: ListPreference = preference as ListPreference

            val prefIndex = listPreference.findIndexOfValue(newValue.toString())
            preference.setSummary(listPreference.entries[prefIndex])

            with(sharedPreferences.edit()) {
                putString(
                    AppConstants.PREF_COUNTRY_VALUE,
                    listPreference.entryValues[prefIndex] as String
                ).apply()
                putString(
                    AppConstants.PREF_COUNTRY_NAME,
                    listPreference.entries[prefIndex] as String
                ).apply()
            }
        }
        return true
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        if (preference!!.key.equals("apps")) {
            AppUtils.launchMoreApps(activity)
        } else if (preference.key.equals("share")) {
            AppUtils.shareApp(activity)
        } else if(preference.key.equals("feedback")) {
            AppUtils.shareFeedback(activity)
        } else if(preference.key.equals("api")) {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(activity!!, Uri.parse("https://newsapi.org/"))
        }
        return true
    }
}