package ng.nexttrip.nexttrip2.role;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import ng.nexttrip.nexttrip2.PhoneActivity;
import ng.nexttrip.nexttrip2.R;
import ng.nexttrip.nexttrip2.home.HomeActivity;
import ng.nexttrip.nexttrip2.signin.AuthenticationActivity;
import ng.nexttrip.nexttrip2.signup.AuthPresenter;
import ng.nexttrip.nexttrip2.signup.RegInterface;
import ng.nexttrip.nexttrip2.signup.RegistrationActivity;
import ng.nexttrip.nexttrip2.util.GlobalVariable;

/**
 * Created by generalfocus on 24/01/2019.
 */

public class RoleActivity extends AppCompatActivity implements RegInterface.View{
    Spinner staticSpinner;  String text;
    String phone_NumberHolder;

    //EditText Field
    EditText phone;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    Boolean CheckEditText;
    SharedPreferences sh;
    Context con;
    public static final String loginURL = GlobalVariable.API+"/api/v1/user/auth";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);
        staticSpinner = findViewById(R.id.static_spinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.role_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);
        text = staticSpinner.getSelectedItem().toString();
        Intent intent = getIntent();
        phone_NumberHolder = intent.getStringExtra("phone_number");
    }
    @Override
    public void proceedToHome(){
        Intent intent = new Intent(RoleActivity.this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
    }
    public void validate_PhoneNumber(View view)
    {
            //showProgress(true);
            AuthPresenter presenter = new AuthPresenter(this);
            presenter.login(phone_NumberHolder);
            //showProgress(false);

    }

    @Override
    public void validateRole(){
        Intent intent = new Intent(this, RoleActivity.class);
        intent.putExtra("phone_number", phone_NumberHolder);
        startActivity(intent);

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
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.putExtra("phone_number", phone_NumberHolder);
        startActivity(intent);
    }

    @Override
    public void openOTP() {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.putExtra("phone_number", phone_NumberHolder);
        startActivity(intent);
    }

    @Override
    public void showProgress(boolean show) {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Validating Phone Number......");
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
        //Not required
    }

}
/*
 override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role)
        val staticSpinner = findViewById<View>(R.id.static_spinner) as Spinner

        // Create an ArrayAdapter using the string array and a default spinner
        val staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.role_array,
                        android.R.layout.simple_spinner_item)

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        staticSpinner.adapter = staticAdapter

        }
 */