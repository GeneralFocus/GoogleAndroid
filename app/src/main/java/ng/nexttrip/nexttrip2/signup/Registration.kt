package ng.com.maktay.nexttrip.signup

import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import ng.nexttrip.nexttrip2.signup.RegInterface
import ng.nexttrip.nexttrip2.util.GlobalVariable
import ng.nexttrip.nexttrip2.util.PrefManager
import org.json.JSONObject

//TODO: Use Interface in order to make Presenter Class and Activity Work together

class Registration(var view: RegInterface.View): RegInterface.Presenter {

    val userUrl = "${GlobalVariable.API}/api/v1/user/auth"
    lateinit var prefManager : PrefManager

    override fun login(phone_number: String){
        val url = "$userUrl?phone_number=$phone_number"

        val loginRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
            val status = it.getBoolean("status")
            //TODO: Open OTP Activity
            view.openOTP()
        }, Response.ErrorListener {
            //TODO: An Unexpected Error Occurred. Try Again
            Log.e("Error", it.toString())
            view.showError(it.message!!)
        })

        Volley.newRequestQueue(view.getContext()).add(loginRequest)
    }

    override fun register(name: String, email: String, phone_number: String, payment_method: String){

        val param = HashMap<String, String>()
        param["name"] = name
        param["email"] = email
        param["phone_number"] = phone_number
        param["payment_method"] = payment_method

        val jsonParam = JSONObject(param)

        val userRequest = JsonObjectRequest(Request.Method.POST, userUrl, jsonParam, Response.Listener {

            val status = it.getBoolean("status")
            if (status){
                // TODO: User has been registered successfully. Pass the value to the View. Use Interface to Link Together
                //TODO: Registration successfull. Open Login Activity for user to enter Phone Number
            }
            else{
                val message = it.getString("message")
                //TODO: Display message in View. Was not able to register User
            }

        }, Response.ErrorListener {
            val errorMessage = it.message

            //TODO: Display an error message to user. An unexpected Error occurred. View Log for workaround

            Log.e("Error", it.toString())
        })

        Volley.newRequestQueue(view.getContext()).add(userRequest)
    }


    override fun sendOTPRequest(phone_number: String, otp_code: String){

        // Send OTP to the endpoint

        val param = HashMap<String, String>()
        param["otp_code"] = otp_code
        param["phone_number"] = phone_number

        val jsonParam = JSONObject(param)

        val otpRequest = JsonObjectRequest(Request.Method.PUT, userUrl, jsonParam, Response.Listener {
            val status = it.getBoolean("status")
            if (status){
                //TODO: Response was successful. Token is correct
                //TODO: Open Home Activity

                prefManager = PrefManager(view.getContext())
                prefManager.saveAccessToken(it.getJSONObject("data").getString("token"))
                prefManager.saveRefreshToken(it.getJSONObject("data").getString("refresh_token"))
            }
            else{
                //TODO: Invalid Token. Resend Request
            }
        }, Response.ErrorListener {
            //TODO: An unexpected error occurred
        })

        Volley.newRequestQueue(view.getContext()).add(otpRequest)
    }
}