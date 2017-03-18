package com.example.sam.myapplication


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import com.example.sam.myapplication.objects.DayEvent
import com.example.sam.myapplication.DayEventAdapter
import com.example.sam.myapplication.objects.Event
import com.example.sam.myapplication.R
import com.example.sam.myapplication.model.DataManager
import java.util.*
import kotlin.concurrent.thread

class AllEventsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_all_events, container, false)
        graphicalPart(view)
        retainInstance = true

        return view
    }

    override fun onResume() {
        super.onResume()
        activity.title = "Все события"
    }

    private fun graphicalPart(view: View){
        var recycler = view.findViewById(R.id.all_events_recycler) as RecyclerView
        var adapter = DayEventAdapter()

        var refreshBar = view.findViewById(R.id.refresh) as SwipeRefreshLayout
        refreshBar.setOnRefreshListener {

            var refrashDB=DataManager(context)
            refrashDB.onSuccess={
                adapter.dataChange(DataManager.getDayEvents())
                refreshBar.isRefreshing=false
            }
            refrashDB.onFailur={
                adapter.dataChange(DataManager.getDayEvents())
                refreshBar.isRefreshing=false
            }
            refrashDB.refrashDb()
        }

        adapter.activity = activity
        adapter.data = DataManager.getDayEvents()
        recycler.layoutManager = LinearLayoutManager(view.context)
        recycler.adapter = adapter
    }

}
