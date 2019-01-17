package ng.nexttrip.nexttrip2.location

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import ng.nexttrip.nexttrip2.pojo.Location
import ng.nexttrip.nexttrip2.util.GlobalVariable
import ng.nexttrip.nexttrip2.util.LocationTracker
import ng.nexttrip.nexttrip2.util.PrefManager
import org.json.JSONObject
import java.util.*

//TOdO: Pass View Object to Presenter
class Location(var view: LocationInterface.View) {

    val url = "${GlobalVariable.API}/api/v1/location"

    // Set Location For DriverInterface
    fun set_location(driver_id: String){

        val location = LocationTracker(view.getContext())

        val lat = location.getLatitude()
        val lng = location.getLongitude()

        val param = HashMap<String, String>()
        param["driver_id"] = driver_id
        //param["lat"] = lat
        //param["lng"] = lng

        val jsonParam = JSONObject(param)

        val locationRequest = JsonObjectRequest(Request.Method.POST, url, jsonParam, Response.Listener {
            val status = it.getBoolean("status")

            if (status){
                // TODO: Location was set successfully.
            }
            else {
                //TODO: Unable to set location. Cause: DriverInterface Details not found
            }
        }, Response.ErrorListener {
            //TODO: An Error Occurred. Show Message
        })

        Volley.newRequestQueue(view.getContext()).add(locationRequest)

    }

    val onlineDrivers = ArrayList<Location>()


    //Get Online Drivers Location
    fun getLocation() {

        val getRequest = JsonObjectRequest(Request.Method.GET, url, null,  Response.Listener {
            val status = it.getBoolean("status")

            if (status){
                // TODO: Location was set successfully.
                val data = it.getJSONArray("data")
                for (i in 0 until data.length()){
                    val single_object = data.getJSONObject(i)

                    val location_id = single_object.getInt("id")
                    val location_driver_id = single_object.getString("driver_id")
                    val location_lat = single_object.getString("lat")
                    val location_lng = single_object.getString("lng")
                    val location_online = single_object.getString("online")

                    val location_drivers = Location(location_id, location_driver_id, location_lat,
                            location_lng, location_online)

                    onlineDrivers.add(location_drivers)

                    //TODO: Show the list of drivers for the user to select from.
                }
            }
            else {
                //TODO: Unable to set location. Cause: DriverInterface Details not found
            }
        }, Response.ErrorListener {
            //TODO: An Error Occurred. Show Message
        })

        Volley.newRequestQueue(view.getContext()).add(getRequest)
    }
}