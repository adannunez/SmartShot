package com.smartshot

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.smartshot.fragments.SetupFragment
import com.smartshot.fragments.ShootFragment

class MainBottomNavActivity : AppCompatActivity() {
    private lateinit var fragmentContainer: FrameLayout

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                openFragment(SetupFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_shoot -> {
                openFragment(ShootFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_bottom_nav_activity)
        openFragment(SetupFragment())

        fragmentContainer = findViewById(R.id.fragmentContainer)

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
