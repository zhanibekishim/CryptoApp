package com.jax.cryptoapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val MILLS = 1000
fun convertTimestampToTime(timestampSeconds: Long?):String{
    if(timestampSeconds == null) return ""

    val dateTime = Date(timestampSeconds*MILLS)
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()

    return sdf.format(dateTime)
}
