package com.example.sam.myapplication.model

import android.content.Context
import android.widget.Toast
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class RegistrationManager (var context:Context ,var login:String, var password :String, var name:String) {
    public var onSuccess: (String) -> Unit = {output -> }
    public var onFailur: (String) -> Unit = {output -> }

    /*fun RegToServer() {
        val adapter = RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build()

        val api = adapter.create<RegisterUser>(RegisterUser::class.java!!)

        api.insertUser(
                login,
                password,
                name,
                object : Callback<Response> {
                    override fun success(result: Response, response: Response) {

                        var reader: BufferedReader? = null

                        var output = ""

                        try {
                            reader = BufferedReader(InputStreamReader(result.getBody().`in`()))

                            output = reader!!.readLine()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        onSuccess(output)

                        Toast.makeText(context, output, Toast.LENGTH_LONG).show()
                    }

                    override fun failure(error: RetrofitError?) {
                        onFailur(error.toString())
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
                    }
                }
        )
    }*/
}