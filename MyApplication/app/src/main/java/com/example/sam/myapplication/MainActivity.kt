package com.example.sam.myapplication

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.app.Activity
import android.app.FragmentManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.sam.myapplication.model.CurrentUser
import com.example.sam.myapplication.model.DataManager
import com.example.sam.myapplication.objects.Event
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, DayEventAdapter.onDayClickListener,
        EventAdapter.onEventClickListener, CreateEventAdapter.onCreateEventClickListener {

    companion object{
        val REQUEST_CREATE_EVENT = 0
        val REQUEST_OPEN_DAY_EVENTS = 1
        val REQUEST_NAVIGATION_ALL_EVENTS = 2
        val REQUEST_CREATE_EVENT_DIALOG = 3
        //val REQUEST_OPEN_EVENT
    }

    val BAR_TITLE = "bar title"

    lateinit var navigationView:NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setCheckedItem(R.id.nav_events)

        val navName = navigationView.getHeaderView(0).findViewById(R.id.user_name) as TextView
        navName.text = CurrentUser.name
        val navMail = navigationView.getHeaderView(0).findViewById(R.id.user_mail) as TextView
        navMail.text = CurrentUser.login

        getPermission()

        if(CurrentUser.canIAddEvewnts()){
            navigationView.getMenu().findItem(R.id.nav_created_events).setVisible(true)
        }

        if(savedInstanceState == null)
            openAllEventsFragment()

    }



    fun getPermission(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            @TargetApi(Build.VERSION_CODES.M)
            if (ContextCompat.checkSelfPermission(applicationContext,Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                @TargetApi(Build.VERSION_CODES.M)
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
// Explain to the user why we need to read the contacts
                }
                requestPermissions(arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(BAR_TITLE, title.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        var ttl = savedInstanceState?.getString(BAR_TITLE)
        title = ttl
    }

    override fun onEventClick(event: Event) {
        var fragment = EventFragment()
        EventFragment.event = event
        var tr = supportFragmentManager.beginTransaction()
        tr.replace(R.id.fragment, fragment)
        tr.addToBackStack("event")
        tr.commit()
        title = event.name
    }

    override fun onDayClick(events: ArrayList<Event>) {
        var fragment = EventsOnDayFragment()
        EventsOnDayFragment.events = events
        fragment.flag = EventsOnDayFragment.FLAG_EVENTS_ON_DAY
        var tr = supportFragmentManager.beginTransaction()
        tr.replace(R.id.fragment, fragment)
        tr.addToBackStack("dayEvent")

        tr.commit()

        title = "События"
    }

    override fun onEditClick(event: Event) {
        var fragment = EditEventFragment()
        EditEventFragment.event = event
        var tr = supportFragmentManager.beginTransaction()
        tr.replace(R.id.fragment, fragment)
        tr.addToBackStack("editEvent")
        tr.commit()

        title = "Редактирование"
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            val cnt = supportFragmentManager.backStackEntryCount
            if (cnt == 0){
                val intent = Intent()
                intent.action = Intent.ACTION_MAIN
                intent.addCategory(Intent.CATEGORY_HOME)
                startActivity(intent)
            }
            else
                super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        //clearBackStack()
        when (id) {
            R.id.nav_today_events -> openTodayEventsFragment()
            R.id.nav_events -> openAllEventsFragment()
            R.id.nav_my_events -> openMyEventsFragment()
            R.id.nav_created_events -> openCratedEventsFragment()
            R.id.nav_exit -> openAuthorisationActivity()
        }
        clearBackStack()
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun clearBackStack(){
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        //Toast.makeText(this, supportFragmentManager.backStackEntryCount.toString(), Toast.LENGTH_SHORT).show()
    }

    fun openAllEventsFragment(){
        var fragment = AllEventsFragment()
        clearBackStack()
        var tr = supportFragmentManager.beginTransaction()
        tr.replace(R.id.fragment, fragment)
        //tr.addToBackStack("fragment")
        tr.commit()

        title = "Все события"
    }

    private fun openTodayEventsFragment(){
        var fragment = EventsOnDayFragment()
        clearBackStack()
        EventsOnDayFragment.events = DataManager.getEventsByDate(Date())
        fragment.flag = EventsOnDayFragment.FLAG_TODAY_EVENTS
        var tr = supportFragmentManager.beginTransaction()
        tr.replace(R.id.fragment, fragment)
        //tr.addToBackStack("fragment")
        tr.commit()

        title = "Cобытия"
    }

    private fun openMyEventsFragment(){
        var fragment = MyEventsFragment()
        clearBackStack()
        fragment.flag = EventsOnDayFragment.FLAG_TODAY_EVENTS
        var tr = supportFragmentManager.beginTransaction()
        tr.replace(R.id.fragment, fragment)
        //tr.addToBackStack("fragment")
        tr.commit()

        title = "Cобытия"
    }

    private fun openCratedEventsFragment(){
        var fragment = MyCreatedEventsFragment()
        clearBackStack()
        var tr = supportFragmentManager.beginTransaction()
        tr.replace(R.id.fragment, fragment)
        //tr.addToBackStack("fragment")
        tr.commit()
        title = "Созданные события"
    }

    private fun openAuthorisationActivity(){
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}