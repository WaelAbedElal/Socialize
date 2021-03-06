package com.abs.samih.samples;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alsalam.sclzroot.Activities.MainHomeActivity;
import com.alsalam.sclzroot.TableManager.EventTbl;
import com.alsalam.sclzroot.handlers.EventsHandler;
import com.alsalam.sclzroot.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by samih on 27/02/2016.
 */
public class MapListFragment extends Fragment implements OnMapReadyCallback, EventsHandler,GoogleMap.OnMarkerClickListener, AdapterView.OnItemClickListener {
    private MapView mapView;
    private GoogleMap mMap;
    private ListView listView;
    private TextView tvMonth;
    SupportMapFragment mapFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map_home, container, false);
        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


       // mapView = (MapView) view.findViewById(R.id.map);
        tvMonth = (TextView) view.findViewById(R.id.tvMonth);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        tvMonth.setText(month_name);

        listView = (ListView) view.findViewById(R.id.lstvEvetnts);
        listView.setOnItemClickListener(this);


        return view;
    }

//


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        ((MainHomeActivity) getActivity()).refreshEventsToListAndMap(listView, R.layout.event_card_itm, mMap);
        mMap.setOnMarkerClickListener(this);
////        // Add a marker in Sydney and move the camera
////        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
////            EventTbl eventTbl=(EventTbl)listView.getAdapter().getItem(i);
////            ///to  Get gps loc-> marker->add to map+ listener for markers
////           // LatLng loc = new LatLng(eventTbl.getLat(),eventTbl.getLang());
////            LatLng loc = new LatLng(32.9943511, 35.1472984);
//////            MarkerOptions markerOptions=new MarkerOptions().position(loc).title(eventTbl.getTitle());
//////            mMap.addMarker(markerOptions);
//////            if(i==0)
//////            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 13));
////
////            MarkerOptions markerOptions = new MarkerOptions();
////            markerOptions.position(loc);
////            markerOptions.title(eventTbl.getTitle());
////            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
////                @Override
////                public boolean onMarkerClick(Marker marker) {
////
////                    return true;
////                }
////            });
////            mMap.addMarker(markerOptions);
////
////            // Locate the first location
////            if (i == 0) {
////
////                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 13));
////            }
////
////
////        }
////
//        EventTbl eventTbl= (EventTbl) listView.getAdapter().getItem(0);
//        Log.d("LNG",eventTbl.getLat()+","+eventTbl.getLang());
//        LatLng sydney = new LatLng(eventTbl.getLat(), eventTbl.getLang());
//        mMap.addMarker(new MarkerOptions().position(sydney).title("מקיף השלום -דנון"));
////        LatLng sydney1 = new LatLng(33.0155026, 35.1359451);
////        mMap.addMarker(new MarkerOptions().position(sydney1).title("נהריה"));
////        LatLng sydney2 = new LatLng(32.9843018, 35.1031843);
////        mMap.addMarker(new MarkerOptions().position(sydney2).title("מזרעה"));
////        LatLng sydney3 = new LatLng(33.0888807, 35.2339193);
////        mMap.addMarker(new MarkerOptions().position(sydney3).title("קיבוץ עברון"));
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
       // mMap.setMyLocationEnabled(true);
       // if (i == 0)
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));
    }

    @Override
    public void onJoinEvent(JoinEventDialog e) {
        Toast.makeText(getContext()," MapListFragment here calling join activity",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 13));


        return  true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EventTbl eventTbl= (EventTbl) parent.getAdapter().getItem(position);
        LatLng latLng=new LatLng(eventTbl.getLat(),eventTbl.getLang());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
    }
}
