package com.example.sam.myapplication


import android.app.Activity
import android.app.DialogFragment
import android.app.FragmentManager
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.sam.myapplication.objects.Event
import com.example.sam.myapplication.R
import com.example.sam.myapplication.CreateEventDialogFragment
import com.example.sam.myapplication.EventAdapter
import com.example.sam.myapplication.model.DataManager
import java.util.*

class MyCreatedEventsFragment : Fragment() {

    lateinit var events: ArrayList<Event>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_create, container, false)
        graphicalPart(view)
        var fab = view.findViewById(R.id.fab) as FloatingActionButton
        fab.visibility = View.VISIBLE
        fab.setOnClickListener { view ->
            var createDialog = CreateEventDialogFragment()
            createDialog.fragment = this
            createDialog.show(activity.fragmentManager, null)
        }
        retainInstance = true
        return view
    }

    lateinit var adapter: EventAdapter

    fun graphicalPart(view: View){
        var recycler = view.findViewById(R.id.day_recycler) as RecyclerView
        var adapter = EventAdapter()


        adapter.data = DataManager.getMyCreatedEvents()
        adapter.activity = activity
        recycler.layoutManager = LinearLayoutManager(view.context)
        recycler.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        activity.title = "Созданные события"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Activity.RESULT_OK){
            var event = data!!.getSerializableExtra("event") as Event
            events.add(event)
            //adapter.data.add(event)
            adapter.notifyDataSetChanged()
        }
    }
}
