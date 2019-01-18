package ng.nexttrip.nexttrip2.location

import android.content.Context
import ng.nexttrip.nexttrip2.pojo.Location

/**
 * Created by generalfocus on 17/01/2019.
 */
interface LocationInterface {
    // TODO: Extend this in Activity/Fragment
    interface View {
        fun showError(error: String)

        fun showMessage(message: String)

        fun getContext(): Context

        fun listDrivers(drivers: ArrayList<Location>)
    }

    /*interface Location{
        fun login(username: String, password: String)

        fun register(name: String, username: String, password: String, email: String,
                     phone_number: String, car_model: String, registration_number: String,
                     car_color: String)
    }*/

}