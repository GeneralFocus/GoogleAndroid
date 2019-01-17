package ng.nexttrip.nexttrip2.trips;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import ng.nexttrip.nexttrip2.R;

public class TripsActivity extends AppCompatActivity implements TripInterface.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
    }

    @Override
    public void showError(@NotNull String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(@NotNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress(boolean show) {
        //TODO: Show Progress
    }

    @NotNull
    @Override
    public Context getContext() {
        return this;
    }
}
