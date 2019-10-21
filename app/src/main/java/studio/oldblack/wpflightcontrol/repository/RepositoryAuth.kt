package studio.oldblack.wpflightcontrol.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import net.openid.appauth.*

class RepositoryAuth(val context: Context) {

    init {

    }

    fun whiz() = "memomemo"

    fun whee() {
        Log.i("HAFIZ", "Did I do anything")
    }

    fun what() {
        Log.i("eyyyy", "Did I do anythiing")
        // Example from https://hackernoon.com/adding-oauth2-to-mobile-android-and-ios-clients-using-the-appauth-sdk-f8562f90ecff
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

        val authService = AuthorizationService(context, AppAuthConfiguration.Builder().build())
        val intentBuilder = authService.createCustomTabsIntentBuilder(authRequest.toUri())
        val authIntent = intentBuilder.build()

        val intent = authService.getAuthorizationRequestIntent(authRequest, authIntent)
        Log.i("HAFIZ", " everything in place: " + authState.authorizationServiceConfiguration?.tokenEndpoint)

    }



}