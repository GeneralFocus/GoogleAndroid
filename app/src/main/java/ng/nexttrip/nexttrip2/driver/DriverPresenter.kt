package ng.nexttrip.nexttrip2.drive

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import ng.nexttrip.nexttrip2.driver.DriverInterface
import ng.nexttrip.nexttrip2.util.GlobalVariable
import ng.nexttrip.nexttrip2.util.PrefManager
import ng.nexttrip.nexttrip2.pojo.Driver
import org.json.JSONObject

class DriverPresenter(var view: DriverInterface.View): DriverInterface.Presenter {

    val driverUrl = "${GlobalVariable.API}/api/v1/driver/auth"

    override fun login(username: String, password: String){
        val url = "$driverUrl?username=$username&password=$password"

        val driverAuth = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {

            val status = it.getBoolean("status")
            if (status){
                // TODO: Login successful. Open DriverInterface End
                val prefManager = PrefManager(view.getContext())
                prefManager.saveRefreshToken(it.getJSONObject("data").getString("refresh_token"))
                prefManager.saveAccessToken(it.getJSONObject("data").getString("access_token"))
            }
            else{
                // TODO: Unable to Login. Show the message
                val message = it.getString("message")
            }

        }, Response.ErrorListener {
            // TODO: Show Error Message via view
        })

        Volley.newRequestQueue(view.getContext()).add(driverAuth)
    }

    override fun register(name: String, username: String, password: String, email: String,
                          phone_number: String, car_model: String, registration_number: String,
                          car_color: String) {

        val param = HashMap<String, String>()
        param["name"] = name
        param["username"] = username
        param["password"] = password
        param["email"] = email
        param["phone_number"] = phone_number
        param["car_model"] = car_model
        param["registration_model"] = registration_number
        param["car_color"] = car_color

        val jsonParam = JSONObject(param)

        val driverReg = JsonObjectRequest(Request.Method.POST, driverUrl, jsonParam, Response.Listener {
            val status = it.getBoolean("status")
            if (status){
                //TODO: AuthPresenter Successful
            }
            else{
                // TODO: Could not register. Show error Message
                val message = it.getString("message")
            }

        }, Response.ErrorListener {
            // TODO: An error occurred. Log the error
        })

        Volley.newRequestQueue(view.getContext()).add(driverReg)
    }
}