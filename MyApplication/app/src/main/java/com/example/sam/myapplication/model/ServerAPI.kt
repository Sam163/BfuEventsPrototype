package com.example.sam.myapplication.model

import android.util.Log
import android.widget.Toast
import com.example.sam.myapplication.model.CurrentUser
import com.example.sam.myapplication.objects.*
import com.example.sam.myapplication.toSqlTime
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.sql.Connection
import java.sql.Date
import java.sql.DriverManager
import java.sql.Time

val ROOT_URL = "http://eventofbfu.96.lt/"

interface RegisterUser {
    @FormUrlEncoded
    @POST("insertUser.php")
    public fun insertUser(
            @Field("email") email:String,
            @Field("password") password:String,
            @Field("fullName") fullName:String
    ): Call<List<Answer>>
}

interface TableMarkEvent {
    @FormUrlEncoded
    @POST("/getLikes.php")
    public fun getMarkedEvents(
            @Field("idUser") idUser:Int
    ): Call<List<MarkedEvent>>
}

interface EventAPI {
    @GET("/getEvents.php")
    fun getEvent(): Call<List<Event>>
}

interface TableMyEvents {
    @FormUrlEncoded
    @POST("/getIdEvent.php")
    public fun getMyEvents(
            @Field("idUser") idUser:Int
    ): Call<List<MyEvent>>
}

interface TableTagEvent{
    @GET("/getEventCat.php")
    fun getEventCat(): Call<List<EventTag>>
}

interface TableTag{
    @GET("/getCategory.php")
    fun getCategory(): Call<List<Tag>>
}

interface CheckUser1 {

    @FormUrlEncoded
    @POST("/checkUser1.php")
    public fun checkUser(
            @Field("email") email:String,
            @Field("password") password:String
    ):Call<List<User>>
}