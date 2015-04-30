package com.example.myproject;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.data.ProfileData;
import com.example.myproject.data.ProfileGps;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Tab_Na extends FragmentActivity implements OnQueryTextListener,
OnMapClickListener,OnMapLongClickListener, OnMarkerDragListener,
OnSeekBarChangeListener, OnCheckedChangeListener  {
	
//	ModifySectionFragment modfrag;//this is a simple class
	//ReminderSectionFragment remfrag;
	Handler tabNavHandler;// message handler

	static ProfileData datasource;
	static ProfileGps profile;
	static List<ProfileGps> values;
	static String ringuri = null;
	//SectionsPagerAdapter mSectionsPagerAdapter;

	static FragmentManager frm;// Its an interface which is used to handle fragment inside the activity

	GoogleMap mMap=null;//Declaring the google map
	List<Address> addresses = null;
	List<MarkerOptions> savedmop = new ArrayList<MarkerOptions>();
	
	static final int RADIUS_MAX = 2000;// setting the maximum value of the radius
	static SeekBar mradiusBar;
	static TextView radiusText;
	static EditText reminder;
	static EditText profile_name;

	String pname = null;
	double plati = 0.0;
	double plongi = 0.0;
	boolean pro_enable = false;
	boolean rem_enable = false;
	int pvolume = 10;
	int pcoverage_radius = 100;
	String preminder = "This is your Reminder";
	static String pringtone = null;
	SectionsPagerAdapter mSectionsPagerAdapter;


	MapSectionFragment mapfrag;// this is the class which is extented by Fragment in below section
	ModifySectionFragment modfrag;
	
	
	static SeekBar pvol;
	static CheckBox pckvib;
	static CheckBox pcksil;
	static CheckBox pckrem;
	static CheckBox pckwifi;
	
	static LatLng setloc = new LatLng(0.0, 0.0);
	LatLng currentmarkposi;
	
	static final int TEN_SECONDS = 10000;
	static final int TEN_METERS = 10;
	static final int TWO_MINUTES = 1000 * 60 * 2;

	static final int UPDATE_ADDRESS = 1;
	static final int UPDATE_LATLNG = 2;
	static final int UPDATE_ADDRESS_MAP = 3;
	static final int UPDATE_LATLNG_MAP = 4;
	static final int UPDATE_ADDRESS_MAP_MARK = 5;
	static final int UPDATE_LATLNG_MAP_MARK = 6;
	static final int UPDATE_VOLUME = 7;
	static final int UPDATE_RADIUS = 8;
	static final int UPDATE_REMINDERCK = 9;
	static final int UPDATE_SILENTCK = 10;
	static final int UPDATE_VIBRATECK = 11;
	static final int UPDATE_WIFICK = 12;
	static final int UPDATE_RINGTONE = 13;
	static final int UPDATE_MAP_TYPE = 14;
	static final int UPDATE_SAVED_MARK = 15;
	static final int UPDATE_SAVED_MARK_POLY = 16;
	static final int UPDATE_DATA = 17;
	
	ViewPager mViewPager;//This is the layout manager which flip the page towards left and right through the pages
	//we generally use ViewPager for it
	ActionBar actionBar;
	private ListView addrlist = null;
	ArrayAdapter<String> addradpt;
	ArrayList<String> addrstrng = new ArrayList<String>();
	static int open_mode;
	static int index;
	static Intent intentRT = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
	static AudioManager audioMod;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		
		addrlist = (ListView) findViewById(R.id.addrlist);
		addradpt = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
			addrstrng);//it is pointing to the in built list as well as the text and we will add the arraylist in the ArrayAdapter

		addrlist.setAdapter(addradpt);

		Intent intent = getIntent();// get intent, action and TWO WAY MAPS(MIME TYPE)
		open_mode = intent.getIntExtra("mode", 0);
		index = intent.getIntExtra("index", 0);
		datasource = new ProfileData(this);
		datasource.open();
		values = datasource.getAllProfiles();
		if(open_mode==0){
			findViewById(R.id.loadinglay).setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		}
			else{
				findViewById(R.id.loadinglay).setVisibility(View.INVISIBLE);
			}
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		
		audioMod=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		actionBar = getActionBar();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab, menu);
		return true;
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMarkerDrag(Marker arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMarkerDragEnd(Marker arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMarkerDragStart(Marker arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapLongClick(LatLng arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}

//----------------------------------------------------------------------------------





public class SectionsPagerAdapter extends FragmentPagerAdapter {

	public SectionsPagerAdapter(FragmentManager fm) {
		super(fm);
		frm = fm;

	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		switch (position) {
		case 0:
			if (modfrag == null)
				modfrag = new ModifySectionFragment();
			if (open_mode != 0) {
				Location loctemp = new Location("tap");
				loctemp.setLatitude(setloc.latitude);
				loctemp.setLongitude(setloc.longitude);
				editUILocation(loctemp);
			} else {
				getclick(null);
			}

			return modfrag;
		case 1:
			if (mapfrag == null)
				mapfrag = new MapSectionFragment();
			return mapfrag;

		default:
			if (remfrag == null)
				remfrag = new ReminderSectionFragment();
			return remfrag;
			/* // */}

	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return 3;
	}

}

//-------------------------------------------------------------------------------------------------------


public static class MapSectionFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_map_view,
				container, false);
		mradiusBar = (SeekBar) rootView.findViewById(R.id.seekBar1);
		radiusText = (TextView) rootView.findViewById(R.id.radius);
		mradiusBar.setEnabled(false);
		mradiusBar.setMax(RADIUS_MAX);
		mradiusBar.setProgress(100);
		if (open_mode != 0) {
			
			mradiusBar.setProgress(profile.getPcoverage_radius());
			
		}
		return rootView;
	}

}


