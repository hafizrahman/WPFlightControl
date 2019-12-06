package studio.oldblack.wpflightcontrol

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

import net.openid.appauth.*

class ActivityMain : AppCompatActivity() {

    // real, current work below

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val mAuthVM = ViewModelAuth(application)

        // old work below

        auth_button.setOnClickListener {
            // Example from https://hackernoon.com/adding-oauth2-to-mobile-android-and-ios-clients-using-the-appauth-sdk-f8562f90ecff
            // First we build the ServiceConfiguration
            val serviceConfig = AuthorizationServiceConfiguration(
                Uri.parse("https://public-api.wordpress.com/oauth2/authorize"),
                Uri.parse("https://public-api.wordpress.com/oauth2/token"))

            val authService = AuthorizationService(it.context)
            val authState = AuthState(serviceConfig)

            // Second, we build the AuthorizationRequest
            val authRequestBuilder = AuthorizationRequest.Builder(
                serviceConfig,
                BuildConfig.OAUTH_APP_ID,
                ResponseTypeValues.TOKEN,
                Uri.parse("https://v2.atomicowl.blog/wpfp/"))
            val authRequest = authRequestBuilder.build()

            // Third, we perform the AuthorizationRequest
            /* Below is the startActivityForResult method, which didn't work in my test
            val authIntent = authService.getAuthorizationRequestIntent(authRequest)
            val DO_AUTH_REQUEST = 1337
            startActivityForResult(authIntent, DO_AUTH_REQUEST)
             */

            /* Below is the pendingIntent method */
            /*
            val action = "studio.oldblack.wpflightcontrol.appauth.HANDLE_AUTHORIZATION_RESPONSE"
            val postAuthorizationIntent = Intent(action)
            val pendingIntent = PendingIntent.getActivity(
                it.context,
                authRequest.hashCode(),
                postAuthorizationIntent,
                0)
            authService.performAuthorizationRequest(authRequest, pendingIntent)
             */
            val safrIntent = authService.getAuthorizationRequestIntent(authRequest)
            val DO_AUTH_REQUEST = 1337
            val action = "studio.oldblack.wpflightcontrol.appauth.HANDLE_AUTHORIZATION_RESPONSE"
            startActivityForResult(safrIntent, DO_AUTH_REQUEST)


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val encodedFragment = data?.data?.encodedFragment

        // Process the encodedFragment and turn it into a Map
        // Initially, ncodedFragment looks like this:
        // "access_token=XXX&token_type=bearer&site_id=XXX&scope=&state=XXX"
        // Steps:
        // 1. Create new array A of each key-value pair, delimited by "&"
        // 2. Create empty map B
        // 3. Iterate over each item in A. Parse each item delimited by "="
        // 3.b. Put item i[0] as a key in B, i[1] as value in B

        val tempSplit = encodedFragment?.split("&")
        var encodedFragmentValues = mutableMapOf<String, String>()

        for(pair in tempSplit.orEmpty()) {
            var tempSplitPair = pair.split("=")
            encodedFragmentValues[tempSplitPair[0]] = tempSplitPair[1]
        }

        // Loggers

        Log.i("Hafiz", "What encodedFragment")
        for((k, v) in encodedFragmentValues) {
                Log.i("maps", "$k = $v")
        }

        if(requestCode == 1337) {
            if(resultCode == Activity.RESULT_OK) {
                // Yeah, what?
                Log.i("Hafiz", "This is inside onActivityResult")
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        checkIntent(intent)
    }

    private fun checkIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            Log.i("Hafiz", "intent is not null $action")
            if (!intent.hasExtra("USED_INTENT")) {
                intent.putExtra("USED_INTENT", true)
            }
            when (action) {
                "studio.oldblack.wpflightcontrol.appauth.HANDLE_AUTHORIZATION_RESPONSE" -> {
                        if (!intent.hasExtra("USED_INTENT")) {
                            intent.putExtra("USED_INTENT", true)
                        }
                    }
            }// do nothing
        }
    }

    override fun onStart() {
        super.onStart()
        checkIntent(intent)
    }
}
