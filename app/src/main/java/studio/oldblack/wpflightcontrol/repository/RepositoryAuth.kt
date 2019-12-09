package studio.oldblack.wpflightcontrol.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import net.openid.appauth.*
import studio.oldblack.wpflightcontrol.*
import studio.oldblack.wpflightcontrol.BuildConfig

class RepositoryAuth(val context: Context) {

    fun getAppAuthRequestIntent(context: Context) : Intent {
        // Example from https://hackernoon.com/adding-oauth2-to-mobile-android-and-ios-clients-using-the-appauth-sdk-f8562f90ecff
        // First we build the ServiceConfiguration
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse(WPFC_WPCOM_AUTH_ENDPOINT),
            Uri.parse(WPFC_WPCOM_TOKEN_ENDPOINT))

        val authService = AuthorizationService(context)

        // Second, we build the AuthorizationRequest
        val authRequestBuilder = AuthorizationRequest.Builder(
            serviceConfig,
            BuildConfig.OAUTH_APP_ID,
            ResponseTypeValues.TOKEN,
            Uri.parse(WPFC_WPCOM_APP_REDIRECT))
        val authRequest = authRequestBuilder.build()

        return authService.getAuthorizationRequestIntent(authRequest)
    }


}