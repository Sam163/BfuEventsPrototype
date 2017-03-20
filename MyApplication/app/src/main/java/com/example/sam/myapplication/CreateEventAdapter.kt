package com.example.sam.myapplication

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.sam.myapplication.objects.Event
import com.example.sam.myapplication.R
import com.example.sam.myapplication.model.DataManager
import java.text.SimpleDateFormat
import java.util.*

class CreateEventAdapter : RecyclerView.Adapter<CreateEventAdapter.ViewHolder>() {
    var data = ArrayList<Event>()
    lateinit var activity: Activity
    lateinit var clickCallback: onCreateEventClickListener

    interface onCreateEventClickListener{
        fun onEventClick(event: Event)
        fun onEditClick(event:Event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.create_event_card_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var event = data[position]

        holder!!.name.text = event.name

        var formatDate = SimpleDateFormat("dd.MM.YY")

        var formatTime = SimpleDateFormat("HH:mm")//Время по формату
        var time = formatDate.format(toSqlDate(data[position].date)) + "\n" + formatTime.format(toSqlTime(data[position].timeBegin))

        holder.time.text =  time

        clickCallback = activity as onCreateEventClickListener
        holder.view.setOnClickListener {
            clickCallback.onEventClick(event)
        }
        holder.img.setOnClickListener {
            var del=DataManager(holder!!.view.context)
            del.onSuccess={
                data.removeAt(position)
                notifyDataSetChanged()
            }
            del.onFailur={}
            del.deleteEvent(event.id)
            //clickCallback.onEditClick(event)//todo
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var view: View
        var name: TextView
        var time: TextView
        var img: ImageView

        init{
            view = itemView
            time = view.findViewById(R.id.time) as TextView
            name = view.findViewById(R.id.name_event) as TextView
            img = view.findViewById(R.id.edit) as ImageView
        }
    }
}