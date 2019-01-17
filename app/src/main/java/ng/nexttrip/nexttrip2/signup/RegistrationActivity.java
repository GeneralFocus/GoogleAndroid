package ng.nexttrip.nexttrip2.signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ng.nexttrip.nexttrip2.PhoneActivity;
import ng.nexttrip.nexttrip2.R;
import ng.nexttrip.nexttrip2.signin.AuthenticationActivity;
import ng.nexttrip.nexttrip2.util.GlobalVariable;
import ng.nexttrip.nexttrip2.util.JSONParser;
import ng.nexttrip.nexttrip2.util.UserInfo;
import ng.nexttrip.nexttrip2.util.Util;

/**
 * Created by Olabode Qudus on 11/10/2018.
 */

public class RegistrationActivity extends AppCompatActivity {
    EditText reg_firstname, reg_lastname, reg_email;
    String  firstname_Holder, lastname_Holder, email_Holder;
    String phone,name,email;
    JSONParser jparser=new JSONParser();
    SharedPreferences sh;
    Context con;
    Boolean CheckEditText;
    public static final String regiURL = GlobalVariable.API+"/api/v1/user/auth";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        reg_firstname = findViewById(R.id.reg_first_name);
        reg_lastname = findViewById(R.id.reg_lastname);
        reg_email = findViewById(R.id.reg_email);
       //TODO get the phone number passed from the PhoneActivity To Avoid Input Replication
    }

    //TODO After registration take user to the login page
    public void proceedButton(View view){
        CheckEditTextIsEmptyOrNot();
        if (CheckEditText) {
            Registration reg = new Registration();
            reg.execute();
          //  new Registration.execute();
        } else {
            Toast.makeText(RegistrationActivity.this, "All Fields Are Required", Toast.LENGTH_LONG).show();
        }
       /* Intent intent = new Intent(RegistrationActivity.this,
                AuthenticationActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_in);*/

    }
    public void CheckEditTextIsEmptyOrNot() {
        // Getting values from EditText.
        firstname_Holder = reg_firstname.getText().toString().trim();
        lastname_Holder =reg_lastname.getText().toString().trim();
        email_Holder = reg_email.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if (TextUtils.isEmpty(firstname_Holder) || TextUtils.isEmpty(lastname_Holder) || TextUtils.isEmpty(email_Holder)) {
            // If EditText is empty then set variable value as False.
            CheckEditText = false;
        } else {
            // If EditText is filled then set variable value as True.
            CheckEditText = true;
        }
    }
    class Registration extends AsyncTask<String,String, String> {


        ProgressDialog pDialog;
        String toastText="Internet Problem";
        String regiresult="";
        int success=0;
        int error=0;
        String errmsg="Server is down";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(con);
            pDialog.setMessage("Registration is processing......");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            String res="no res";
             name= reg_firstname.getText().toString().trim() + " " + reg_lastname.getText().toString().trim();
            email=reg_email.getText().toString().trim();
            Intent intent = getIntent();
            phone = intent.getStringExtra("phone_number");

            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("phone_number", phone));
            params.add(new BasicNameValuePair("user_type", "client"));
            params.add(new BasicNameValuePair("category", "client"));

            UserInfo.setEmail(email);
            UserInfo.setName(name);
            UserInfo.setPhonenumber(phone);

            try {
                JSONObject jobj= jparser.makeHttpRequest(regiURL, "POST", params);
                success=jobj.getInt("success");
                res=jobj.toString();
                if(success==1){
                    toastText="Registration complete";
                }else if(success==0){
                    regiresult=jobj.getString("message");
                    toastText="Problem in registration";
                }else{
                    toastText="Link not found";
                }

            } catch (JSONException e) {
                toastText="There are some problem";
                e.printStackTrace();
            }catch( Exception e){
                error=1;
            }


            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Toast.makeText(MainActivity.this, s+" "+result, Toast.LENGTH_SHORT).show();
            pDialog.dismiss();
            Log.e("XXX",result);
            if(error==1){
                Toast.makeText(con,errmsg, Toast.LENGTH_SHORT).show();
                if(Util.isConnectingToInternet(con)){
                    Toast.makeText(con, "Server is down. Please try again later", Toast.LENGTH_SHORT).show();
                  /*  registrationResult.setText("Server is down. Please try again later");
                    registrationResult.setVisibility(View.VISIBLE);*/
                }else{
                    Util.showNoInternetDialog(con);
                }
                return;
            }

            if(success==0){
                Toast.makeText(con, regiresult, Toast.LENGTH_SHORT).show();
               /* registrationResult.setText(regiresult);
                registrationResult.setVisibility(View.VISIBLE);*/
            }else if (success==1){

				/*GetUserData data=new GetUserData();
				data.execute();

				Intent i=new Intent(con, MainScreenActivity.class);
				startActivity(i);

				registrationResult.setVisibility(View.GONE);
				finish();*/
				//TODO we going back to login page after reg
                startActivity(new Intent(con, PhoneActivity.class));
                Toast.makeText(con,toastText, Toast.LENGTH_SHORT).show();
                finish();



            }
        }

    }
}
