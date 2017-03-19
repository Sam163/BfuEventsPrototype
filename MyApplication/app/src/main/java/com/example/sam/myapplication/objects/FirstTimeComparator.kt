package com.example.sam.myapplication.objects

import java.util.Comparator

/**
 * Created by Vlad on 19.03.2017.
 */
public class FirstTimeComparator() : Comparator<Event> {
    override fun compare(o1: Event, o2: Event): Int {
        return o1.timeBegin.compareTo(o2.timeBegin)
    }

}