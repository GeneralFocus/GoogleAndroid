package ng.nexttrip.nexttrip2.driver

import android.content.Context

interface DriverInterface {

    // TODO: Extend this in Activity/Fragment
    interface View {
        fun showError(error: String)

        fun showMessage(message: String)

        fun getContext(): Context
    }

    interface Presenter{
        fun login(username: String, password: String)

        fun register(name: String, username: String, password: String, email: String,
                     phone_number: String, car_model: String, registration_number: String,
                     car_color: String)
    }
}