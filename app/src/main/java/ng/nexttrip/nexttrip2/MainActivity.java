package ng.nexttrip.nexttrip2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Olabode Qudus on 11/8/2018.
 */

public class MainActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); //Hide the actionbar

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Splash Screen starting intent after threading for 3 seconds

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_up, R.anim.push_down);
                finish();
            }
        }, 3000);
        //hhh

    }
}
