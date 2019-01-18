package ng.nexttrip.nexttrip2.signin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jkb.vcedittext.VerificationCodeEditText;

import org.jetbrains.annotations.NotNull;

import ng.com.maktay.nexttrip.signup.AuthPresenter;
import ng.nexttrip.nexttrip2.PhoneActivity;
import ng.nexttrip.nexttrip2.R;
import ng.nexttrip.nexttrip2.home.HomeActivity;
import ng.nexttrip.nexttrip2.signup.RegInterface;
import ng.nexttrip.nexttrip2.signup.RegistrationActivity;

/**
 * Created by Olabode Qudus on 11/10/2018.
 */

public class AuthenticationActivity extends AppCompatActivity implements RegInterface.View {
    ProgressDialog progressDialog;
    String phoneNumber;
    VerificationCodeEditText text_verify_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        text_verify_code = findViewById(R.id.text_verify_code);
        phoneNumber = getIntent().getStringExtra("phone_number");

    }
    public void validate_otp_button(View view){
        //TODO: Calling method to validate OTP
        String otp_text = text_verify_code.getText().toString();
        validateOTP(phoneNumber,otp_text);
    }

    @Override
    public void proceedToHome(){
        Intent intent = new Intent(AuthenticationActivity.this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
    }

    void validateOTP(String phoneNumber, String otp){
        AuthPresenter presenter = new AuthPresenter(this);
        presenter.sendOTPRequest(phoneNumber, otp);
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
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    @Override
    public void openOTP() {
        //Not Required
    }

    @Override
    public void showProgress(boolean show) {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Authenticating Credentials Please Wait......");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @NotNull
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void openLogin() {
        startActivity(new Intent(this, PhoneActivity.class));
    }
}
