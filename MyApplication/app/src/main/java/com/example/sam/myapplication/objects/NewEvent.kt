package com.example.sam.myapplication.objects

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.sql.Date
import java.sql.Time

/**
 * Created by Sam on 14.03.2017.
 */
class NewEvent{
    public var date: Date=Date(0)
    public var timeBegin: Time=Time(0)
    public var timeEnd: Time=Time(0)
    public var name:String="no name"
    public var info:String="no info"
    public var img: Bitmap?=null
    public var id_user:Int=0
    public var arrTagID=ArrayList<Int>()

    constructor(_date: Date, _timeB: Time,
                _timeE: Time, _name:String, _info:String,
                bitmap: Bitmap?, _id_user:Int, _arrTagID:ArrayList<Int> ){
        date = _date
        timeBegin = _timeB
        timeEnd = _timeE
        name= _name
        info = _info
        img = bitmap
        id_user = _id_user
        arrTagID = _arrTagID
    }
    constructor(_id_user:Int){
        id_user = _id_user
    }
}