package com.example.sam.myapplication

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.sam.myapplication.R
import com.example.sam.myapplication.model.ServerAPI
import com.example.sam.myapplication.LoginActivity
import com.example.sam.myapplication.model.RegistrationManager

class RegistrationActivity: AppCompatActivity() {

    private var login_text: AutoCompleteTextView? = null
    private var pass_text: EditText? = null
    private var fullName_text: AutoCompleteTextView? = null
    private var mess_text: TextView? = null
    private var back_btn: Button? = null
    private var create_ak_btn: Button? = null
    private var progress_bar: View? = null
    private var login_form: View? = null

    companion object{
        fun isFullNameValid(name:String):Boolean{
            return Regex("""^[a-zA-Zа-яёА-ЯЁ\s\-]+$""").matches(name)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        login_text = findViewById(R.id.login_txt) as AutoCompleteTextView
        pass_text = findViewById(R.id.password_txt) as EditText
        fullName_text=findViewById(R.id.fullName_txt) as AutoCompleteTextView

        mess_text = findViewById(R.id.mess_txt) as TextView
        progress_bar = findViewById(R.id.regist_progress)
        login_form = findViewById(R.id.login_form)

        back_btn = findViewById(R.id.back_login_btn) as Button
        create_ak_btn = findViewById(R.id.create_ak_btn) as Button

        login_text!!.setText("")
        pass_text!!.setText("")
        fullName_text!!.setText("")

        back_btn!!.setOnClickListener {
            showLoginActivity()
        }

        create_ak_btn!!.setOnClickListener {
            attemptRegIn()
        }
    }

    private fun attemptRegIn() {//попытка коннекта
        mess_text!!.text=""

        login_text!!.error = null
        pass_text!!.error = null
        fullName_text!!.error = null
        val login = login_text!!.text.toString()
        val password = pass_text!!.text.toString()
        val fullName = fullName_text!!.text.toString()

        var cancel = false
        var focusView: View? = null

        if (TextUtils.isEmpty(password) ) {
            pass_text!!.error = "Пароль пуст"
            focusView = pass_text
            cancel = true
        }else{
            if(!LoginActivity.isPasswordValid(password)){
                pass_text!!.error = "Пароль меньше 5 символов"
                focusView = pass_text
                cancel = true
            }
        }

        if (TextUtils.isEmpty(login)) {
            login_text!!.error = "Логин пустой"
            focusView = login_text
            cancel = true
        }else{
            if(!LoginActivity.isEmailValid(login)){
                login_text!!.error = "Почта не корректна"
                focusView = login_text
                cancel = true
            }
        }

        if (TextUtils.isEmpty(fullName)) {
            fullName_text!!.error = "Имя отсутствует"
            focusView = fullName_text
            cancel = true
        }else{
            if(!isFullNameValid(fullName)){
                fullName_text!!.error = "Запрещены цифры и спец.символы"
                focusView = fullName_text
                cancel = true
            }
        }

        if (cancel) {
            focusView!!.requestFocus()
        } else {

            showProgress(true)
            /*var doReg=RegistrationManager(applicationContext,login,password,fullName)
            doReg.onSuccess={
                out->
                if(out=="username or email already exist<br />")
                    mess_text!!.text = "Учетная запись на эту почту уже существует!"
                else{
                    mess_text!!.text = "Учетная запись успешно создана!"
                    create_ak_btn!!.visibility= View.GONE
                    back_btn!!.text = "Вернуться к авторизации"
                }
                showProgress(false)
            }
            doReg.onFailur={s->
                showProgress(false)
                mess_text!!.text="Ошибка подключения к базе :"+ s
            }
            doReg.RegToServer()*/
            //doReg.execute()
        }
    }

    private fun showLoginActivity(){
        var intent= Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    //показать/скрыть прогрессбар
    private fun showProgress(show: Boolean) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)

            login_form!!.visibility = if (show) View.GONE else View.VISIBLE
            progress_bar!!.visibility = if (show) View.VISIBLE else View.GONE
            progress_bar!!.animate().setDuration(shortAnimTime.toLong()).alpha(
                    (if (show) 1 else 0).toFloat()).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    progress_bar!!.visibility = if (show) View.VISIBLE else View.GONE
                }
            })
        } else {
            login_form!!.visibility = if (show) View.GONE else View.VISIBLE
            progress_bar!!.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    override fun onBackPressed() {
        showLoginActivity()
    }
}