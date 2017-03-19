package com.example.sam.myapplication

import android.app.Activity
import android.app.DialogFragment
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.sam.myapplication.model.CurrentUser
import com.example.sam.myapplication.model.NewEventManager
import com.example.sam.myapplication.objects.NewEvent
import java.io.FileNotFoundException
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*


class CreateEventDialogFragment: DialogFragment() {
    companion object{
        val REQUEST_TIME_BEGIN = 0
        val REQUEST_TIME_END = 1
        val PICK_IMAGE = 2
    }

    interface send

    var event = NewEvent(CurrentUser._id)
    lateinit var fragment: MyCreatedEventsFragment

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater!!.inflate(R.layout.create_event_dialog_fragment, container, false)

        retainInstance = true

        var btnAddBeginDate = view.findViewById(R.id.add_begin_date) as Button
        var btnAddEndDate = view.findViewById(R.id.add_end_date) as Button
        var btnOk = view.findViewById(R.id.btn_ok) as Button

        var nameText = view.findViewById(R.id.event_name) as TextView
        var descriptionText = view.findViewById(R.id.event_description) as TextView
        var img = view.findViewById(R.id.img) as ImageView

        img.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE)
        }

        btnAddBeginDate.setOnClickListener {
            var dialog = DateDialogFragment()
            dialog.setTargetFragment(this, REQUEST_TIME_BEGIN)
            dialog.show(fragmentManager,null)
        }
        btnAddEndDate.setOnClickListener {
            var dialog = TimeDialogFragment()
            dialog.setTargetFragment(this, REQUEST_TIME_END)
            dialog.show(fragmentManager,null)
        }
        btnOk.setOnClickListener {
            var today = GregorianCalendar.getInstance()
            var todayDate = today.time

            if(nameText.text.toString() == "" || descriptionText.text.toString() == "")
                Toast.makeText(activity.applicationContext, "Имеются пустые поля ввода", Toast.LENGTH_SHORT).show()
            else if(event.date < todayDate){
                Toast.makeText(activity.applicationContext, "Событие нельзя создавать в прошлом", Toast.LENGTH_SHORT).show()
            }

            else{
                event.name = nameText.text.toString()
                event.info  = descriptionText.text.toString()
                var evMenager=NewEventManager(activity.applicationContext, event )
                evMenager.onSuccess={
                    message->
                    dismiss()
                }
                evMenager.onFailur={
                    Toast.makeText(activity.applicationContext, "Упс: запрос не удался", Toast.LENGTH_LONG).show()
                }
                evMenager.SendNewEventToServer()
            }
        }
        return view
    }

    private fun getBitmap(imageUri: Uri): Bitmap {
        var imageStream = activity.contentResolver.openInputStream(imageUri)
        var selectedImage = BitmapFactory.decodeStream(imageStream)
        return selectedImage
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var beginDate = view.findViewById(R.id.begin_date) as TextView
        var endDate = view.findViewById(R.id.end_date) as TextView
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_TIME_BEGIN -> {
                    var date = data!!.getSerializableExtra("date") as Date
                    var format = SimpleDateFormat("Начало dd.MM.YYYY в\n HH:mm")
                    beginDate.text = format.format(date)
                    event.date = toSqlDate(date)
                    event.timeBegin=toSqlTime(date)
                }
                REQUEST_TIME_END -> {
                    var date = data!!.getSerializableExtra("date") as Time
                    var format = SimpleDateFormat("Окончание в HH:mm")
                    endDate.text = format.format(date)
                    event.timeEnd = toSqlTime(date)
                }
                PICK_IMAGE -> {
                    var img = view.findViewById(R.id.img) as ImageView

                    try {
                        var imageUri = data!!.data
                        img.setImageBitmap(getBitmap(imageUri))
                        event.img=getBitmap(imageUri)
                    }catch (e: FileNotFoundException){
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}

