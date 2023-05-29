package com.github.imort.news.data

import android.app.Application
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import javax.inject.Inject

interface BiometricHelper {
    fun available(): Boolean
    fun showPrompt(activity: FragmentActivity, onSuccess: () -> Unit)
}

internal class BiometricHelperImpl @Inject constructor(
    application: Application,
) : BiometricHelper {
    private val manager = BiometricManager.from(application)

    override fun available(): Boolean {
        val result = manager.canAuthenticate(Authenticators.BIOMETRIC_WEAK)
        return result == BiometricManager.BIOMETRIC_SUCCESS
    }

    override fun showPrompt(activity: FragmentActivity, onSuccess: () -> Unit) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Cancel")
            .build()
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                Toast.makeText(activity, errString, Toast.LENGTH_LONG).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                onSuccess()
            }

            override fun onAuthenticationFailed() {
                Toast.makeText(activity, "Auth failed", Toast.LENGTH_LONG).show()
            }
        }
        BiometricPrompt(activity, callback).authenticate(promptInfo)
    }
}
