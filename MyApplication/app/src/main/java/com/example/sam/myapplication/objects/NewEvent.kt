package com.example.sam.myapplication.objects

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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
    public var imgUri: Uri?=null
    public var id_user:Int=0
    public var tagID:Int=0

    constructor(_date: Date, _timeB: Time,
                _timeE: Time, _name:String, _info:String,
                uri: Uri?, _id_user:Int, _tagID:Int ){
        date = _date
        timeBegin = _timeB
        timeEnd = _timeE
        name= _name
        info = _info
        imgUri = uri
        id_user = _id_user
        tagID = _tagID
    }
    constructor(_id_user:Int){
        id_user = _id_user
    }
}