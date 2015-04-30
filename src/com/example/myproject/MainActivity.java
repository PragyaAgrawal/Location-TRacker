package com.example.myproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {
	
	//static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	  //static final LatLng KIEL = new LatLng(53.551, 9.993);
	  private GoogleMap map;
	  double lat , lng;
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	 // GetCurrent location	  
	    
	    LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
	    
	    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0,new LocationListener() 
	    {
			
			public void onLocationChanged(Location location) {}

			public void onProviderDisabled(String provider){}

			public void onProviderEnabled(String provider){}

			public void onStatusChanged(String provider, int status,Bundle extras){}
	});
	    
	      
		//sb.append("\n").append(provider2).append(": ");
		Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location != null) {
			lat = location.getLatitude();
			lng = location.getLongitude();
		}
	    
	    setContentView(R.layout.activity_main);
	    	    
	    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	            .getMap();
	        Marker hamburg = map.addMarker(new MarkerOptions().position(new LatLng(lat, lng))
	            .title("My Location"));
	        Marker kiel = map.addMarker(new MarkerOptions()
	            .position(new LatLng(lat, lng))
	            .title("Kiel")
	            .snippet("Kiel is cool")
	            .icon(BitmapDescriptorFactory
	                .fromResource(R.drawable.ic_launcher)));

	        // Move the camera instantly to hamburg with a zoom of 15.
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));

	        // Zoom in, animating the camera.
	        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	        
	       // Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + saddr + "&daddr=" + daddr));
		}
	 
	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    
	    return true;
	  }

}
