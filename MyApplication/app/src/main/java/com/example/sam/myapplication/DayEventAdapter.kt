package com.example.sam.myapplication

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.sam.myapplication.objects.DayEvent
import com.example.sam.myapplication.objects.Event
import com.example.sam.myapplication.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DayEventAdapter : RecyclerView.Adapter<DayEventAdapter.ViewHolder>() {
    var data = ArrayList<DayEvent>()
    lateinit var activity: Activity

    private lateinit var clickCallback: onDayClickListener

    interface onDayClickListener{
        fun onDayClick(event: ArrayList<Event>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.all_events_card_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var events = data[position]._listEvents

        var format = SimpleDateFormat("dd MMM yyyy")//День по формату
        var date = format.format(data[position].date)
        holder!!.date.text =  date
        holder.numberOfEvent.text = "Количество событий: ${events.size.toString()}"

        clickCallback = activity as onDayClickListener
        holder.view.setOnClickListener{
            clickCallback.onDayClick(events)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun dataChange(dataServerLoad: ArrayList<DayEvent>){
        data = dataServerLoad
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var view: View
        var date: TextView
        var numberOfEvent: TextView

        init{
            view = itemView
            date = view.findViewById(R.id.date) as TextView
            numberOfEvent = view.findViewById(R.id.number_of_events) as TextView
        }
    }
}