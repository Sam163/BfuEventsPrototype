package com.example.sam.myapplication


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.sam.myapplication.objects.Event


class EditEventFragment : Fragment() {

    companion object{
        lateinit var event: Event
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_edit_event, container, false)

        var eventName = view.findViewById(R.id.evname_txt) as TextView
        var eventDescription = view.findViewById(R.id.evinfo_txt) as TextView
        var img = view.findViewById(R.id.img) as ImageView

        eventName.text = event.name
        eventDescription.text = event.info
        //TODO сделать что-нибудь с картинкой

        var btnSave = view.findViewById(R.id.btn_save) as Button
        var btnDelete = view.findViewById(R.id.btn_delete) as Button

        btnSave.setOnClickListener {
            //TODO взаимодейтсвие с сервером

            var fragment = MyCreatedEventsFragment()
            var tr = activity.supportFragmentManager.beginTransaction()
            tr.replace(R.id.fragment, fragment)
            tr.commit()
        }
        btnDelete.setOnClickListener {
            //TODO удаление события

            var fragment = MyCreatedEventsFragment()
            var tr = activity.supportFragmentManager.beginTransaction()
            tr.replace(R.id.fragment, fragment)
            tr.commit()
        }

        return view
    }

}
