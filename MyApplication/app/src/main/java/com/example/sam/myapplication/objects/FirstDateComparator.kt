package com.example.sam.myapplication.objects

import java.util.Comparator

/**
 * Created by Vlad on 19.03.2017.
 */

public class FirstDateComparator() : Comparator<DayEvent> {
    override fun compare(o1: DayEvent, o2: DayEvent): Int {
        return o1.date.compareTo(o2.date)
    }

}
