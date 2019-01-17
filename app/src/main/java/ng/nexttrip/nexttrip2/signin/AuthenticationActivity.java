package ng.nexttrip.nexttrip2.signin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import ng.nexttrip.nexttrip2.PhoneActivity;
import ng.nexttrip.nexttrip2.R;
import ng.nexttrip.nexttrip2.home.HomeActivity;
import ng.nexttrip.nexttrip2.signup.RegistrationActivity;

/**
 * Created by Olabode Qudus on 11/10/2018.
 */

public class AuthenticationActivity extends AppCompatActivity {
    //Todo this where user input the OTP sent to his/her phone number....

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

    }
    public void proceedToHome(View view){
        Intent intent = new Intent(AuthenticationActivity.this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
    }

}
