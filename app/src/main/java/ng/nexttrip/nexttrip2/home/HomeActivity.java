package ng.nexttrip.nexttrip2.home;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ng.nexttrip.nexttrip2.R;
import ng.nexttrip.nexttrip2.payment.PaymentActivity;
import ng.nexttrip.nexttrip2.promo.PromoActivity;
import ng.nexttrip.nexttrip2.location.GpsTracker;
import ng.nexttrip.nexttrip2.rental.MyRentalActivity;
import ng.nexttrip.nexttrip2.trips.TripsActivity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.widget.TextView;

import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, LocationListener
       {
           protected Context context;  private GoogleMap mMap;
        //   LocationManager locationManager;
           String mprovider;
          double latitude, longitude;
           //Todo From AndroidHive
           LocationManager locationManager;
           Location loc;
           ArrayList<String> permissions = new ArrayList<>();
           ArrayList<String> permissionsToRequest;
           ArrayList<String> permissionsRejected = new ArrayList<>();
           boolean isGPS = false;
           boolean isNetwork = false;
           boolean canGetLocation = true;
           final String TAG = "GPS";
           private final static int ALL_PERMISSIONS_RESULT = 101;
           private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
           private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
           //Todo end Here



           @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

          /**    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
               Criteria criteria = new Criteria();

               mprovider = locationManager.getBestProvider(criteria, false);

               if (mprovider != null && !mprovider.equals("")) {
                   if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                       return;
                   }
                   Location location = locationManager.getLastKnownLocation(mprovider);
                   locationManager.requestLocationUpdates(mprovider, 3000, 1, this);

                   if (location != null)
                       onLocationChanged(location);
                   else
                       Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
               }**/

          //Todo form here
               locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
               isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
               isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

               permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
               permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
               permissionsToRequest = findUnAskedPermissions(permissions);

               if (!isGPS && !isNetwork) {
                   Log.d(TAG, "Connection off");
                   showSettingsAlert();
                   getLastLocation();
               } else {
                   Log.d(TAG, "Connection on");
                   // check permissions
                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                       if (permissionsToRequest.size() > 0) {
                           requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                                   ALL_PERMISSIONS_RESULT);
                           Log.d(TAG, "Permission requests");
                           canGetLocation = false;
                       }
                   }

                   // get location
                   getLocation();
               }

                   //Todo To here
