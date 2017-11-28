package com.edwinacubillos.agendaapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends MenuActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    float lat, longit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_maps);

        //setContentView(R.layout.activity_main);
        FrameLayout fl = (FrameLayout) findViewById(R.id.frame);
        getLayoutInflater().inflate(R.layout.activity_maps,fl);

        Bundle data = getIntent().getExtras();
        if (data != null){
            lat = data.getFloat("lat");
            longit = data.getFloat("longit");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera
    /*    LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        LatLng eafit = new LatLng(lat, longit);
        mMap.addMarker(new MarkerOptions().
                position(eafit).
                title("Universidad EAFIT").
                snippet("Aplicaciones Móviles"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eafit,15));
    }
}