//----------------------------------------------------------------------------------------------------------------



public class ModifySectionFragment extends Fragment {
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_add_location,
				container, false);
		Tab_Na.lati = (TextView) rootView.findViewById(R.id.lati);
		Tab_Na.longi = (TextView) rootView.findViewById(R.id.longi);
		Tab_Na.pvol = (SeekBar) rootView.findViewById(R.id.volumekBar);
		Tab_Na.pckvib = (CheckBox) rootView.findViewById(R.id.checkVib);
		Tab_Na.pcksil = (CheckBox) rootView.findViewById(R.id.checkSil);
		Tab_Na.pckrem = (CheckBox) rootView.findViewById(R.id.checkRem);
		Tab_Na.pckwifi = (CheckBox) rootView.findViewById(R.id.checkWifi);
		Tab_Na.profile_name = (EditText) rootView
				.findViewById(R.id.locationname);
		Tab_Na.profile_name.setText("Profile " + (Tab_Na.values.size() + 1));
		Tab_Na.pcksil.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Tab_Na.pckvib.setChecked(false);
					Tab_Na.pvol.setProgress(0);
				} else {
					Tab_Na.pvol.setProgress(50);
				}
			}
		});
		Tab_Na.pckvib.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Tab_Na.pcksil.setChecked(false);
					Tab_Na.pvol.setProgress(0);
				} else {
					Tab_Na.pvol.setProgress(50);
				}
			}
		});
		
		Tab_Na.pvol.setMax(Tab_Na.audioMod.getStreamMaxVolume(AudioManager.STREAM_RING));
		Tab_Na.pvol.setProgress(Tab_Na.audioMod.getStreamVolume(AudioManager.STREAM_RING));
		Tab_Na.pvol.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (fromUser)
					if (progress == 0) {
						Tab_Na.pcksil.setChecked(true);
					} else {
						Tab_Na.pcksil.setChecked(false);
						Tab_Na.pckvib.setChecked(false);
					}

			}
		});
		if (Tab_Na.open_mode != 0) {
			Tab_Na.profile_name.setText(Tab_Na.profile.getPname());
			Tab_Na.longi.setText(Tab_Na.profile.getPlatitude() + "\n"
					+ Tab_Na.profile.getPlongitude());
			Tab_Na.setloc = new LatLng(Tab_Na.profile.getPlatitude(),
					Tab_Na.profile.getPlongitude());
			Tab_Na.pckvib.setChecked(Tab_Na.profile.getPvolume() == -1);
			Tab_Na.pcksil.setChecked(Tab_Na.profile.getPvolume() == 0);
			if (Tab_Na.pcksil.isChecked() || Tab_Na.pckvib.isChecked()) {
				Tab_Na.pvol.setProgress(0);
			} else {
				Tab_Na.pvol.setProgress(Tab_Na.profile.getPvolume());
			}
			
			Tab_Na.pckrem.setChecked(Tab_Na.profile.isRem_enable());

		}
		(rootView.findViewById(R.id.ringPick))
				.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {

						switch (event.getAction()) {
						case MotionEvent.ACTION_UP:
							v.setBackgroundColor(getResources().getColor(
									android.R.color.transparent));
							launchringPK();
							break;
						case MotionEvent.ACTION_DOWN:
							v.setBackgroundColor(getResources().getColor(
									android.R.color.holo_blue_dark));
							break;
						case MotionEvent.ACTION_CANCEL:
							v.setBackgroundColor(getResources().getColor(
									android.R.color.transparent));
							break;
						case MotionEvent.ACTION_HOVER_EXIT:
							v.setBackgroundColor(getResources().getColor(
									android.R.color.transparent));
							break;/**/
						default:

						}
						return true;
					}
				});

		return rootView;
	}

	protected void launchringPK() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
		intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,
				RingtoneManager.TYPE_RINGTONE);
		intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Set Ringtone");
		if (Tab_Na.pringtone != null) {
			intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,
					Uri.parse(Tab_Na.pringtone));
		}

		else {
			intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,
					(Uri) null);
		}
		startActivityForResult(intent, 212);

	}
}

