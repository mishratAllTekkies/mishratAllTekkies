package com.htf.zbCard.ui.location

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
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.htf.zbCard.R
import com.htf.zbCard.base.BaseActivity
import com.htf.zbCard.databinding.MapActivityBinding
import com.htf.zbCard.ui.home.HomeActivity
import com.htf.zbCard.utils.AppSession
import com.htf.zbCard.utils.DialogUtils.showToast
import kotlinx.android.synthetic.main.map_activity.*
import kotlinx.android.synthetic.main.toolbar_primary.*

class MapActivity : BaseActivity<MapActivityBinding,DetectLocationViewModel>(DetectLocationViewModel::class.java),
    View.OnClickListener,OnMapReadyCallback {
    private var currActivity: Activity = this

    var latitude: Double = 0.00
    var longitude: Double = 0.00
    private lateinit var lastLocation: Location
    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap
    private lateinit var locationRequest: LocationRequest
    var mLocation: Location? = null
    private lateinit var fusedlocationClient: FusedLocationProviderClient
    private lateinit var mMap: GoogleMap
    private var locationUpdateState = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback


    override val layout: Int get() = R.layout.map_activity
    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,MapActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.detectLocationViewModel = viewModel
        tvTitle.text=currActivity.getString(R.string.detect_your_location)
        setListener()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createLocationRequest()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
                setUpMap()
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
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 20000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->

            if (e is ResolvableApiException) {
                try {

                    e.startResolutionForResult(
                        currActivity,
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {

                }
            }
        }
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        mMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                //placeMarkerOnMap(currentLatLng)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 18f))
                mMap.mapType = 1
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CHECK_SETTINGS -> {
                if (resultCode == Activity.RESULT_OK) {
                    locationUpdateState = true
                    startLocationUpdates()
                }

            }

        } }

    private fun placeMarkerOnMap(location: LatLng) {
        // 1
        try {
            val geoCoder= Geocoder(currActivity)
            val addressList=geoCoder.getFromLocation(location.latitude, location.longitude, 1)

            if(!addressList.isEmpty()){

                val locality=addressList.get(0).getAddressLine(0)

                AppSession.locality=locality

                val country=addressList.get(0).countryName


                val markerOptions = MarkerOptions().position(mMap.cameraPosition.target)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_detector))
                // 2
                mMap.addMarker(markerOptions)

                imgLocationMap_home.visibility= View.GONE

                latitude=addressList[0].latitude
                longitude=addressList[0].longitude
                AppSession.latitude = latitude
                AppSession.longitude = longitude
               // AppSession.countryCode=addressList[0].countryCode
                etDetectLocation.setText(locality)

            }

        }catch (e: Exception){
            showToast(currActivity, e.message.toString(), true)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    setUpMap()
                }
                return;
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled=false
        setUpMap()
        mMap.setOnCameraMoveListener(object : GoogleMap.OnCameraMoveListener {
            override fun onCameraMove() {
                mMap.clear()
                imgLocationMap_home.visibility = View.VISIBLE
//                pbLocationMaps.visibility=View.VISIBLE
            }

        })
        mMap.setOnCameraIdleListener(object : GoogleMap.OnCameraIdleListener {
            override fun onCameraIdle() {
                val latlang = mMap.cameraPosition.target
                placeMarkerOnMap(latlang)
            }
        })

    }


    private fun setListener() {
        btn_continue.setOnClickListener(this)
      }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_continue->{
                HomeActivity.open(currActivity)
            }
         }
      }

}

