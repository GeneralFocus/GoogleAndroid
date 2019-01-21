package ng.nexttrip.nexttrip2.role

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import ng.nexttrip.nexttrip2.R
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner



class RoleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role)
        val staticSpinner = findViewById<View>(R.id.static_spinner) as Spinner

        // Create an ArrayAdapter using the string array and a default spinner
        val staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.role_array,
                        android.R.layout.simple_spinner_item)

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        staticSpinner.adapter = staticAdapter

        }
    }

