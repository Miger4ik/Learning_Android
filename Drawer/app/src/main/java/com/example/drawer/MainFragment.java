package com.example.drawer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

import static android.content.Context.LOCATION_SERVICE;

public class MainFragment extends Fragment {

    private TextView tvLocation;
    private Button getSettings;
    private WebView mapView;

    private LocationManager locationManager;

    private String locationText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);

        tvLocation = view.findViewById(R.id.tvLocation);
        getSettings = view.findViewById(R.id.btnLocationSettings);

        locationManager =(LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        getSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });

        return view;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();
        // set delay 10 seconds get location and distance for change 10 meters
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                1000 * 10, 10, locationListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {

        @SuppressLint("MissingPermission")
        @Override
        public void onProviderEnabled(String provider) {
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    private void showLocation(Location location) {
        if (location == null) {
            return;
        }
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            locationText = formatLocation(location);
            tvLocation.setText(formatLocation(location));
            Log.d("locationGPS -> ", locationText);
        } else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
            tvLocation.setText(formatLocation(location));
            locationText = formatLocation(location);
            Log.d("locationNET -> ", locationText);
        }
    }

    private String formatLocation(Location location) {
        if (location == null)
            return "";
        return String.format(
                "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));
    }
}
