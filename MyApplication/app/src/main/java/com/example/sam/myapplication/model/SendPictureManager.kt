package com.example.sam.myapplication.model

import android.content.Context
import com.example.sam.myapplication.objects.Answer
import com.example.sam.myapplication.objects.PictureToSend
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.Toast
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.MultipartBody
import java.io.File


/**
 * Created by Sam on 20.03.2017.
 */
class SendPictureManager (var context: Context, var ps:PictureToSend) {
    public var onSuccess: () -> Unit = { -> }
    public var onFailur: () -> Unit = { -> }

    public fun send() {

        var adapter = Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var network = adapter.create<PostImage>(PostImage::class.java!!)
        // Map is used to multipart the file using okhttp3.RequestBody
        var file = File(ps.uri.path)

        // Parsing any Media type file
        var requestBody = RequestBody.create(MediaType.parse("*/*"), file)
        var fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody)
        var filename = RequestBody.create(MediaType.parse("text/plain"), file.getName())

        var call = network.uploadFile(fileToUpload, filename, ps.id)
        call.enqueue(object : Callback<List<Answer>> {
            override fun onResponse(call: Call<List<Answer>>, response: Response<List<Answer>>) {
                val output = response.body()
                if (output != null) {
                    onSuccess()
                        Toast.makeText(context, "ololo", Toast.LENGTH_SHORT).show()
                } else {
                    onFailur()
                    Toast.makeText(context, "wtf", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Answer>>, t: Throwable) {
                onFailur()
            }
        })
    }
}
