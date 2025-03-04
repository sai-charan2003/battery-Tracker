package dev.charan.batteryTracker

import Battery_Trackertheme
import android.Manifest
import android.annotation.SuppressLint

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

import androidx.core.app.ActivityCompat

import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController

import dev.charan.batteryTracker.Navigation.NavigationApphost
import dev.charan.batteryTracker.Utils.SharedPref


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.S)

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH_CONNECT),2)
        if(SharedPref.getInstance(this).isDarkModeEnabled){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }
        setContent {
            Battery_Trackertheme() {
                NavigationApphost(navController = rememberNavController())
            }
        }
    }
}



