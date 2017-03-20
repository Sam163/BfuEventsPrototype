package com.example.sam.myapplication.model

import android.util.Log
import android.widget.Toast
import com.example.sam.myapplication.model.CurrentUser
import com.example.sam.myapplication.objects.*
import com.example.sam.myapplication.toSqlTime
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.sql.Connection
import java.sql.Date
import java.sql.DriverManager
import java.sql.Time
import okhttp3.RequestBody
import okhttp3.MultipartBody
import retrofit2.http.*
import retrofit2.http.POST
import retrofit2.http.Multipart




val ROOT_URL = "http://eventofbfu.96.lt/"

interface PostImage {
    @Multipart
    @POST("uploadImage.php")
    fun uploadFile(
            @Part file: MultipartBody.Part,
            @Part("file") name: RequestBody,
            @Field("id_event") id_event:Int): Call<List<Answer>>
}

interface RegisterUser {
    @FormUrlEncoded
    @POST("insertUser.php")
    public fun insertUser(
            @Field("email") email:String,
            @Field("password") password:String,
            @Field("fullName") fullName:String
    ): Call<List<Answer>>
}

interface AddLike {
    @FormUrlEncoded
    @POST("addLike.php")
    public fun insertLike(
            @Field("id_user") idUser:Int,
            @Field("id_event") idEvent:Int,
            @Field("id_type") idType:Int
    ): Call<List<Answer>>
}

interface RemoveLike {
    @FormUrlEncoded
    @POST("removeLike.php")
    public fun removeLike(
            @Field("id_user") idUser:Int,
            @Field("id_event") idEvent:Int,
            @Field("id_type") idType:Int
    ): Call<List<Answer>>
}

interface InsertEvent {
    @FormUrlEncoded
    @POST("insertEvent.php")
    public fun insertEvent(
            @Field("date") date:String,
            @Field("timeBegin") timeBegin:String,
            @Field("timeEnd") timeEnd:String,
            @Field("name") name:String,
            @Field("info") info:String,
            @Field("id_user") _id_user:String,
            @Field("tagID") _tagID:String
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