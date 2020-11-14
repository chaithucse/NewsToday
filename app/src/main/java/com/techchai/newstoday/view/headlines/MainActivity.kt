package com.techchai.newstoday.view.headlines

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.techchai.newstoday.R
import com.techchai.newstoday.common.AppConstants
import com.techchai.newstoday.view.search.SearchActivity
import com.techchai.newstoday.view.settings.SettingsFragment
import com.techchai.newstoday.view.sources.SourcesFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Chaitanya
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var toolbar: Toolbar
    lateinit var sharedPreferences: SharedPreferences
    private val mNewsFragment = MainFragment()
    private val mSourceFragment: SourcesFragment = SourcesFragment()
    private val mSettingFragment = SettingsFragment()
    var activeFragment: Fragment = mNewsFragment
    val fm: FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.app_name)

        val navigationView1: BottomNavigationView = findViewById(R.id.nav_view)
        navigationView1.setOnNavigationItemSelectedListener(this)

        fm.beginTransaction().add(R.id.container, mSettingFragment, "3").hide(mSettingFragment)
            .commit()
        fm.beginTransaction().add(R.id.container, mSourceFragment, "2").hide(mSourceFragment)
            .commit()
        fm.beginTransaction().add(R.id.container, mNewsFragment, "1").commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.news -> {
                invalidateOptionsMenu()
                fm.beginTransaction().hide(activeFragment).show(mNewsFragment).commit()
                activeFragment = mNewsFragment
                toolbar.title = "News Today"
            }

            R.id.source -> {
                fm.beginTransaction().hide(activeFragment).show(mSourceFragment).commit()
                activeFragment = mSourceFragment
                toolbar.menu.clear()
                toolbar.title = "News Sources"
            }

            R.id.settings -> {
                toolbar.title = "Settings"
                toolbar.menu.clear()
                fm.beginTransaction().hide(activeFragment).show(mSettingFragment).commit()
                activeFragment = mSettingFragment
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
            }
            else -> ""
        }
        return true
    }
}