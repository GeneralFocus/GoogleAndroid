package ng.nexttrip.nexttrip2.trips

import android.content.Context

interface TripInterface {

    interface View {
        fun showError(error: String)

        fun showMessage(message: String)

        fun showProgress(show: Boolean)

        fun getContext(): Context
    }

    interface Presenter {
        fun bookTrip(driver_id: String, user_id: String, pick_location: String, drop_location: String)

        fun setTripStatus(request_id: String, ride_status: String)

        fun startJourney(trip_request_id: String, trip_rate: String)

        fun endJourney(trip_request_id: String, payment_amount: String)
    }
}