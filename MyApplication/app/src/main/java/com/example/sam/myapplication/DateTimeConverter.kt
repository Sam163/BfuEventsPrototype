package com.example.sam.myapplication

import java.text.SimpleDateFormat


public fun toSqlDate(date:String):java.sql.Date{
    return java.sql.Date.valueOf(date)
}

public fun toSqlDate(dat_utl: java.util.Date):java.sql.Date{
    return java.sql.Date(dat_utl.getTime())
}

public fun toSqlTime(time:String):java.sql.Time{
    return java.sql.Time.valueOf(time)
}

public fun toSqlTime(dat_utl: java.util.Date):java.sql.Time{
    return java.sql.Time(dat_utl.getTime())
}

public fun toStringSqlTime(time: java.sql.Time):String{
    return time.toString()
}

public fun toStringSqlDate(date: java.sql.Date):String{
    return date.toString()
}