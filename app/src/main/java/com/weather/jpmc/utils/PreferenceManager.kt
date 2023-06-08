package com.weather.jpmc.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceManager @Inject constructor(val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREF_FILE, Activity.MODE_PRIVATE)
    }

    private val prefsEditor : SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

    public fun storeStringInPreferences(key: String, value: String) {
        prefsEditor.putString(key, value)
        prefsEditor.commit()
    }

    public fun getStringValueFromPreferences(key: String) : String? {
        return sharedPreferences.getString(key, "")
    }

    companion object {
        const val PREF_FILE = "jpmc_weather"
    }
}