package com.example.sam.myapplication.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.net.Uri
import com.example.sam.myapplication.objects.*
import com.example.sam.myapplication.toSqlDate
import com.example.sam.myapplication.toSqlTime
import java.io.File
import java.sql.Date


class LDbHelper private constructor (context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(DB_CREATE_EVENTS)
            db.execSQL(DB_CREATE_TAGS)
            db.execSQL(DB_CREATE_TAG_EVENT)
            db.execSQL(DB_CREATE_MARKEVENTS)
            db.execSQL(DB_CREATE_MYEVENTS)
        }

        companion object {
            private var dbHelper: LDbHelper? = null
            private var db: SQLiteDatabase? = null

            private val DB_NAME = "actualbfuevents"
            private val DB_VERSION = 1
            //таб.соб.
            private val DB_TABLE_EVENTS = "events"
            private val DB_COLUMN_NAME = "name"
            private val DB_COLUMN_DATE = "date"
            private val DB_COLUMN_TIME_BEGIN = "timeBegin"
            private val DB_COLUMN_TIME_END = "time_end"
            private val DB_COLUMN_INFO = "info"
            private val DB_COLUMN_PIC_URL = "pic_dir"
            private val DB_COLUMN_LIKES = "like_count"
            private val DB_COLUMN_GO = "go_count"
            private val DB_COLUMN_USERNAME = "user_name"
            private val DB_COLUMN_USERCONTACT = "user_contact"

            //таб.теги
            private val DB_TABLE_TAGS = "tags"
            private val DB_COLUMN_TAGNAME = "name"

            //таб. тег-событие
            private val DB_TABLE_TAG_EVENT = "tag_event"
            private val DB_COLUMN_EVENTID = "id_event"
            private val DB_COLUMN_TAGID = "id_tag"

            //таб. мои созданные события
            private val DB_TABLE_MYEVENTS = "my_event"
            //private val DB_COLUMN_EVENTID = "id_event"

            //таб. мои отмеченные события
            private val DB_TABLE_MARKEVENTS = "mark_event"
            private val DB_COLUMN_TYPE = "mark_type"
            //private val DB_COLUMN_EVENTID = "id_event"
            public val MARK_TYPE_INTERESTED = 2
            public val MARK_TYPE_ILLGO = 1


            private val DB_CREATE_EVENTS = "CREATE TABLE " +
                    DB_TABLE_EVENTS + "( _id INTEGER NOT NULL PRIMARY KEY, " +
                    DB_COLUMN_DATE + " TEXT NOT NULL, " +
                    DB_COLUMN_TIME_BEGIN + " TEXT NOT NULL, " +
                    DB_COLUMN_TIME_END + " TEXT, " +
                    DB_COLUMN_NAME + " TEXT NOT NULL, " +
                    DB_COLUMN_INFO +" TEXT, " +
                    DB_COLUMN_PIC_URL + " TEXT, " +
                    DB_COLUMN_LIKES + " INTEGER, "+
                    DB_COLUMN_GO + " INTEGER, "+
                    DB_COLUMN_USERNAME + " TEXT, "+
                    DB_COLUMN_USERCONTACT + " TEXT "+ ");"

            private val DB_CREATE_TAGS = "CREATE TABLE " +
                    DB_TABLE_TAGS + "( _id INTEGER PRIMARY KEY, " +
                    DB_COLUMN_TAGNAME + " TEXT "+ ");"

            private  val DB_CREATE_TAG_EVENT = "CREATE TABLE " +
                    DB_TABLE_TAG_EVENT + "( " +
                    DB_COLUMN_EVENTID + " INTEGER NOT NULL, " +
                    DB_COLUMN_TAGID +" INTEGER NOT NULL "+ ");"

            private  val DB_CREATE_MYEVENTS = "CREATE TABLE " +
                    DB_TABLE_MYEVENTS +"( " +
                    DB_COLUMN_EVENTID + " INTEGER PRIMARY KEY );"

            private  val DB_CREATE_MARKEVENTS = "CREATE TABLE " +
                    DB_TABLE_MARKEVENTS +"( " +
                    DB_COLUMN_EVENTID + " INTEGER PRIMARY KEY,"+
                    DB_COLUMN_TYPE +" INTEGER NOT NULL"+" );"

            public fun initLDbHelper(context: Context) {
                if (dbHelper == null) {
                    dbHelper = LDbHelper(context)
                    db = dbHelper!!.writableDatabase
                }
            }

            public fun isLocalDataBaseExist(context: Context): Boolean{
                var data: File = context.getDatabasePath(DB_NAME)
                if(data.exists()){
                    return true
                }
                else{
                    return false
                }
            }

            public fun clearLocalDataBase(){
                db!!.delete(DB_TABLE_EVENTS, null,  null)
                db!!.delete(DB_TABLE_TAGS, null,  null)
                db!!.delete(DB_TABLE_TAG_EVENT, null,  null)
                db!!.delete(DB_TABLE_MARKEVENTS, null,  null)
                db!!.delete(DB_TABLE_MYEVENTS, null,  null)
            }

            public fun clear_TABLE_EVENTS(){
                db!!.delete(DB_TABLE_EVENTS, null,  null)
            }
            public fun clear_TABLE_TAGS(){
                db!!.delete(DB_TABLE_TAGS, null,  null)
            }
            public fun clear_TABLE_TAG_EVENT(){
                db!!.delete(DB_TABLE_TAG_EVENT, null,  null)
            }
            public fun clear_TABLE_MARKEVENTS(){
                db!!.delete(DB_TABLE_MARKEVENTS, null,  null)
            }
            public fun clear_TABLE_MYEVENTS(){
                db!!.delete(DB_TABLE_MYEVENTS, null,  null)
            }

            public fun deleteLocalDataBase(context: Context){
                closeLDbHelper()
                context.deleteDatabase(DB_NAME)
            }

            public fun addEvents(arr: ArrayList<Event>) {
                for (task in arr) {
                    addEvent(task)
                }
            }
            public fun addEvents(arr: List<Event>) {
                for (task in arr) {
                    addEvent(task)
                }
            }

            public fun addEvent(bfu: Event) {
                var cv = ContentValues()
                cv.put("_id", bfu.id)
                cv.put(DB_COLUMN_DATE, bfu.date)
                cv.put(DB_COLUMN_TIME_BEGIN, bfu.timeBegin)
                cv.put(DB_COLUMN_TIME_END, bfu.timeEnd)
                cv.put(DB_COLUMN_NAME, bfu.name)
                cv.put(DB_COLUMN_INFO, bfu.info)
                cv.put(DB_COLUMN_PIC_URL, bfu.picUrl)
                cv.put(DB_COLUMN_LIKES, bfu.likes)
                cv.put(DB_COLUMN_GO, bfu.go)
                cv.put(DB_COLUMN_USERNAME, bfu.fullName)
                cv.put(DB_COLUMN_USERCONTACT, bfu.email)
                db!!.insert(DB_TABLE_EVENTS, null, cv)
                var resultQuery = db!!.query(DB_TABLE_EVENTS, null, null, null, null, null, null)
                var c=0
            }

            public fun addLinkTagEvent(arrTagID: ArrayList<Int>,arrEventID: ArrayList<Int> ){
                var m=arrTagID.size
                if(m>arrEventID.size) m=arrEventID.size//такого вообще быть не должно, но навсяк - исключения
                for(i in 0..m-1) {
                    var cv = ContentValues()
                    cv.put(DB_COLUMN_EVENTID, arrEventID[i])
                    cv.put(DB_COLUMN_TAGID, arrTagID[i])
                    db!!.insert(DB_TABLE_TAG_EVENT, null, cv)
                }
            }
            public fun addLinkTagEvent(arr: List<EventTag>){
                for(item in arr) {
                    var cv = ContentValues()
                    cv.put(DB_COLUMN_EVENTID, item.idEvent)
                    cv.put(DB_COLUMN_TAGID, item.idTag)
                    db!!.insert(DB_TABLE_TAG_EVENT, null, cv)
                }
            }

            public fun addTags(arrTagID: ArrayList<Int>,arrTagName: ArrayList<String> ){
                for(i in 0..arrTagID.size-1) {
                    var cv = ContentValues()
                    cv.put("_id", arrTagID[i])
                    cv.put(DB_COLUMN_TAGNAME, arrTagName[i])
                    db!!.insert(DB_TABLE_TAGS, null, cv)
                }
            }
            public fun addTags(arr: List<Tag>){
                for(item in arr) {
                    var cv = ContentValues()
                    cv.put("_id", item.idTag)
                    cv.put(DB_COLUMN_TAGNAME, item.nameTag)
                    db!!.insert(DB_TABLE_TAGS, null, cv)
                }
            }

            public fun addMyEvents(arrEventID: ArrayList<Int>){
                for(i in 0..arrEventID.size-1) {
                    var cv = ContentValues()
                    cv.put(DB_COLUMN_EVENTID, arrEventID[i])
                    db!!.insert(DB_TABLE_MYEVENTS, null, cv)
                }
            }
            public fun addMyEvents(arr: List<MyEvent>){
                for(item in arr) {
                    var cv = ContentValues()
                    cv.put(DB_COLUMN_EVENTID, item.id)
                    db!!.insert(DB_TABLE_MYEVENTS, null, cv)
                }
            }

            public fun addMarkEvents(arrEventID: ArrayList<Int>, arrMark: ArrayList<Int>){
                for(i in 0..arrEventID.size-1) {
                    var cv = ContentValues()
                    cv.put(DB_COLUMN_EVENTID, arrEventID[i])
                    cv.put(DB_COLUMN_TYPE, arrMark[i])
                    db!!.insert(DB_TABLE_MARKEVENTS, null, cv)
                }
            }
            public fun addMarkEvents(arr: List<MarkedEvent>){
                for(item in arr) {
                    var cv = ContentValues()
                    cv.put(DB_COLUMN_EVENTID, item.idEvent)
                    cv.put(DB_COLUMN_TYPE,item.idType)
                    db!!.insert(DB_TABLE_MARKEVENTS, null, cv)
                }
            }
            public fun addMarkEvent(eventID: Int, mark: Int){
                    var cv = ContentValues()
                    cv.put(DB_COLUMN_EVENTID, eventID)
                    cv.put(DB_COLUMN_TYPE,mark)
                    db!!.insert(DB_TABLE_MARKEVENTS, null, cv)
            }

            public fun getEvents():ArrayList<Event> {
                var result = ArrayList<Event>()
                var resultQuery = db!!.query(DB_TABLE_EVENTS, null, null, null, null, null, null)
                while (resultQuery.moveToNext()) {
                    result.add(Event(
                            resultQuery.getInt(resultQuery.getColumnIndex("_id")),
                            toSqlDate(resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_DATE))),
                            toSqlTime(resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_TIME_BEGIN))),
                            toSqlTime(resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_TIME_END))),
                            resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_NAME)),
                            resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_INFO)),
                            resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_PIC_URL)),
                            resultQuery.getInt(resultQuery.getColumnIndex(DB_COLUMN_LIKES)),
                            resultQuery.getInt(resultQuery.getColumnIndex(DB_COLUMN_GO)),
                            resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_USERNAME)),
                            resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_USERCONTACT))
                            )
                    )
                }
                return result
            }

            public fun getEventsByDate(date: Date):ArrayList<Event> {
                var result = ArrayList<Event>()
                var resultQuery = db!!.query(DB_TABLE_EVENTS, null, DB_COLUMN_DATE + " = ?", arrayOf(date.toString()), null, null, null)
                while (resultQuery.moveToNext()) {
                    result.add(Event(
                            resultQuery.getInt(resultQuery.getColumnIndex("_id")),
                            toSqlDate(resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_DATE))),
                            toSqlTime(resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_TIME_BEGIN))),
                            toSqlTime(resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_TIME_END))),
                            resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_NAME)),
                            resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_INFO)),
                            resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_PIC_URL)),
                            resultQuery.getInt(resultQuery.getColumnIndex(DB_COLUMN_LIKES)),
                            resultQuery.getInt(resultQuery.getColumnIndex(DB_COLUMN_GO)),
                            resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_USERNAME)),
                            resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_USERCONTACT))
                    )
                    )
                }
                return result
            }

            public fun getTagsByEventID(idEvent:Int):ArrayList<String>{
                var result = ArrayList<String>()
                var teQuery = db!!.query(DB_TABLE_TAG_EVENT,null, DB_COLUMN_EVENTID + " = ?",
                        arrayOf(idEvent.toString()), null, null, null)
                while (teQuery.moveToNext()) {
                    var tag = db!!.query(DB_TABLE_TAGS, null, "_id " + " = ?",
                            arrayOf(teQuery.getInt(teQuery.getColumnIndex(DB_COLUMN_TAGID)).toString()), null, null, null, null)
                    tag.moveToFirst()
                    result.add(tag.getString(tag.getColumnIndex(DB_COLUMN_TAGNAME)));
                }
                return result
            }

            public fun getPicUrlByEventID(id_event:Int):String{
                var result = ""
                var resultQuery = db!!.query(DB_TABLE_EVENTS, null, "_id " + " = ?",
                        arrayOf(id_event.toString()), null, null, null)
                resultQuery.moveToFirst()
                result = resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_PIC_URL))
                return result
            }

            public fun getPicturesNames():ArrayList<String>{
                var result = ArrayList<String>()
                var resultQuery = db!!.query(DB_TABLE_EVENTS, null, null, null, null, null, null)
                while (resultQuery.moveToNext()) {
                    result.add(
                            getPicNameByURL(resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_PIC_URL)))
                    )
                }
                return result
            }

            public fun getMyEvents():ArrayList<Event>{
                var result = ArrayList<Event>()
                var myevents = db!!.query(DB_TABLE_MYEVENTS, null, null, null, null, null, null)
                while (myevents.moveToNext()) {
                    var idEv=myevents.getInt(myevents.getColumnIndex(DB_COLUMN_EVENTID))
                    var resultQuery = db!!.query(DB_TABLE_EVENTS, null, "_id " + " = ?",
                            arrayOf(idEv.toString()), null, null, null)
                    resultQuery.moveToFirst()
                    if(resultQuery.count!=0) {
                        result.add(Event(
                                resultQuery.getInt(resultQuery.getColumnIndex("_id")),
                                toSqlDate(resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_DATE))),
                                toSqlTime(resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_TIME_BEGIN))),
                                toSqlTime(resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_TIME_END))),
                                resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_NAME)),
                                resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_INFO)),
                                resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_PIC_URL)),
                                resultQuery.getInt(resultQuery.getColumnIndex(DB_COLUMN_LIKES)),
                                resultQuery.getInt(resultQuery.getColumnIndex(DB_COLUMN_GO)),
                                resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_USERNAME)),
                                resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_USERCONTACT))
                        )
                        )
                    }
                }
                return result
            }
            public fun getMarkedEvents():ArrayList<Event>{
                var result = ArrayList<Event>()
                var mevents = db!!.query(DB_TABLE_MARKEVENTS, arrayOf(DB_COLUMN_EVENTID), null, null, null, null, null)
                while (mevents.moveToNext()) {
                    var idEv=mevents.getInt(mevents.getColumnIndex(DB_COLUMN_EVENTID))
                    var resultQuery = db!!.query(DB_TABLE_EVENTS, null, "_id " + " = ?",
                            arrayOf(idEv.toString()), null, null, null)
                    resultQuery.moveToFirst()
                    if(resultQuery.count!=0) {
                        result.add(Event(
                                resultQuery.getInt(resultQuery.getColumnIndex("_id")),
                                toSqlDate(resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_DATE))),
                                toSqlTime(resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_TIME_BEGIN))),
                                toSqlTime(resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_TIME_END))),
                                resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_NAME)),
                                resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_INFO)),
                                resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_PIC_URL)),
                                resultQuery.getInt(resultQuery.getColumnIndex(DB_COLUMN_LIKES)),
                                resultQuery.getInt(resultQuery.getColumnIndex(DB_COLUMN_GO)),
                                resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_USERNAME)),
                                resultQuery.getString(resultQuery.getColumnIndex(DB_COLUMN_USERCONTACT))
                        )
                        )
                    }
                }
                return result
            }
            public fun getMarkEvent(idEv:Int):Int{
                var events = db!!.query(DB_TABLE_MARKEVENTS, null, DB_COLUMN_EVENTID + " = ?", arrayOf(idEv.toString()), null, null, null)
                if(events.count==0)
                    return -1;//нет оценки
                events.moveToFirst()
                return events.getInt(events.getColumnIndex(DB_COLUMN_TYPE))
            }

            public fun isMyEvent(idEv:Int):Boolean{
                var myevents = db!!.query(DB_TABLE_MYEVENTS, null, DB_COLUMN_EVENTID + " = ?", arrayOf(idEv.toString()), null, null, null)
                if(myevents.count==1)
                    return true
                else return false
            }

            public fun updateEvent(bfu: Event){
                var cv = ContentValues()
                cv.put("_id", bfu.id)
                cv.put(DB_COLUMN_DATE, bfu.date.toString())
                cv.put(DB_COLUMN_TIME_BEGIN, bfu.timeBegin.toString())
                cv.put(DB_COLUMN_TIME_BEGIN, bfu.timeEnd.toString())
                cv.put(DB_COLUMN_NAME, bfu.name)
                cv.put(DB_COLUMN_INFO, bfu.info)
                cv.put(DB_COLUMN_PIC_URL, bfu.picUrl)
                cv.put(DB_COLUMN_LIKES, bfu.likes)
                cv.put(DB_COLUMN_GO, bfu.go)
                cv.put(DB_COLUMN_USERNAME, bfu.fullName)
                cv.put(DB_COLUMN_USERCONTACT, bfu.email)
                db!!.update(DB_TABLE_EVENTS, cv, "_id = ?", arrayOf(bfu.id.toString()))
            }

            public fun deleteEventByID(id:Int){
                db!!.delete(DB_TABLE_EVENTS, "_id = ?",  arrayOf(id.toString()))
                db!!.delete(DB_TABLE_TAG_EVENT, DB_COLUMN_EVENTID+" = ?",  arrayOf(id.toString()))
                db!!.delete(DB_TABLE_MARKEVENTS, DB_COLUMN_EVENTID+" = ?",  arrayOf(id.toString()))
                db!!.delete(DB_TABLE_MYEVENTS, DB_COLUMN_EVENTID+" = ?",  arrayOf(id.toString()))
            }

            public fun deleteMarkEvent(eventID: Int, mark: Int){
                db!!.execSQL("DELETE FROM " +DB_TABLE_MARKEVENTS+
                        " WHERE "+DB_COLUMN_TYPE+" = "+mark.toString()+" AND "+
                        DB_COLUMN_EVENTID + " = "+eventID.toString()
                )
            }

            public fun closeLDbHelper() {
                if (db != null && db!!.isOpen()) {
                    db!!.close()
                    dbHelper!!.close()
                }
            }

            public fun getPicNameByURL(url:String):String{
                return Uri.parse(url).getLastPathSegment()+"."+ Bitmap.CompressFormat.JPEG
            }
        }
}