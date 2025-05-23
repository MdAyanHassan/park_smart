package com.example.firebasesigninwithmanualdi.core

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class Utils {
    companion object {
        fun printError(e: Exception?) {
            if (e != null)
                Log.e(TAG, e.stackTraceToString())
        }
        fun getDateTime(timestamp: Date?): String {
            if (timestamp != null) {
                val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")

                return dateFormat.format(timestamp)
            }
            return SAMPLE_DATE_TIME
        }
    }
}