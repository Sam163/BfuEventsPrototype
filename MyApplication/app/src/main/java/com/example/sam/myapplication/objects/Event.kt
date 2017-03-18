package com.example.sam.myapplication.objects

import java.sql.Date
import java.sql.Time

class Event() {
    public var id=0
    public var date = Date(0).toString()
    public var timeBegin = Time(0).toString()
    public var timeEnd = Time(0).toString()
    public var name="no name"
    public var info="no info"
    public var picUrl = ""
    public var likes = 0//кол-во кому интересно
    public var go = 0 // кол-во кто-пойдет
    public var fullName = "no name"//имя создателя
    public var email = "no contact"//контакт создателя (мыло)

    constructor(_id:Int, _date: Date, _timeB: Time, _timeE: Time, _name:String, _info:String, _dir:String,
                _likes:Int, _go:Int, _uName:String, _uContact:String ): this(){
        id = _id
        date = _date.toString()
        timeBegin = _timeB.toString()
        timeEnd = _timeE.toString()
        name= _name
        info = _info
        picUrl = _dir
        likes = _likes
        go = _go
        fullName = _uName
        email = _uContact
    }

    constructor(_id:Int, _date: String, _timeB: String,
                _timeE: String, _name:String, _info:String,
                _dir:String, _uName:String, _uContact:String ): this(){
        id = _id
        date = _date
        timeBegin = _timeB
        timeEnd = _timeE
        name= _name
        info = _info
        picUrl = _dir
        likes = 0
        go = 0
        fullName = _uName
        email = _uContact
    }
}
/*
class Event() {
    public var id=0
    public var date: Date = Date(0)
    public var timeBegin: Time = Time(0)
    public var timeEnd: Time = Time(0)
    public var name="no name"
    public var info="no info"
    public var picUrl = ""
    public var likes = 0//кол-во кому интересно
    public var go = 0 // кол-во кто-пойдет
    public var fullName = "no name"//имя создателя
    public var email = "no contact"//контакт создателя (мыло)

    constructor(_id:Int, _date: Date, _timeB: Time, _timeE: Time, _name:String, _info:String, _dir:String,
                _likes:Int, _go:Int, _uName:String, _uContact:String ): this(){
        id = _id
        date = _date
        timeBegin = _timeB
        timeEnd = _timeE
        name= _name
        info = _info
        picUrl = _dir
        likes = _likes
        go = _go
        fullName = _uName
        email = _uContact
    }

    constructor(_id:Int, _date: Date, _timeB: Time, _timeE: Time, _name:String, _info:String, _dir:String, _uName:String, _uContact:String ): this(){
        id = _id
        date = _date
        timeBegin = _timeB
        timeEnd = _timeE
        name= _name
        info = _info
        picUrl = _dir
        likes = 0
        go = 0
        fullName = _uName
        email = _uContact
    }
}*/