Toast.makeText(getApplicationContext(),"lat: "+latitude + "long: "+longitude,Toast.LENGTH_LONG).show();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

           @Override
           public void onLocationChanged(Location location) {
               Log.d(TAG, "onLocationChanged");
               updateUI(location);
           }

           @Override
           public void onStatusChanged(String s, int i, Bundle bundle) {}

           @Override
           public void onProviderEnabled(String s) {
               getLocation();
           }

           @Override
           public void onProviderDisabled(String s) {
               if (locationManager != null) {
                   locationManager.removeUpdates(this);
               }
           }
           private void getLocation() {
               try {
                   if (canGetLocation) {
                       Log.d(TAG, "Can get location");
                       if (isGPS) {
                           // from GPS
                           Log.d(TAG, "GPS on");
                           locationManager.requestLocationUpdates(
                                   LocationManager.GPS_PROVIDER,
                                   MIN_TIME_BW_UPDATES,
                                   MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                           if (locationManager != null) {
                               loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                               if (loc != null)
                                   updateUI(loc);
                           }
                       } else if (isNetwork) {
                           // from Network Provider
                           Log.d(TAG, "NETWORK_PROVIDER on");
                           locationManager.requestLocationUpdates(
                                   LocationManager.NETWORK_PROVIDER,
                                   MIN_TIME_BW_UPDATES,
                                   MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                           if (locationManager != null) {
                               loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                               if (loc != null)
                                   updateUI(loc);
                           }
                       } else {
                           loc.setLatitude(0);
                           loc.setLongitude(0);
                           updateUI(loc);
                       }
                   } else {
                       Log.d(TAG, "Can't get location");
                   }
               } catch (SecurityException e) {
                   e.printStackTrace();
               }
           }
           private void getLastLocation() {
               try {
                   Criteria criteria = new Criteria();
                   String provider = locationManager.getBestProvider(criteria, false);
                   Location location = locationManager.getLastKnownLocation(provider);
                   Log.d(TAG, provider);
                   Log.d(TAG, location == null ? "NO LastLocation" : location.toString());
               } catch (SecurityException e) {
                   e.printStackTrace();
               }
           }

           private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
               ArrayList result = new ArrayList();

               for (String perm : wanted) {
                   if (!hasPermission(perm)) {
                       result.add(perm);
                   }
               }

               return result;
           }

           private boolean hasPermission(String permission) {
               if (canAskPermission()) {
                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                       return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
                   }
               }
               return true;
           }

           private boolean canAskPermission() {
               return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
           }

           @TargetApi(Build.VERSION_CODES.M)
           @Override
           public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
               switch (requestCode) {
                   case ALL_PERMISSIONS_RESULT:
                       Log.d(TAG, "onRequestPermissionsResult");
                       for (String perms : permissionsToRequest) {
                           if (!hasPermission(perms)) {
                               permissionsRejected.add(perms);
                           }
                       }

                       if (permissionsRejected.size() > 0) {
                           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                               if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                                   showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                   new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                               requestPermissions(permissionsRejected.toArray(
                                                       new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                           }
                                       }
                                   });
                                   return;
                               }
                           }
                       } else {
                           Log.d(TAG, "No rejected permissions.");
                           canGetLocation = true;
                           getLocation();
                       }
                       break;
               }
           }

           public void showSettingsAlert() {
               AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
               alertDialog.setTitle("GPS is not Enabled!");
               alertDialog.setMessage("Do you want to turn on GPS?");
               alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                       Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                       startActivity(intent);
                   }
               });

               alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                   }
               });

               alertDialog.show();
           }

           private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
               new AlertDialog.Builder(HomeActivity.this)
                       .setMessage(message)
                       .setPositiveButton("OK", okListener)
                       .setNegativeButton("Cancel", null)
                       .create()
                       .show();
           }

           private void updateUI(Location loc) {
               Log.d(TAG, "updateUI");
               latitude = loc.getLatitude();
               longitude = loc.getLongitude();
             //  tvLatitude.setText(Double.toString(loc.getLatitude()));
               //tvLongitude.setText(Double.toString(loc.getLongitude()));
               //tvTime.setText(DateFormat.getTimeInstance().format(loc.getTime()));
           }

           @Override
           protected void onDestroy() {
               super.onDestroy();
               if (locationManager != null) {
                   locationManager.removeUpdates(this);
               }
           }

        /**   @Override
           public void onLocationChanged(Location location) {

               longitude=  location.getLongitude();
               latitude = location.getLatitude();
           }

           @Override
           public void onStatusChanged(String s, int i, Bundle bundle) {

           }

           @Override
           public void onProviderEnabled(String s) {

           }

           @Override
           public void onProviderDisabled(String s) {

           }**/
         /*  @Override
           public void onMapReady(GoogleMap googleMap) {
               // Add a marker in Sydney, Australia,
               // and move the map's camera to the same location.
            // LatLng sydney = new LatLng(-33.852, 151.211);
               LatLng sydney = new LatLng(6.4698,3.5852);
              // LatLng sydney = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
               googleMap.addMarker(new MarkerOptions().position(sydney)
                       .title("This ur current location"));
               googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
           }*/
         @Override
         public void onMapReady(GoogleMap googleMap) {

             mMap = googleMap;

             mMap.getUiSettings().setZoomControlsEnabled(true);
             mMap.setMinZoomPreference(11);

           //  LatLng placeLoc = new LatLng(6.4698, 3.5852);
             LatLng placeLoc = new LatLng(latitude, longitude);

             MarkerOptions markerOptions = new MarkerOptions();
             markerOptions.position(placeLoc)
                     .title("Your Current Location")
                     .snippet("Book Your Next Trip")
                     .icon(BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_GREEN));

             Marker m = mMap.addMarker(markerOptions);
             mMap.moveCamera(CameraUpdateFactory.newLatLng(placeLoc));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        } /*else if (id == R.id.nav_driving) {
            Intent intent = new Intent(HomeActivity.this, BookRdeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_in);

        } */else if (id == R.id.nav_promo) {
            Intent intent = new Intent(HomeActivity.this, PromoActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_in);

        } else if (id == R.id.nav_payment) {
            Intent intent = new Intent(HomeActivity.this, PaymentActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_in);

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

    //From Android Hive..

}

