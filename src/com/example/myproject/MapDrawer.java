package com.example.myproject;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

public class MapDrawer {


private static int EARTH_RADIUS = 6371000;


private LatLng getPoint(LatLng center, int radius, double angle) {
    // Get the coordinates of a circle point at the given angle
    double east = radius * Math.cos(angle);//it will set the radius of the latitude
    double north = radius * Math.sin(angle);//it will set the radius of the longitude

    double cLat = center.latitude;
    double cLng = center.longitude;
    double latRadius = EARTH_RADIUS * Math.cos(cLat / 180 * Math.PI);

    double newLat = cLat + (north / EARTH_RADIUS / Math.PI * 180);
    double newLng = cLng + (east / latRadius / Math.PI * 180);

    return new LatLng(newLat, newLng);
}

public PolygonOptions drawCircle(LatLng center, int radius) {
    // Clear the map to remove the previous circle
   
    // Generate the points
    List<LatLng> points = new ArrayList<LatLng>();
    int totalPonts = 20; // number of corners of the pseudo-circle(finite topology space)
    for (int i = 0; i < totalPonts; i++) {
        points.add(getPoint(center, radius, i*2*Math.PI/totalPonts));
    }
   
    // Create and return the polygon
    return (new PolygonOptions().addAll(points).geodesic(true).strokeWidth(2).strokeColor(Color.TRANSPARENT).
    		fillColor(Color.HSVToColor(100, new float[] {234, 1, 1})));
// geodesic is the shortest path between two points on the Earth's surface. The geodesic curve is constructed assuming
    //the Earth is a sphere if true then it means that geodesic is been drawn
    //strokeWidth it is been used to set the width/distance of the given location 
    }
}
