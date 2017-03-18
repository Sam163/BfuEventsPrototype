package com.example.sam.myapplication.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import com.example.sam.myapplication.objects.Event
import com.example.sam.myapplication.model.ServerAPI
import com.example.sam.myapplication.model.LDbHelper
import java.io.*
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by Sam on 13.03.2017.
 */
class PictureManager(var context: Context, var strUrl:String): AsyncTask<Void, Void, Boolean>() {
    public var inPostExecute: (Bitmap?,Boolean) -> Unit={map,b->}
    public var inCancelled: () -> Unit={->}

    companion object{
        fun clearPicturesCache(context: Context){
            var arr=LDbHelper.getPicturesNames()
            for(s in arr){
                var folder = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).absolutePath
                var file=File(folder,s)
                file.delete()
            }
        }
    }

    private var bitmap:Bitmap?=null

    override fun doInBackground(vararg params: Void): Boolean {
        var folder = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).absolutePath
        var name=LDbHelper.getPicNameByURL(strUrl)
        var file=File(folder,name)
        try {
            if(file.exists()){
                var fis = FileInputStream(file)
                bitmap=BitmapFactory.decodeStream(fis)
                fis.close()
            } else{
                var url = URL(strUrl)
                var input = url.openConnection().inputStream
                bitmap=BitmapFactory.decodeStream(input)

                var fos = FileOutputStream(file)
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 75, fos)
                fos.flush()
                fos.close()
            }
        }
        catch (e: Exception) {
            return false
        }
        catch (e: MalformedURLException){
            return false
        }
        return true
    }
    override fun onPostExecute(success: Boolean) {
        inPostExecute(bitmap,success)
    }
    override fun onCancelled() {
        inCancelled()
    }
}