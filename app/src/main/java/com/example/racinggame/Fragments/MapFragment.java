package com.example.racinggame.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.racinggame.Logic.GameManager;
import com.example.racinggame.Models.ScoreList;
import com.example.racinggame.R;
import com.example.racinggame.Utilities.MySP;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MapFragment extends Fragment {

    public MapFragment() {
        // Required empty public constructor
    }

    private GoogleMap myMap;
    private ArrayList<LatLng> locations = new ArrayList<LatLng>();
    private Marker[] mMarkers;
    ScoreList scoreListFromJson;
    FrameLayout mapView;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            myMap = googleMap;

            mMarkers = new Marker[locations.size()];
            for (int i = 0; i < locations.size(); i++) {
                mMarkers[i] = myMap.addMarker(new MarkerOptions().position(locations.get(i)).title("Marker " + (i+1)));
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        findViews(view);
        initData();

        return view;
    }

    private void initData() {
        String fromSP =  MySP.getInstance().getString(GameManager.SCORE_KEY,"");
        if(fromSP.isEmpty() == true)
            scoreListFromJson = new ScoreList();
        else
            scoreListFromJson = new Gson().fromJson(fromSP, ScoreList.class);
        Log.d("From JSON", scoreListFromJson.toString());

        for (int i = 0; i < scoreListFromJson.getScores().size(); i++) {
                locations.add(new LatLng(scoreListFromJson.getScores().get(i).getmLatitude(), scoreListFromJson.getScores().get(i).getmLongitude()));
        }
    }

    private void findViews(View view) {
        mapView = view.findViewById(R.id.map);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    public void zoomOnUserLocation(double latitude, double longitude) {
        LatLng location = new LatLng(latitude, longitude);
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
    }
}