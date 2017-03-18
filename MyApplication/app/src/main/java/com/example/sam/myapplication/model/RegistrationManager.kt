package com.example.sam.myapplication.model

import android.content.Context
import android.widget.Toast
import com.example.sam.myapplication.objects.User
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

    fun RegToServer() {
        var adapter = Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var network = adapter.create<RegisterUser>(RegisterUser::class.java!!)
        var call = network.insertUser(login,password,name)
        var result:List<User>?=null
        call.enqueue(object : Callback<Retrofit> {
            override fun onResponse(call: Call<Retrofit>?, response: Response<Retrofit>?) {
                if(response!=null) {
                    var reader: BufferedReader? = null

                    var output = ""

                    try {
                        //reader = BufferedReader(InputStreamReader(response.body().`in`()))

                        output = reader!!.readLine()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    onSuccess(output)
                }else {
                    onFailur(response!!.message())
                }
            }
            override fun onFailure(call: Call<Retrofit>?, t: Throwable?) {
                onFailur(t!!.localizedMessage)
            }

        })
    }
}