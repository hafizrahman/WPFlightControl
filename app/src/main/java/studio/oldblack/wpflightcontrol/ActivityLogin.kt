package studio.oldblack.wpflightcontrol

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys.*
import kotlinx.android.synthetic.main.activity_login.*
import net.openid.appauth.*
import studio.oldblack.wpflightcontrol.viewmodel.ViewModelAuth

class ActivityLogin : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mAuthVM = ViewModelAuth(application)

        // EncryptedSharedPreferences
        // Example from:
        // https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences
        val masterKeyAlias = getOrCreate(AES256_GCM_SPEC)
        sharedPreferences = EncryptedSharedPreferences.create(
            WPFC_SHARED_PREFS_FILENAME,
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        auth_login_button.setOnClickListener {
            val authIntent = mAuthVM.getAppAuthRequestIntent(this)
            startActivityForResult(authIntent,
                WPFC_WPCOM_AUTH_REQUEST_CODE
            )
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

                // Saving to sharedPreferences
                // val editor = sharedPreferences.edit()
                with (sharedPreferences.edit()) {
                    putString(WPFC_SHARED_PREFS_KEY_AUTH_ACCESS_TOKEN,  encodedFragmentValues["access_token"])
                    putString(WPFC_SHARED_PREFS_KEY_AUTH_EXPIRATION,    encodedFragmentValues["expires_in"])
                    putString(WPFC_SHARED_PREFS_KEY_AUTH_STATE,         encodedFragmentValues["state"])
                    putString(WPFC_SHARED_PREFS_KEY_AUTH_COMPLETE_TIMESTAMP, System.currentTimeMillis().toString())
                    commit()
                }

                Log.i("Hafiz", "fetched data saved,boss")

                // Open ActivityMain
                val intent = Intent(this, ActivityLogin::class.java)
                startActivity(intent)
            }
        }
    }

    // If back button is pressed during login activity, minimize the screen.
    // The app can't function without login anyway, so we're not letting the
    // user to go back any other activities before the login is completed.
    // The code is from https://stackoverflow.com/a/26492794
    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}
