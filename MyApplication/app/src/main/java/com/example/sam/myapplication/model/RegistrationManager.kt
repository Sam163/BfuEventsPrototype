package com.example.sam.myapplication.model

import android.content.Context
import android.widget.Toast
import com.example.sam.myapplication.objects.Answer
import com.example.sam.myapplication.objects.User
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class RegistrationManager (var context:Context ,var login:String, var password :String, var name:String) {
    public var onSuccess: (String) -> Unit = {output -> }
    public var onFailur: (String) -> Unit = {output -> }

    public fun RegToServer() {

        var adapter = Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var network = adapter.create<RegisterUser>(RegisterUser::class.java!!)
        var call = network.insertUser(login,password,name)
        call.enqueue(object : Callback<List<Answer>> {
            override fun onResponse(call: Call<List<Answer>>?, response: Response<List<Answer>>?) {
                if(response!!.isSuccessful()) {

                    var output = response.body()
                    if(output[0].answer =="successfully registered")
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