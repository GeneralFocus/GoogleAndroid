package ng.nexttrip.nexttrip2.home;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ng.nexttrip.nexttrip2.R;
import ng.nexttrip.nexttrip2.location.LocationInterface;
import ng.nexttrip.nexttrip2.promo.PromoActivity;
import ng.nexttrip.nexttrip2.location.GpsTracker;
import ng.nexttrip.nexttrip2.rental.MyRentalActivity;
import ng.nexttrip.nexttrip2.trips.TripsActivity;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback
       {
           private GpsTracker gpsTracker;
           private TextView tvLatitude,tvLongitude;
   // SupportMapFragment mMap;
           //   implements NavigationView.OnNavigationItemSelectedListener, LocationInterface,OnMapReadyCallback

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Updating Your Current Location", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
            }
        });*/

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


           public void getLocation(View view){
               gpsTracker = new GpsTracker(HomeActivity.this);
               if(gpsTracker.canGetLocation()){
                   double latitude = gpsTracker.getLatitude();
                   double longitude = gpsTracker.getLongitude();
                   tvLatitude.setText(String.valueOf(latitude));
                   tvLongitude.setText(String.valueOf(longitude));
               }else{
                   gpsTracker.showSettingsAlert();
               }
           }

           @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
        public void onMapReady(GoogleMap googleMap) {
        gpsTracker = new GpsTracker(HomeActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
           // tvLatitude.setText(String.valueOf(latitude));
            //tvLongitude.setText(String.valueOf(longitude));
            LatLng sydney = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(sydney)
                    .title("This Where You Are"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }else{
            gpsTracker.showSettingsAlert();
        }
               // Add a marker in Sydney, Australia,
               // and move the map's camera to the same location.

           }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_trips) {
            Intent intent = new Intent(HomeActivity.this, TripsActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
            // Handle the camera action
        } else if (id == R.id.nav_driving) {
           /* Intent intent = new Intent(HomeActivity.this, GoogleMapPage.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_in);*/

        } else if (id == R.id.nav_promo) {
            Intent intent = new Intent(HomeActivity.this, PromoActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_in);

        } else if (id == R.id.nav_payment) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_settings) {

        } else if(id == R.id.nav_rentals){
            Intent intent = new Intent(HomeActivity.this, MyRentalActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_in);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class MapsMarkerActivity extends AppCompatActivity
            implements OnMapReadyCallback {
        // Include the OnCreate() method here too, as described above.
        @Override
        public void onMapReady(GoogleMap googleMap) {
            // Add a marker in Sydney, Australia,
            // and move the map's camera to the same location.
            LatLng sydney = new LatLng(-33.852, 151.211);
            googleMap.addMarker(new MarkerOptions().position(sydney)
                    .title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }
}

