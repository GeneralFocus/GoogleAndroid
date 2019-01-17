package ng.nexttrip.nexttrip2.signin;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import ng.com.maktay.nexttrip.signup.Registration;
import ng.nexttrip.nexttrip2.PhoneActivity;
import ng.nexttrip.nexttrip2.R;
import ng.nexttrip.nexttrip2.home.HomeActivity;
import ng.nexttrip.nexttrip2.signup.RegInterface;
import ng.nexttrip.nexttrip2.signup.RegistrationActivity;

/**
 * Created by Olabode Qudus on 11/10/2018.
 */

public class AuthenticationActivity extends AppCompatActivity implements RegInterface.View {

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

    void login(String phoneNumber){
        //TODO: Call this method to Login
        Registration presenter = new Registration(this);
        presenter.login(phoneNumber);
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
    public void openRegister() {
        startActivity(new Intent(AuthenticationActivity.this, RegistrationActivity.class));
    }

    @Override
    public void openOTP() {
        //TODO: Open OTP Activity
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

    @Override
    public void openLogin() {
    }
}
