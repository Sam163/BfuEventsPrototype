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
    @POST("/insertUser.php")
    public fun insertUser(
            @Field("email") email:String,
            @Field("password") password:String,
            @Field("fullName") fullName:String,
            callback: Call<Retrofit>)
}

interface TableMarkEvent {
    @FormUrlEncoded
    @POST("/getLikes.php")
    public fun getMarkedEvents(
            @Field("idUser") idUser:Int): Call<List<MarkedEvent>>
}

interface EventAPI {
    @GET("/getEvents.php")
    fun getEvent(): Call<List<Event>>
}

interface TableMyEvents {
    @FormUrlEncoded
    @POST("/getIdEvent.php")
    public fun getMyEvents(
            @Field("idUser") idUser:Int): Call<List<MyEvent>>
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

class ServerAPI(){
    companion object{

        //стандартные сообщения
        val MESS_CONNECTED="connected"
        val MESS_NOCONNECTION="no connection"
        val MESS_NOUSER="no such user"
        val MESS_ACC_CREATED="account created successfully"
        val MESS_ACC_EXIST="account already exist"
        //объект для подключений
        var Connector= ServerAPI()
    }



    private val SQLTasks = "{call AdminInfo}"//в jdbc такие строки для вызова процедур на серваке
    private val SQLTaskStatusDone ="{call change_status(?,?)}"

    private var connection: Connection? = null

    public var mess=""//для сообщений активности о выполнении запросов
        get() {return  field}
        private set(value){field=value}

    //для аутентификации и всех действий пользователя
    //(нужна всегда проверка пароля для закрытия доступа неаутентифицировавшимся пользователям)
    //т.е. поидее надо выполнять всегда когда пользователь делает запрос к бд - добавляет событие или ставит отметку
    /*public fun ConnectToServer(login:String, pass:String ):Boolean {
        try {
            //получение ip от сервера
            //Thread.sleep(1000)//запрос аутентификации
            //подключение - пароль и логин
            if(true){//если такой юзер есть
                //возвращаем id и права пользователя
                CurrentUser.login=login
                CurrentUser.pass=pass
                CurrentUser._id =1445//добавить присваивание
                CurrentUser.permission=1//добавить присваивание

                mess = MESS_CONNECTED
                return true
            } else {//если такого юзера нет
                mess = MESS_NOUSER
                return true
            }
        }
        catch (ex: Exception){
            mess=ex.toString()
            Log.e("ConnectToServer",mess)
            return false
        }
    }*/

    //создание акка
    //единственная операция без проверки пользователя, пароля
    public fun createAccount(login:String, pass:String):Boolean{
        try {
            //try to connect
            Thread.sleep(3000)//иммитируем подключение
            //вызов процедуры создания акка на серваке

            //проверяем есть литакой акк с такой же почтой
            if(false/*сюда результат вызова проверки*/){//если такой юзер есть
                mess = MESS_ACC_EXIST
                return false
            }
            //если такого юзера нет
            //создаем новую запись в бд с новым аком
            mess = MESS_ACC_CREATED
            return true
        }
        catch(ex: Exception){
            mess=ex.toString()
            Log.e("CreationAccount",mess)
            return false
        }
    }

    public fun getEvents(taskdata:ArrayList<Event>):Boolean{
        try {
            //переподключение
            //выполнение запроса и получение данных
            taskdata.add(Event(1, Date(0), toSqlTime("10:00:00"), toSqlTime("10:00:00"),"gg0","guio","D://fd",5,5,"vasya","email" ))
            taskdata.add(Event(2, Date(2000), toSqlTime("10:00:00"), toSqlTime("10:00:00"),"gg2","guio","D://fd",5,5,"vasya","email" ))
            taskdata.add(Event(3, Date(4000), toSqlTime("10:00:00"), toSqlTime("10:00:00"),"gg3","guio","D:fd",5,5,"vasya","email" ))
            taskdata.add(Event(4, Date(500000000000), toSqlTime("10:00:00"), toSqlTime("10:00:00"),"gg4","guio","D:fd",5,5,"vasya","email" ))
            taskdata.add(Event(5, Date(500000000000), toSqlTime("10:00:00"), toSqlTime("10:00:00"),"gg5","guio","D:fd",5,5,"vasya","email" ))
            taskdata.add(Event(6, Date(600000000), toSqlTime("10:00:00"), toSqlTime("10:00:00"),"gg6","guio","D:fd",5,5,"vasya","email" ))
            taskdata.add(Event(7, Date(7000000), toSqlTime("10:00:00"), toSqlTime("10:00:00"),"g7call","guio","D:d",5,5,"vasya","email" ))
            taskdata.add(Event(8, Date(8), toSqlTime("10:00:00"), toSqlTime("10:00:00"),"g8call","guio","D:d",5,5,"vasya","email" ))
            //Log.e("CreationAccount",taskdata.size.toString())
            return true
        }
        catch(ex: Exception){
            mess=ex.toString()
            Log.e("getEvents",mess)
            return false
        }
    }

    public fun getLinkTagEvent(arrTagID: ArrayList<Int>,arrEventID: ArrayList<Int>):Boolean{
        try {
            //переподключение
            //выполнение запроса и получение данных
            return true
        }
        catch(ex: Exception){
            mess=ex.toString()
            Log.e("getLinkTagEvent",mess)
            return false
        }
    }

    public fun getUsersEvents(arrEventID: ArrayList<Int>):Boolean{
        try {
            //переподключение
            //выполнение запроса и получение данных
            return true
        }
        catch(ex: Exception){
            mess=ex.toString()
            Log.e("getLinkTagEvent",mess)
            return false
        }
    }

    public fun getUsersMarks(arrEventID: ArrayList<Int>, arrMark: ArrayList<Int>):Boolean{
        try {
            //переподключение
            //выполнение запроса и получение данных
            return true
        }
        catch(ex: Exception){
            mess=ex.toString()
            Log.e("getLinkTagEvent",mess)
            return false
        }
    }

    public fun getTags(arrTagID: ArrayList<Int>,arrTagName: ArrayList<String>):Boolean{
        try {
            //переподключение
            //выполнение запроса и получение данных
            return true
        }
        catch(ex: Exception){
            mess=ex.toString()
            Log.e("getLinkTagEvent",mess)
            return false
        }
    }

    public fun sendNewEvent(newEvent: NewEvent):Boolean{
        try {
            //переподключение
            //запрос
            //выполнение запроса и получение данных
            return false
        }
        catch(ex: Exception){
            mess=ex.toString()
            Log.e("sendNewEvent",mess)
            return false
        }
    }

    public fun sendMarkEvent(idEvent: Int, markType: Int):Boolean{
        try {
            //переподключение
            //запрос
            //выполнение запроса и получение данных
            return true
        }
        catch(ex: Exception){
            mess=ex.toString()
            Log.e("sendMarkEvent",mess)
            return false
        }
    }

    public fun removeMarkEvent(idEvent: Int, markType: Int):Boolean{
        try {
            //переподключение
            //запрос
            //выполнение запроса и получение данных
            return true
        }
        catch(ex: Exception){
            mess=ex.toString()
            Log.e("sendMarkEvent",mess)
            return false
        }
    }
}