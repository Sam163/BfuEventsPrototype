package com.example.sam.myapplication.model

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.widget.Toast
import com.example.sam.myapplication.model.CurrentUser.Companion.login
import com.example.sam.myapplication.model.ServerAPI
import com.example.sam.myapplication.model.LDbHelper
import com.example.sam.myapplication.objects.*
import com.example.sam.myapplication.toSqlDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.security.AccessControlContext
import java.sql.Date

class DataManager(var context: Context) {

    public var onSuccess: (String) -> Unit = {output -> }
    public var onFailur: (String) -> Unit = {output -> }

    companion object{
        public var accession=true//=false во время обновления
            get() {return field}
            private set(value) {field=value}
        fun getMyCreatedEvents():ArrayList<Event>{
            if(accession) {
                return LDbHelper.getMyEvents()
            }
            else
                return ArrayList<Event>()
        }

        fun getMarkedEvents():ArrayList<Event>{
            if(accession) {
                return LDbHelper.getMarkedEvents()
            }
                else return ArrayList<Event>()
        }

        fun getEvents():ArrayList<Event>{
            if(accession) {
                return LDbHelper.getEvents()
            }else
                return ArrayList<Event>()
        }

        fun getEventsByDate(date:java.util.Date):ArrayList<Event>{
            if(accession) {
                return LDbHelper.getEventsByDate(toSqlDate(date))
            }else
                return ArrayList<Event>()
        }

        fun getDayEvents():ArrayList<DayEvent> {
            if(accession) {
                var e = LDbHelper.getEvents()
                var de = ArrayList<DayEvent>()
                if (e.size > 0) {
                    de.add(DayEvent(toSqlDate(e[0].date)))
                    de[de.size - 1]._listEvents.add(e[0])
                    for (i in 1..e.size - 1) {
                        var b = true
                        for (j in 0..de.size - 1) {
                            if (de[j].date == toSqlDate(e[i].date)) {
                                de[j]._listEvents.add(e[i])
                                b = false
                                break
                            }
                        }
                        if (b) {
                            de.add(DayEvent(toSqlDate(e[i].date)))
                            de[de.size - 1]._listEvents.add(e[i])
                        }
                    }
                }
                return de
            }else
                return ArrayList<DayEvent>()
        }
        fun getEventsByDate(date:String):ArrayList<Event>{
            if(accession) {
                return LDbHelper.getEventsByDate(toSqlDate(date))
            } else
                return ArrayList<Event>()
        }
    }

    public fun cacheFreshDB(){
        if(LDbHelper.isLocalDataBaseExist(context)) {
            LDbHelper.initLDbHelper(context)
            LDbHelper.clearLocalDataBase()
            PictureManager.clearPicturesCache(context)
        }else
            LDbHelper.initLDbHelper(context)
        cacheTableEvents()
    }

    private fun cacheTableEvents() {
        var adapter = Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var network = adapter.create<EventAPI>(EventAPI::class.java!!)
        var call = network.getEvent()
        var result:List<Event>?=null

        call.enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>?, response: Response<List<Event>>?) {
                if(response!!.isSuccessful) {
                    result = response!!.body()
                    if(result!=null) {
                        accession=false
                        LDbHelper.addEvents(result!!)
                        accession=true
                    }
                    cacheTableTagEvent()
                }else {
                    onFailur(response!!.message())
                }
            }
            override fun onFailure(call: Call<List<Event>>?, t: Throwable?) {
                onFailur(t!!.localizedMessage)
            }

        })
    }

    private fun cacheTableTagEvent(){
        var adapter = Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var network = adapter.create<TableTagEvent>(TableTagEvent::class.java!!)
        var call = network.getEventCat()
        var result:List<EventTag>?=null

        call.enqueue(object : Callback<List<EventTag>> {
            override fun onResponse(call: Call<List<EventTag>>?, response: Response<List<EventTag>>?) {
                if(response!!.isSuccessful) {
                    result = response!!.body()
                    if(result!=null) {
                        accession=false
                        LDbHelper.addLinkTagEvent(result!!)
                        accession=true
                    }
                    cacheTableTags()
                }else {
                    onFailur(response!!.message())
                }
            }
            override fun onFailure(call: Call<List<EventTag>>?, t: Throwable?) {
                onFailur(t!!.localizedMessage)
            }

        })
    }

    private fun cacheTableTags(){
        var adapter = Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var network = adapter.create<TableTag>(TableTag::class.java!!)
        var call = network.getCategory()
        var result:List<Tag>?=null

        call.enqueue(object : Callback<List<Tag>> {
            override fun onResponse(call: Call<List<Tag>>?, response: Response<List<Tag>>?) {
                if(response!!.isSuccessful) {
                    result = response!!.body()
                    if(result!=null) {
                        accession=false
                        LDbHelper.addTags(result!!)
                        accession=true
                    }
                    cacheTableMarkEvent()
                }else {
                    onFailur(response!!.message())
                }
            }
            override fun onFailure(call: Call<List<Tag>>?, t: Throwable?) {
                onFailur(t!!.localizedMessage)
            }

        })
    }

    private fun cacheTableMarkEvent(){
        var adapter = Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var network = adapter.create<TableMarkEvent>(TableMarkEvent::class.java!!)
        var call = network.getMarkedEvents(CurrentUser._id)
        var result:List<MarkedEvent>?=null

        call.enqueue(object : Callback<List<MarkedEvent>> {
            override fun onResponse(call: Call<List<MarkedEvent>>?, response: Response<List<MarkedEvent>>?) {
                if(response!!.isSuccessful) {
                    result = response!!.body()
                    if(result!=null) {
                        accession=false
                        LDbHelper.addMarkEvents(result!!)
                        accession=true
                    }
                    cacheTableMyEvents()
                }else {
                    onFailur(response!!.message())
                }
            }
            override fun onFailure(call: Call<List<MarkedEvent>>?, t: Throwable?) {
                onFailur(t!!.localizedMessage)
            }

        })
    }

    private fun cacheTableMyEvents(){
        var adapter = Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var network = adapter.create<TableMyEvents>(TableMyEvents::class.java!!)
        var call = network.getMyEvents(CurrentUser._id)
        var result:List<MyEvent>?=null

        call.enqueue(object : Callback<List<MyEvent>> {
            override fun onResponse(call: Call<List<MyEvent>>?, response: Response<List<MyEvent>>?) {
                if(response!!.isSuccessful) {
                    result = response!!.body()
                    if(result!=null) {
                        accession=false
                        LDbHelper.addMyEvents(result!!)
                        accession=true
                    }
                    accession=true
                    onSuccess("")
                }else {
                    onFailur(response!!.message())
                }
            }
            override fun onFailure(call: Call<List<MyEvent>>?, t: Throwable?) {
                onFailur(t!!.localizedMessage)
            }

        })
    }
}