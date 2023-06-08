package com.weather.jpmc.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object AppTestUtils {

    @Throws(IOException::class)
    fun getResponseFileAsString(fileName: String): String {
        val inputStream = this.javaClass.classLoader?.getResourceAsStream(fileName)
        inputStream?.let { input ->
            val buf = StringBuilder()
            val bufferedReader = BufferedReader(InputStreamReader(input))

            var str: String? = bufferedReader.readLine()
            while (str != null) {
                buf.append(str)
                str = bufferedReader.readLine()
            }
            input.close()
            bufferedReader.close()

            return buf.toString()
        }
        return ""
    }

    inline fun <reified T> readJsonResponseFile(jsonFileName: String): T {
        val gson = Gson()
        return gson.fromJson(
            getResponseFileAsString(
                jsonFileName
            ), T::class.java
        )
    }

    inline fun <reified T> readJsonResponseFileForList(jsonFileName: String): T {
        val gson = Gson()
        return gson.fromJson(
            getResponseFileAsString(
                jsonFileName
            ), object : TypeToken<T>() {}.type
        )
    }


}