package com.example.sam.myapplication

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sam.myapplication.objects.Event
import com.example.sam.myapplication.R
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    var data = ArrayList<Event>()
    lateinit var activity: Activity
    lateinit var clickCallback: onEventClickListener

    interface onEventClickListener{
        fun onEventClick(event: Event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.day_card_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var event = data[position]

        holder!!.name.text = event.name

        var format = SimpleDateFormat("HH:mm")
        var time = format.format(toSqlTime(data[position].timeBegin))
        holder.time.text =  time

        clickCallback = activity as onEventClickListener
        holder.view.setOnClickListener {
            clickCallback.onEventClick(event)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var view: View
        var name: TextView
        var time: TextView

        init{
            view = itemView
            time = view.findViewById(R.id.time) as TextView
            name = view.findViewById(R.id.name_event) as TextView
        }
    }
}