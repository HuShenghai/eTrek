package com.bottle.track.home

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.bottle.track.BaseActivity
import com.bottle.track.MyApplication
import com.bottle.track.R
import com.bottle.track.service.Command
import com.bottle.track.service.TrekService
import com.bottle.util.hasPermission
import com.bottle.util.requestPermission
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var homeFragment: Fragment? = null
    private var msgFragment: Fragment? = null
    private var dashFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(this)
        init()
        permission()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> supportFragmentManager.beginTransaction()
                    .hide(dashFragment)
                    .hide(msgFragment)
                    .show(homeFragment)
                    .commit()
            R.id.navigation_dashboard -> supportFragmentManager.beginTransaction()
                    .hide(homeFragment)
                    .hide(msgFragment)
                    .show(dashFragment)
                    .commit()
            R.id.navigation_notifications -> supportFragmentManager.beginTransaction()
                    .hide(homeFragment)
                    .hide(dashFragment)
                    .show(msgFragment)
                    .commit()
        }
        return true
    }

    private fun init() {
        homeFragment = HomeFragment.newInstance("", "1")
        msgFragment = CollectionFragment.newInstance("3", "3")
        dashFragment = MapFragment.newInstance("2", "2")
        supportFragmentManager.beginTransaction()
                .add(R.id.fLayoutContainer, homeFragment, "home")
                .add(R.id.fLayoutContainer, dashFragment, "map")
                .add(R.id.fLayoutContainer, msgFragment, "msg")
                .show(dashFragment)
                .commit()
        navigation.selectedItemId = R.id.navigation_dashboard
        val locationIntent = Intent(MyApplication.app, TrekService::class.java)
        locationIntent.putExtra(TrekService.COMMAND, Command(Command.START_LOCATION, "启动定位服务", ""))
        fLayoutContainer.postDelayed({ startService(locationIntent) }, 2000)

    }
    private fun permission(){
        if(!hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION,100)
        }
        if(!hasPermission(this, Manifest.permission.READ_PHONE_STATE)){
            requestPermission(this, Manifest.permission.READ_PHONE_STATE, 101)
        }
    }
}