//---------------------------------------------------------------------------------------------------------------


public static class ReminderSectionFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.reminder_layout,
				container, false);
		reminder = (EditText) rootView.findViewById(R.id.reminderText);
		reminder.setText(R.string.empty_reminder);
		return rootView;
	}
}

public void okclick(View v) {//this is the OK button method of map_view

	LatLng savedloc = null;

	for (int i = 0; i < values.size(); i++) {

		savedloc = new LatLng(values.get(i).getPlatitude(), values.get(i)
				.getPlongitude());
		if (open_mode != 0 && open_mode == values.get(i).getPid()) {
		} else {
			if (getdistancemeter(setloc, savedloc) <= values.get(i)
					.getPcoverage_radius() + mradiusBar.getProgress()) {
				Toast.makeText(
						v.getContext(),
						"Location Conflicts with "
								+ values.get(i).getPname(),
						Toast.LENGTH_LONG).show();

				return;
			}
		}
	}

	pname = profile_name.getText().toString();
	if (setloc == null) {
		Toast.makeText(v.getContext(), "Select location in Map",
				Toast.LENGTH_SHORT).show();
		return;
	}
	plati = setloc.latitude;
	plongi = setloc.longitude;
	pro_enable = true;
	rem_enable = pckrem.isChecked();
	if (pcksil.isChecked()) {
		pvolume = 0;// silent
	} else if (pckvib.isChecked()) {
		pvolume = -1;// vibrate
	} else {
		pvolume = pvol.getProgress();
	}

	pcoverage_radius = mradiusBar.getProgress();

	if (reminder == null) {
		if (open_mode != 0) {
			preminder = profile.getPreminder();
		} else {
			preminder = getString(R.string.empty_reminder);
		}
	} else {
		preminder = reminder.getText().toString();
	}

	if (open_mode == 0) {
		profile = datasource.createProfile(pname, plati, plongi,
				pro_enable, rem_enable, pvolume, pcoverage_radius,
				preminder, pringtone);
	} else {
		ContentValues val = new ContentValues();
		val.put(GPSDataHelper.COLUMN_PNAME, pname);
		val.put(GPSDataHelper.COLUMN_PLATI, plati);
		val.put(GPSDataHelper.COLUMN_PNLONGI, plongi);
		val.put(GPSDataHelper.COLUMN_PISPRO, pro_enable);
		val.put(GPSDataHelper.COLUMN_PISREM, rem_enable);
		val.put(GPSDataHelper.COLUMN_PVOL, pvolume);
		val.put(GPSDataHelper.COLUMN_PRADI, pcoverage_radius);
		val.put(GPSDataHelper.COLUMN_PREM, preminder);
		long i = datasource.updateProfile(val, open_mode);

		Toast.makeText(v.getContext(), pname + " saved", Toast.LENGTH_SHORT)
				.show();
	}

	Intent resultIntent = new Intent();
	if (open_mode == 0) {
		resultIntent.putExtra("added", profile.getPid());
	} else {
		resultIntent.putExtra("edited", open_mode);
		resultIntent.putExtra("added", index);
	}
	setResult(Activity.RESULT_OK, resultIntent);
	datasource.close();
	finish();

}

