package com.example.sam.myapplication


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sam.myapplication.objects.Event
import com.example.sam.myapplication.EventAdapter
import com.example.sam.myapplication.R
import java.util.*

class DayEventFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_day_event, container, false)
        graficalPart(view)
        return view
    }

    companion object{
        lateinit var events: ArrayList<Event>
    }

    fun graficalPart(view: View){

        var recycler = view.findViewById(R.id.day_recycler) as RecyclerView
        var adapter = EventAdapter()
        adapter.data = events
        adapter.activity = activity
        recycler.layoutManager = LinearLayoutManager(view.context)
        recycler.adapter = adapter
    }

}
