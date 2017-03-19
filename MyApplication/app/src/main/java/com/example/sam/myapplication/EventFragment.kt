package com.example.sam.myapplication


import android.app.AlertDialog
import android.app.usage.UsageEvents
import android.content.ContentValues
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.provider.CalendarContract.Reminders
import android.provider.ContactsContract
import android.util.EventLog
import java.util.*
import android.content.Intent
import android.graphics.Color
import android.widget.*
import com.example.sam.myapplication.objects.Event
import com.example.sam.myapplication.R
import com.example.sam.myapplication.model.MarkingManager
import com.example.sam.myapplication.model.PictureManager


class EventFragment : Fragment() {

    companion object{
        lateinit var event: Event
    }

    val TXT_INTERESTED="Заинтересован"
    val TXT_NOT_INTERESTED="Не заинтересован"

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_event, container, false)
        graphicalPart()

        var evNameText = view.findViewById(R.id.evname_txt) as TextView
        evNameText.text = event.name
        var evInfoText = view.findViewById(R.id.evinfo_txt) as TextView
        evInfoText.text = event.info
        var userNameText = view.findViewById(R.id.user_name_txt) as TextView
        userNameText.text= event.fullName
        var userMailText = view.findViewById(R.id.user_mail_txt) as TextView
        userMailText.text = event.email

        var evImg = view.findViewById(R.id.img) as ImageView
        var progressImg = view.findViewById(R.id.progress_image) as ProgressBar

        var imgLoader=PictureManager(context,event.picUrl)
        imgLoader.inPostExecute={map, success->
            if( success){
                evImg.setImageBitmap(map)
                evImg.visibility = View.VISIBLE
                progressImg.visibility = View.GONE
            }
            else{
                evImg.visibility = View.VISIBLE
                progressImg.visibility = View.GONE
            }
        }
        imgLoader.execute()

        var likeButton = view.findViewById(R.id.like_button) as Button
        if(MarkingManager.getCurrentUserMark(event.id)){
            likeButton.setBackgroundResource(R.drawable.btn_like)
            likeButton.setTextColor(Color.WHITE)
            likeButton.text =TXT_INTERESTED
        }
        else {
            likeButton.setBackgroundResource(R.drawable.btn_unlike)
            likeButton.setTextColor(Color.BLACK)
            likeButton.text = TXT_NOT_INTERESTED
        }

        likeButton.setOnClickListener {
            if (MarkingManager.getCurrentUserMark(event.id)){
                likeButton.setBackgroundResource(R.drawable.btn_unlike)
                likeButton.setTextColor(Color.BLACK)
                likeButton.text = "Не заинтересован"
                /*var query=MarkingManager(context, event.id, MarkingManager.getMarkInterested(), false)
                query.inPostExecute={
                    success,s->
                    if(!success){
                        Toast.makeText(activity.applicationContext, "Не удалось отправить запрос!", Toast.LENGTH_SHORT).show()
                        likeButton.setBackgroundResource(R.drawable.btn_like)
                        likeButton.setTextColor(Color.WHITE)
                        likeButton.text = "Заинтересован"
                    }
                }
                query.execute()*/
            }
            else{
                likeButton.setBackgroundResource(R.drawable.btn_like)
                likeButton.setTextColor(Color.WHITE)
                likeButton.text = "Заинтересован"
                callAlertDialog(event)
                /* var query=MarkingManager(context, event.id, MarkingManager.getMarkInterested(), true)
                query.inPostExecute={
                    success,s->
                    if(success){
                        callAlertDialog()
                    }
                    else{
                        Toast.makeText(activity.applicationContext, "Не удалось отправить запрос!", Toast.LENGTH_SHORT).show()
                        likeButton.setBackgroundResource(R.drawable.btn_unlike)
                        likeButton.setTextColor(Color.BLACK)
                        likeButton.text = "Не заинтересован"
                    }
                }
                query.execute()*/
            }
        }
        retainInstance = true
        return view
    }

    fun graphicalPart(){

    }

    override fun onResume() {
        super.onResume()
        activity.title = "О событии"
    }

    private fun callAlertDialog(event : Event){
        var ad = AlertDialog.Builder(activity)
        ad.setTitle("Напоминание")
        ad.setMessage("Добавить в календарь напоминание?")
        ad.setPositiveButton("Да", DialogInterface.OnClickListener { dialog, which ->
            var dateTimeBegin = Calendar.getInstance()
            var date = toSqlDate(event.date)
            var timeBegin = toSqlTime(event.timeBegin)
            dateTimeBegin.set(date.year, date.month, date.day, timeBegin.hours, timeBegin.minutes)

            var dateTimeEnd = Calendar.getInstance()
            var timeEnd = toSqlTime(event.timeEnd)
            dateTimeEnd.set(date.year, date.month, date.day, timeEnd.hours, timeEnd.minutes)

            addToCalendar(dateTimeBegin,dateTimeEnd, event.name)
        })
        ad.setNegativeButton("Нет", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        ad.show()
    }
    private fun addToCalendar(dateBegin: Calendar, dateEnd: Calendar, title:String){
        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra("beginTime", dateBegin.timeInMillis)
        intent.putExtra("allDay", false)
        intent.putExtra("rrule", "FREQ=YEARLY")
        intent.putExtra("endTime", dateEnd.timeInMillis)
        intent.putExtra("title", title)
        startActivity(intent)
    }
}