@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	Intent resultIntent = new Intent();
	setResult(Activity.RESULT_CANCELED, resultIntent);
	datasource.close();
	rvgeotsk.cancel(true);
	
}

private long getdistancemeter(LatLng currentloc2, LatLng savedloc) {
	// TODO Auto-generated method stub
	double pk = (180 / 3.14169);
	double a1 = (currentloc2.latitude / pk);
	double a2 = (currentloc2.longitude / pk);
	double b1 = (savedloc.latitude / pk);
	double b2 = (savedloc.longitude / pk);

	double t1 = (Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2));
	double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
	double t3 = Math.sin(a1) * Math.sin(b1);
	double tt = Math.acos(t1 + t2 + t3);

	return (long) (6366000 * tt);

}

public void getclick(View v) {//this is used in the add_location inorder to get the current location

	Location gpsLocation = null;
	Location networkLocation = null;
	gpsLocation = requestUpdatesFromProvider(LocationManager.GPS_PROVIDER,
			R.string.not_support_gps);
	networkLocation = requestUpdatesFromProvider(
			LocationManager.NETWORK_PROVIDER, R.string.not_support_network);

	// If both providers return last known locations, compare the two and
	// use the better
	// one to update the UI. If only one provider returns a location, use
	// it.
	if (gpsLocation != null && networkLocation != null) {
		editUILocation(getBetterLocation(gpsLocation, networkLocation));
	} else if (gpsLocation != null) {
		editUILocation(gpsLocation);
	} else if (networkLocation != null) {
		editUILocation(networkLocation);
	}

}

private Location requestUpdatesFromProvider(final String provider,
		final int errorResId) {
	Location location = null;
	if (locationManager.isProviderEnabled(provider)) {
		locationManager.requestSingleUpdate(provider, locationListener,
				this.getMainLooper());
		location = locationManager.getLastKnownLocation(provider);
	} else {
		Toast.makeText(this, errorResId, Toast.LENGTH_LONG).show();
	}
	return location;
}





private void editUILocation(Location location) {
	Message.obtain(tabNavHandler, UPDATE_LATLNG,
			new LatLng(location.getLatitude(), location.getLongitude()))
			.sendToTarget();
	if (isGeocoderAvailable)
		doReverseGeocoding(location);

}



public class GPSLocationListener implements LocationListener {

	@Override
	public void onLocationChanged(Location location) {

		editUILocation(location);

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
}
