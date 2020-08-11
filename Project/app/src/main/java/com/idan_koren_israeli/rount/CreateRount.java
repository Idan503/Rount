package com.idan_koren_israeli.rount;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateRount extends FragmentActivity {

    // implements OnMapReadyCallback, OnStreetViewPanoramaReadyCallback

    // George St, Sydney
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private GoogleMap mMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_rount);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //        .findFragmentById(R.id.create_rount_map_fragment);

        // Obtain street view fragment
        //SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
        //        (SupportStreetViewPanoramaFragment)
        //                getSupportFragmentManager().findFragmentById(R.id.create_rount_streetview_fragment);

        /*
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
            @Override
            public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
                // Set the panorama view to sydney - on startup (savedInstance is null
                if(savedInstanceState == null){
                    streetViewPanorama.setPosition(SYDNEY);
                    System.out.println("Set Sydney panorama view!");
                }
            }
        });

        mapFragment.getMapAsync(this);
    }
         */

        /*
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     /*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        }
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(SYDNEY).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(SYDNEY));

    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    */
    }
}