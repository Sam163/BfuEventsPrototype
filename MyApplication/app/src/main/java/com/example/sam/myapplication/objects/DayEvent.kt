package com.example.sam.myapplication.objects

import com.example.sam.myapplication.objects.Event
import java.sql.Date

class DayEvent(var date: Date) {
    var _listEvents = ArrayList<Event>()

    fun addEvent(ev: Event){
        _listEvents.add(ev)
    }

}