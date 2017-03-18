package com.example.sam.myapplication.model

import android.content.Context
import android.widget.Toast
import com.example.sam.myapplication.RegistrationActivity
import com.example.sam.myapplication.objects.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by Sam on 15.03.2017.
 */
class LoginManager (var context: Context, var login:String, var pass:String) {
    public var onSuccess: (Boolean) -> Unit = {output -> }
    public var onFailur: (String) -> Unit = {output -> }

    public fun LogIn() {

        var adapter = Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var network = adapter.create<CheckUser1>(CheckUser1::class.java!!)
        var call = network.checkUser(login,pass)
        var result:List<User>?=null
        call.enqueue(object : Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                if(response!=null) {
                    result = response.body()

                    if (result!!.size == 1 && result!![0].id_user != 0) {
                        CurrentUser._id = result!![0].id_user
                        CurrentUser.name = result!![0].fullName
                        CurrentUser.permission = result!![0].id_group
                        CurrentUser.login = login
                        onSuccess(true)
                    } else {
                        onSuccess(false)
                    }
                }else {
                    onFailur(response!!.message())
                }
            }
            override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
                onFailur(t!!.localizedMessage)
            }

        })


    }

}
