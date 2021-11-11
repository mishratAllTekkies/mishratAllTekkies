package com.htf.zbCard.ui.home


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.htf.zbCard.R
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.diva.utils.GPSTracker
import com.diva.utils.GeoCodingLocation
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.htf.zbCard.base.BaseActivity
import com.htf.zbCard.databinding.HomeActivityBinding
import com.htf.zbCard.utils.AppSession
import com.htf.zbCard.utils.PermissionUtility
import kotlinx.android.synthetic.main.home_activity.*


class HomeActivity : BaseActivity<HomeActivityBinding, DashboardViewModel>(
    DashboardViewModel::class.java),
    View.OnClickListener {
    private var currentNavController: LiveData<NavController>? = null

    private var isMenu=true
    override var layout = R.layout.home_activity
    lateinit var navControllers: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private var currActivity: Activity =this

    private var mLocationPermissionGranted = false
    private var latitude = 0.0
    private var longitude = 0.0
    private var currentLocation = ""
    private var seletcedLocation = ""
    private lateinit var gps: GPSTracker
    private lateinit var lastLocation: Location
    private var locationUpdateState = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private  val LOCATION_PERMISSION_REQUEST_CODE = 1
    private  val REQUEST_CHECK_SETTINGS = 2

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, HomeActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.homeViewModel = viewModel
        setListener()
        navController = findNavController(R.id.main_nav_host) //Initialising navController

        appBarConfiguration = AppBarConfiguration.Builder(R.id.nav_graph_home) //Pass the ids of fragments from nav_graph which you d'ont want to show back button in toolbar
            .setDrawerLayout(binding.mainDrawerLayout) //Pass the drawer layout id from activity xml
            .build()

        setSupportActionBar(binding.mainToolbar) //Set toolbar
        setupActionBarWithNavController(navController,appBarConfiguration) //Setup toolbar with back button and drawer icon according to appBarConfiguration

        visibilityNavElements(navController) //If you want to hide drawer or bottom navigation configure that in this function
        showBothNavigation()
        binding.mainToolbar.setNavigationOnClickListener {
            if(isMenu){
                //open Drawer
                binding.mainDrawerLayout.openDrawer(GravityCompat.START,true)
            }else{
                onBackPressed()
            }
        }

        if (AppSession.locality!=""){
            tvLocation.text=AppSession.locality
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) { return; }
        initGpsLocation()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createLocationRequest()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                AppSession.latitude=lastLocation.latitude
                AppSession.longitude=lastLocation.longitude
                currentLocation = GeoCodingLocation.getAddressFromLatLong(currActivity,lastLocation.latitude,lastLocation.longitude)
                tvLocation.text = currentLocation

            }
        }

        main_bottom_navigation_view.setOnNavigationItemReselectedListener {
            // Do nothing to ignore the reselection
        }

    }

    private fun setListener() {
        //toolbar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
             /* R.id.toolbar->{
              onBackPressed()*/
            }
        }

    private fun initGpsLocation() {
        currentLocation = GeoCodingLocation.getAddressFromLatLong(currActivity, AppSession.latitude, AppSession.longitude)
        tvLocation.text = currentLocation

        mLocationPermissionGranted = PermissionUtility.checkLocationPermission(currActivity)
        if (mLocationPermissionGranted) {
            gps = GPSTracker(currActivity)
            if (gps.canGetLocation()) {
                latitude = gps.getLatitude()
                longitude = gps.getLongitude()
                if (latitude != 0.0 && longitude != 0.0) {
                    try {
                        AppSession.latitude = latitude
                        AppSession.longitude = longitude
                        currentLocation = GeoCodingLocation.getAddressFromLatLong(currActivity, latitude, longitude)
                        val geoCoder = Geocoder(currActivity)
                        val addressList = geoCoder.getFromLocation(lastLocation.latitude,lastLocation.longitude, 1)
                        tvLocation.text = currentLocation

                    }catch (e:Exception){

                    }
                }
            }
        } else {
            mLocationPermissionGranted = PermissionUtility.checkLocationPermission(currActivity)
        }
    }



    private fun visibilityNavElements(navController: NavController) {

        //Listen for the change in fragment (navigation) and hide or show drawer or bottom navigation accordingly if required
        //Modify this according to your need
        //If you want you can implement logic to deselect(styling) the bottom navigation menu item when drawer item selected using listener

        navController.addOnDestinationChangedListener { _, destination, _->
            when (destination.id) {

              R.id.home_dest->{
                    isMenu=true
                    binding.mainToolbar.visibility=View.VISIBLE
                    binding.rltAddress.visibility=View.VISIBLE
                    binding.mainToolbar.setNavigationIcon(R.drawable.menu)
                }

              R.id.nav_graph_cart_info->{
                    isMenu=true
                  binding.mainToolbar.visibility=View.VISIBLE
                    binding.rltAddress.visibility=View.GONE
                    binding.mainToolbar.setNavigationIcon(R.drawable.menu)
                }

              R.id.nav_graph_order->{
                    isMenu=true
                  binding.mainToolbar.visibility=View.VISIBLE
                    binding.rltAddress.visibility=View.GONE
                    //binding.mainToolbar.title=currActivity.getString(R.string.home)
                    binding.mainToolbar.setNavigationIcon(R.drawable.menu)
                }

              R.id.nav_graph_favourite->{
                    isMenu=true
                  binding.mainToolbar.visibility=View.VISIBLE
                  binding.rltAddress.visibility=View.GONE
                    binding.mainToolbar.setNavigationIcon(R.drawable.menu)
                }

                R.id.details_dest -> {
                    isMenu=false
                    binding.rltAddress.visibility=View.GONE
                    hideBothNavigation()
                }
                else -> showBothNavigation()
            }
        }
    }

    private fun hideBothNavigation() {
        binding.mainToolbar.setNavigationIcon(R.drawable.back_arrow)//Hide both drawer and bottom navigation bar
        binding.mainBottomNavigationView.visibility = View.VISIBLE
        binding.mainNavigationView.visibility = View.GONE
        binding.mainToolbar.visibility=View.GONE
        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //To lock navigation drawer so that it don't respond to swipe gesture
    }

    private fun hideBottomNavigation() { //Hide bottom navigation
        binding.mainBottomNavigationView.visibility = View.GONE
        binding.mainNavigationView.visibility = View.VISIBLE
        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED) //To unlock navigation drawer
        binding.mainNavigationView.setupWithNavController(navController) //Setup Drawer navigation with navController
    }

    private fun showBothNavigation() {
        binding.mainBottomNavigationView.visibility = View.VISIBLE
        binding.mainNavigationView.visibility = View.VISIBLE
        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        setupNavControl()
        binding.mainToolbar.setNavigationIcon(R.drawable.menu)//To configure navController with drawer and bottom navigation
    }

    private fun setupNavControl() {
        binding.mainNavigationView.setupWithNavController(navController) //Setup Drawer navigation with navController
        binding.mainBottomNavigationView.setupWithNavController(navController) //Setup Bottom navigation with navController
    }

    fun exitApp() { //To exit the application call this function from fragment
        this.finishAffinity()
    }

    override fun onSupportNavigateUp(): Boolean { //Setup appBarConfiguration for back arrow
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        when { //If drawer layout is open close that on back pressed
            binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START) -> {
                binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
            }
            else -> {
                super.onBackPressed() //If drawer is already in closed condition then go back
            }
        }
    }


    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        /*   locationRequest.interval = 10000
           locationRequest.fastestInterval = 20000*/
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->

            if (e is ResolvableApiException) {
                try {

                    e.startResolutionForResult(currActivity, REQUEST_CHECK_SETTINGS
                    )

                } catch (sendEx: IntentSender.SendIntentException) {

                }
            }
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> {
                if (resultCode == Activity.RESULT_OK) {
                    locationUpdateState = true
                    startLocationUpdates()
                }
            }
             }
        }


}