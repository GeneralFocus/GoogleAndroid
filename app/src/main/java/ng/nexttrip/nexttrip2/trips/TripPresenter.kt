package ng.nexttrip.nexttrip2.trips

import android.location.Address
import android.location.Geocoder
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.LatLng
import ng.nexttrip.nexttrip2.util.GlobalVariable
import org.json.JSONObject

class TripPresenter(var view: TripInterface.View): TripInterface.Presenter {

    val tripUrl = "${GlobalVariable.API}/api/v1/taxi"

    override fun bookTrip(driver_id: String, user_id: String, pick_location: String, drop_location: String) {

        val latlng = getLocationFromAddress(drop_location)

        val param = HashMap<String, String>()
        param["driver_id"] = driver_id
        param["user_id"] = user_id
        param["pick_location"] = pick_location
        param["drop_location"] = drop_location
        param["lat"] = latlng?.latitude.toString()
        param["lng"] = latlng?.longitude.toString()

        val jsonParam = JSONObject(param)

        val bookRequest = JsonObjectRequest(Request.Method.POST, tripUrl, jsonParam, Response.Listener {
            val status = it.getBoolean("status")

            if (status){
                view.showMessage("Trip Booked Successfully")
            }
            else {
                view.showError("An Error Occurred Booking your trip")
            }

        }, Response.ErrorListener {
            view.showError(it.message!!)
        })

        Volley.newRequestQueue(view.getContext()).add(bookRequest)
    }

    override fun setTripStatus(request_id: String, ride_status: String) {
        if (ride_status !== "ACCEPTED" || ride_status !== "REJECTED"){
            view.showError("Invalid Status. Please Accept or Reject Ride")
            return
        }

        val statusURL = "$tripUrl?request_id=$request_id&ride_status=$ride_status"

        val statusRequest = JsonObjectRequest(Request.Method.GET, statusURL, null, Response.Listener {

            val status = it.getBoolean("status")
            if (status){
                view.showMessage("You have $ride_status this Trip")
            }
            else {
                view.showError("An Error Occurred. Trip was not found in Database")
            }

        }, Response.ErrorListener {
            view.showError(it.message!!)
        })

        Volley.newRequestQueue(view.getContext()).add(statusRequest)
    }

    private fun getLocationFromAddress(strAddress: String): LatLng? {
        val coder = Geocoder(view.getContext())
        val address: List<Address>?
        var p1: LatLng? = null

        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location = address[0]
            location.latitude
            location.longitude

            p1 = LatLng(location.latitude, location.longitude)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return p1
    }
}