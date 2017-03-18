package com.example.sam.myapplication.model

import android.content.Context
import android.os.AsyncTask
import com.example.sam.myapplication.objects.NewEvent

/**
 * Created by Sam on 15.03.2017.
 */
class MarkingManager (var context: Context, var idEvent: Int, var markType: Int, var set:Boolean): AsyncTask<Void, Void, Boolean>() {
    public var inPostExecute: (Boolean,String) -> Unit={b,s->}
    public var inCancelled: () -> Unit={->}

    companion object{
        fun getMarkInterested():Int{
            return LDbHelper.MARK_TYPE_ILLGO
        }

        fun getMarkWillGo():Int{
            return LDbHelper.MARK_TYPE_ILLGO
        }

        fun getCurrentUserMark(id_event:Int):Boolean{
            var m= LDbHelper.getMarkEvent(id_event)
            if(m==LDbHelper.MARK_TYPE_ILLGO || m==LDbHelper.MARK_TYPE_ILLGO)
                return true
            else
                return false
        }
    }

    override fun doInBackground(vararg params: Void): Boolean? {
        if(set) {
            if (ServerAPI.Connector.sendMarkEvent(idEvent, markType)) {
                return true
            } else {
                return false
            }
        }
        else{
            if (ServerAPI.Connector.removeMarkEvent(idEvent, markType)) {
                return true
            } else {
                return false
            }
        }
    }
    override fun onPostExecute(success: Boolean) {
        //?????????
        var successful=success
        /*if(successful){
            var ev= DataManager(context)
            ev.inPostExecute={b,s->
                if(!b)
                    successful=false
            }
            ev.execute()
        }*/
        //
        inPostExecute(success, ServerAPI.Connector.mess)
    }
    override fun onCancelled() {
        inCancelled()
    }
}