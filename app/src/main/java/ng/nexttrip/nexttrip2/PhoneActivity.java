package ng.nexttrip.nexttrip2;

/**
 * Created by Olabode Qudus on 11/10/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ng.nexttrip.nexttrip2.signin.AuthenticationActivity;
import ng.nexttrip.nexttrip2.signup.RegistrationActivity;
import ng.nexttrip.nexttrip2.util.GlobalVariable;
import ng.nexttrip.nexttrip2.util.JSONParser;
import ng.nexttrip.nexttrip2.util.UserInfo;
import ng.nexttrip.nexttrip2.util.Util;


public class PhoneActivity extends AppCompatActivity {
    //ec2-52-15-130-95.us-east-2.compute.amazonaws.com
    //Create string variable to hold the EditText Value.
    String phone_NumberHolder;

    //EditText Field
    EditText phone;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Storing server url into String variable.
   // String HttpUrl = "http://ec2-52-15-130-95.us-east-2.compute.amazonaws.com/api/v1/user/auth";

    Boolean CheckEditText;
    JSONParser jparser=new JSONParser();
    SharedPreferences sh;
    Context con;
    public static final String loginURL = GlobalVariable.API+"/api/v1/user/auth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonenumber);
        con = PhoneActivity.this;
        sh=getSharedPreferences("NEXT_TRIP", MODE_PRIVATE);
        phone = findViewById(R.id.authenticate_phone);
        progressDialog = new ProgressDialog(PhoneActivity.this);
    }
    public void validate_PhoneNumber(View view)
    {
        CheckEditTextIsEmptyOrNot();
        if (CheckEditText) {
            new Login().execute("client");
        } else {
            Toast.makeText(PhoneActivity.this, "Input your phone number", Toast.LENGTH_LONG).show();
        }
    }
    public void CheckEditTextIsEmptyOrNot() {
        // Getting values from EditText.
        phone_NumberHolder = phone.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if (TextUtils.isEmpty(phone_NumberHolder)) {
            // If EditText is empty then set variable value as False.
            CheckEditText = false;
        } else {
            // If EditText is filled then set variable value as True.
            CheckEditText = true;
        }
    }



    class Login extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;
        String s="";
        int success=-1;
        int error=0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(con);
            pDialog.setMessage("Validating Phone Number......");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        boolean driver=false;
        String phone_number="";

        @Override
        protected String doInBackground(String... st) {


          /*  if(st!=null && st[0].equals("driver")){
                email=drloginEmail.getText().toString();
                password=drloginPassword.getText().toString();
                driver=true;
            }else{*/
            phone_number = phone .getText().toString().trim();
         //   }

            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("phone_number",phone_number));
            params.add(new BasicNameValuePair("user_type", "client"));

            UserInfo.setPhonenumber(phone_number);

            try {
                JSONObject jobj=jparser.makeHttpRequest(loginURL, "POST", params);
                success=jobj.getInt("success");
                s=jobj.getString("message");

                if(success==1){
                    JSONObject job=jobj.getJSONArray("info").getJSONObject(0);
                    UserInfo.setName(job.getString("name"));
                    UserInfo.setPhonenumber(job.getString("phone_number"));
                    UserInfo.setId(job.getString("id"));
                    Intent intent = new Intent(PhoneActivity.this, AuthenticationActivity.class);
                    intent.putExtra("phone_number", phone_number);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                }else {
                    Toast.makeText(con, "Phone Number Doesn't Exist Please Register..", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PhoneActivity.this, RegistrationActivity.class);
                    intent.putExtra("phone_number", phone_number);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                }

            } catch (JSONException e) {
                //	e.printStackTrace();
                error=1;
            }catch(Exception e){
                error=1;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();

            if(error==1){
                if(Util.isConnectingToInternet(con)){
                    Toast.makeText(con, "No Response Register", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PhoneActivity.this, RegistrationActivity.class);
                    intent.putExtra("phone_number", phone_number);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                }else
                    Util.showNoInternetDialog(con);
                return;
            }

            if(success==0){
                //Toast.makeText(con, s, Toast.LENGTH_LONG).show();
            }else if(success==1){
				/*GetUserData data=new GetUserData();
				data.execute();*/

                SharedPreferences.Editor edit= sh.edit();
                edit.putString("phone_number", phone_number);
                edit.putBoolean("type", driver);
                edit.commit();


               /* Intent i=new Intent(con, DriverPositionActivity.class);
                if(driver)
                    i=new Intent(con, DriverActivity.class);

                startActivity(i);
                //Toast.makeText(con, s, Toast.LENGTH_SHORT).show();
                finish();*/
            }
        }
    }
}
