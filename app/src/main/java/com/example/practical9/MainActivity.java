package com.example.practical9;

import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude = 19.105259;
    private double longitude = 17.832515;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        longitude = intent.getDoubleExtra("longitude", 17.832515);
        latitude = intent.getDoubleExtra("latitude", 19.105259);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set a default map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Enable UI controls like zoom controls
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Set the location and add a marker
        LatLng specificLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(specificLocation)
                .title("Marker in Specific Location")
                .snippet("This is a marker description")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        // Move the camera to the location with a zoom level
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(specificLocation, 15));
    }
}
