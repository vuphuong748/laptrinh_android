package com.example.b2004748_mymap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.b2004748_mymap.databinding.ActivityDisplayMapsBinding
import com.example.b2004748_mymap.models.UserMap
import com.google.android.gms.maps.model.LatLngBounds

private const val TAG = "DisplayMapActivity"
class DisplayMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityDisplayMapsBinding
    private lateinit var userMap: UserMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisplayMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userMap = intent.getSerializableExtra(Utils.EXTRA_USER_MAP) as UserMap
        supportActionBar?.title = userMap.title

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        Log.i(TAG, "map: ${userMap.title}")
        val boundsBuilder = LatLngBounds.builder() //xd ranh gioi
        for (place in userMap.places) {
            val latLng = LatLng(place.latitude, place.longitude)
            boundsBuilder.include(latLng)
            mMap.addMarker(MarkerOptions().position(latLng).title(place.title).snippet(place.description) )
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 1000, 1000, 0))
        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}