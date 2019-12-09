package studio.oldblack.wpflightcontrol

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

import net.openid.appauth.*
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class ActivityMain : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    // real, current work below
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // EncryptedSharedPreferences
        // Example from:
        // https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        sharedPreferences = EncryptedSharedPreferences.create(
            WPFC_SHARED_PREFS_FILENAME,
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        // Check if we have existing access token
        // If not, open the login activity
        val accessToken = sharedPreferences.getString(WPFC_SHARED_PREFS_KEY_AUTH_ACCESS_TOKEN, "none")
        Log.i("Hafiz", "Current access token is $accessToken")

        // TODO: if access token exists, check its validity and force login if not valid
        if(accessToken.equals("none")) {
            //open login activity
            val intentForLogin = Intent(this, ActivityLogin::class.java)
            startActivity(intentForLogin)
        }

        //val mAuthVM = ViewModelAuth(application)

        // If we're here, it means we have access token and can now display contents.
    }
}
