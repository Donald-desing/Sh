package com.example.wifishare

import android.content.Context
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ssidInput = findViewById<EditText>(R.id.ssidInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val shareButton = findViewById<Button>(R.id.shareButton)

        shareButton.setOnClickListener {
            val ssid = ssidInput.text.toString()
            val password = passwordInput.text.toString()

            if (ssid.isNotBlank() && password.isNotBlank()) {
                createHotspot(ssid, password)
            } else {
                Toast.makeText(this, "SSID and Password cannot be empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createHotspot(ssid: String, password: String) {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        // Configure the hotspot
        val wifiConfig = WifiConfiguration().apply {
            SSID = ssid
            preSharedKey = password
            allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
        }

        try {
            val method = wifiManager.javaClass.getMethod(
                "setWifiApEnabled", WifiConfiguration::class.java, Boolean::class.javaPrimitiveType
            )
            method.invoke(wifiManager, wifiConfig, true)
            Toast.makeText(this, "Hotspot created successfully!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to create hotspot.", Toast.LENGTH_SHORT).show()
        }
    }
}
