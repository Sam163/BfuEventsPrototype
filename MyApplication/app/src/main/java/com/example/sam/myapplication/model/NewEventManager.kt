package com.example.sam.myapplication.model

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.example.sam.myapplication.objects.Answer
import com.example.sam.myapplication.objects.Event
import com.example.sam.myapplication.objects.NewEvent
import com.example.sam.myapplication.toSqlDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewEventManager(var context:Context, var newEvent: NewEvent) {
    public var onSuccess: (String) -> Unit={s->}
    public var onFailur: (String) -> Unit={s->}

    public fun SendNewEventToServer() {

        var adapter = Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var network = adapter.create<InsertEvent>(InsertEvent::class.java!!)
        var call = network.insertEvent(newEvent.date.toString(),newEvent.timeBegin.toString(),newEvent.timeEnd.toString(),
                newEvent.name,newEvent.info,newEvent.id_user.toString(),newEvent.tagID.toString())
        call.enqueue(object : Callback<List<Answer>> {
            override fun onResponse(call: Call<List<Answer>>?, response: Response<List<Answer>>?) {
                if(response!!.isSuccessful()) {
                    var output = response.body()
                    if(output[0].answer =="successfully Add Events")
                        onSuccess("")
                    else{
                        onFailur("")
                    }
                }else {
                    onFailur(response!!.message())
                }
            }
            override fun onFailure(call: Call<List<Answer>>?, t: Throwable?) {
                onFailur(t!!.localizedMessage)
            }

        })
    }
}