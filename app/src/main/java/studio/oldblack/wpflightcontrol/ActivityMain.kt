package studio.oldblack.wpflightcontrol

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModel
import net.openid.appauth.*
import studio.oldblack.wpflightcontrol.viewmodel.ViewModelAuth

class ActivityMain : AppCompatActivity() {

    // real, current work below

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mAuthVM = ViewModelAuth(application)

        // old work below

        auth_button.setOnClickListener {
            Toast.makeText(this, "This button is clicked for sure", Toast.LENGTH_LONG).show();
        }

        // Example from https://hackernoon.com/adding-oauth2-to-mobile-android-and-ios-clients-using-the-appauth-sdk-f8562f90ecff
        /*
        val mServiceConfig = AuthorizationServiceConfiguration(
            Uri.parse("https://public-api.wordpress.com/oauth2/authorize"),
            Uri.parse("https://public-api.wordpress.com/oauth2/token"))

        var authState = AuthState(mServiceConfig)

        Log.i("HAFIZ", " authorization endpoint: " + authState.authorizationServiceConfiguration?.authorizationEndpoint)
        Log.i("HAFIZ", " token endpoint: " + authState.authorizationServiceConfiguration?.tokenEndpoint)

        val authRequestBuilder = AuthorizationRequest.Builder(
            authState.getAuthorizationServiceConfiguration()!!,
            "67067",
            ResponseTypeValues.CODE,
            Uri.parse("https://localhost/"))


        val authRequest = authRequestBuilder.build()

        val authService = AuthorizationService(this, AppAuthConfiguration.Builder().build())
        val intentBuilder = authService.createCustomTabsIntentBuilder(authRequest.toUri())
        val authIntent = intentBuilder.build()

        intent = authService.getAuthorizationRequestIntent(authRequest, authIntent)
        Log.i("HAFIZ", " everything in place: " + authState.authorizationServiceConfiguration?.tokenEndpoint)
*/
    }
}
