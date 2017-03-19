package com.example.sam.myapplication.model

import android.content.Context
import android.os.AsyncTask
import com.example.sam.myapplication.objects.Answer
import com.example.sam.myapplication.objects.NewEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Sam on 15.03.2017.
 */
class MarkingManager (var context: Context, var idEvent: Int, var markType: Int){
    public var onSuccess: (String) -> Unit={s->}
    public var onFailur: (String) -> Unit={s->}

    companion object{
        fun getMarkInterested():Int{
            return LDbHelper.MARK_TYPE_INTERESTED
        }

        fun getMarkWillGo():Int{
            return LDbHelper.MARK_TYPE_ILLGO
        }

        fun getCurrentUserMark(id_event:Int):Boolean{
            var m= LDbHelper.getMarkEvent(id_event)
            if(m==LDbHelper.MARK_TYPE_ILLGO || m==LDbHelper.MARK_TYPE_INTERESTED)
                return true
            else
                return false
        }
    }

    public fun SendLikeToServer() {

        var adapter = Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var network = adapter.create<AddLike>(AddLike::class.java!!)
        var call = network.insertLike(CurrentUser._id, idEvent, markType)
        call.enqueue(object : Callback<List<Answer>> {
            override fun onResponse(call: Call<List<Answer>>?, response: Response<List<Answer>>?) {
                if(response!!.isSuccessful()) {

                    var output = response.body()
                    if(output[0].answer =="successfully") {
                        onSuccess(output[0].answer)
                        DataManager.setLikeOnSuccessTransaction(idEvent, markType)
                    }
                    else{
                        onFailur(output[0].answer)
                    }
                }else {
                    onFailur(response!!.message())
                }
            }
            override fun onFailure(call: Call<List<Answer>>?, t: Throwable?) {
                onFailur(t!!.message.toString())
            }

        })
    }
}