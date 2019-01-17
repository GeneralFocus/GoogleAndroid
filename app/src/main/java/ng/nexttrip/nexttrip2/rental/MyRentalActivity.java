package ng.nexttrip.nexttrip2.rental;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import ng.nexttrip.nexttrip2.R;

/**
 * Created by generalfocus on 20/12/2018.
 */

public class MyRentalActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrips);

        TabHost tabHost = getTabHost();

        // Tab for Photos
        TabHost.TabSpec historyspec = tabHost.newTabSpec("History");
        // setting Title and Icon for the Tab

        historyspec.setIndicator("History");//getResources().getDrawable(R.drawable.icon_photos_tab)
        Intent historyIntent = new Intent(this, RentalHistoryActivity.class);
        historyspec.setContent(historyIntent);

        // Tab for Songs
        TabHost.TabSpec reservationspec = tabHost.newTabSpec("Reservation");
        reservationspec.setIndicator("Reservation");
        Intent reservationIntent = new Intent(this, RentalReservationActivity.class);
        reservationspec.setContent(reservationIntent);

        // Adding all TabSpec to TabHost
        tabHost.addTab(historyspec); // Adding History tab
        tabHost.addTab(reservationspec); // Adding Reservation tab
    }
}