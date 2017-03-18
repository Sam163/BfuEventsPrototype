package com.example.sam.myapplication

import android.app.Activity
import android.app.DialogFragment
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.GregorianCalendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.example.sam.myapplication.R
import java.util.*


class DateDialogFragment: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater!!.inflate(R.layout.date_dialog_fragment, container, false)
        retainInstance = true

        var datePicker = view.findViewById(R.id.date_picker) as DatePicker
        var timePicker = view.findViewById(R.id.time_picker) as TimePicker

        timePicker.setIs24HourView(true)

        var btnOk = view.findViewById(R.id.btn_ok) as Button
        var btnCancel = view.findViewById(R.id.btn_cancel) as Button



        btnOk.setOnClickListener {
            var dateEvent = Date(datePicker.year-1900, datePicker.month,
                    datePicker.dayOfMonth, timePicker.hour, timePicker.minute)
            var data = Intent()
            data.putExtra("date", dateEvent)
            targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_OK, data)
            dismiss()
        }
        btnCancel.setOnClickListener {
            targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, null)
            dismiss()
        }


        return view
    }
}