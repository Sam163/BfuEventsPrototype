package com.example.sam.myapplication.model

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.example.sam.myapplication.objects.Event
import com.example.sam.myapplication.objects.NewEvent
import com.example.sam.myapplication.toSqlDate

class NewEventManager(var context:Context, var newEvent: NewEvent): AsyncTask<Void, Void, Boolean>() {
    public var inPostExecute: (Boolean,String) -> Unit={b,s->}
    public var inCancelled: () -> Unit={->}

    override fun doInBackground(vararg params: Void): Boolean? {
       /* if(ServerAPI.Connector.sendNewEvent(newEvent)){
            return true
        }else {
            return false
        }*/
        return true
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
        //inPostExecute(success, ServerAPI.Connector.mess)
    }
    override fun onCancelled() {
        inCancelled()
    }
}