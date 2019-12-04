package com.mohanad.myownbank;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<LatLng> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment != null){
            mapFragment.getMapAsync(this);
        }

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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locations=new ArrayList<>();


        locations.add(new LatLng(31.523910, 34.453230));
        locations.add(new LatLng(31.518556, 34.445932));
        locations.add(new LatLng(31.514295, 34.450088));
        locations.add(new LatLng(31.536340, 34.461379));
        locations.add(new LatLng(31.477640, 34.389144));
        locations.add(new LatLng(31.410179, 34.360016));
        locations.add(new LatLng(31.444396, 34.396952));
        locations.add(new LatLng(31.52308, 34.48559));
        locations.add(new LatLng(31.2768084, 34.2506016));
        locations.add(new LatLng(31.3466072, 34.3036632));
        // Add a marker in Sydney and move the camera

        for(int i=0;i<locations.size();i++){
            mMap.addMarker(new MarkerOptions().position(locations.get(i)).title("Bank Of Palestine").
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        }


        mMap.moveCamera(CameraUpdateFactory.newLatLng(locations.get(1)));
        mMap.setMinZoomPreference(10);
    }
}
