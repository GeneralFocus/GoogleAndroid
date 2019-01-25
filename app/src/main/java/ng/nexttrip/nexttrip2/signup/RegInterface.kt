package ng.nexttrip.nexttrip2.signup

import android.content.Context
import android.view.View

interface RegInterface {
    // TODO: Extend this in Activity/Fragment
    interface View {
        fun showError(error: String)

        fun showMessage(message: String)

        fun openRegister()

        fun openOTP()

        fun openLogin()

        fun showProgress(show: Boolean)

        fun getContext(): Context

        fun proceedToHome()

        fun validateRole()
    }

    interface Presenter{
        fun login(phone_number: String)

        fun register(name: String, email: String, phone_number: String, payment_method: String)

        fun sendOTPRequest(phone_number: String, otp_code: String)

        fun validateRole(phone_number: String)
    }
}