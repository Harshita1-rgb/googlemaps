package com.example.practical9;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.practical9.databinding.ActivityMaps2Binding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMaps2Binding binding;
    private MarkerOptions markerOptions;
    private GpsUtil gpsUtil = new GpsUtil();
    private LocationCallback locationCallback;

    public static void getInstance(Context context, double latitude, double longitude) {
        Intent intent = new Intent(context, MapsActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMaps2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Retrieve latitude and longitude from intent
        double latitude = getIntent().getDoubleExtra("latitude", -34);
        double longitude = getIntent().getDoubleExtra("longitude", 151);
        LatLng initialLocation = new LatLng(latitude, longitude);

        // Initialize marker options and add the marker
        markerOptions = new MarkerOptions().position(initialLocation).title("Current Location");
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 10));

        // Set up location callback to update marker
        setupLocationCallback();
        gpsUtil.getLocation(this, locationCallback);
    }

    private void setupLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    double latitude = locationResult.getLastLocation().getLatitude();
                    double longitude = locationResult.getLastLocation().getLongitude();
                    LatLng newLocation = new LatLng(latitude, longitude);

                    // Update marker position and move camera
                    markerOptions.position(newLocation);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 10));
                }
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        gpsUtil.closeLocationUpdates(locationCallback);
    }
}
