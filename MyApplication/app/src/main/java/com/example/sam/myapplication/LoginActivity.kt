package com.example.sam.myapplication

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.example.sam.myapplication.R
import com.example.sam.myapplication.model.*


class LoginActivity : AppCompatActivity() {
    companion object{
        val KEY_SHARED="shar"
        val KEY_LOGIN="login"
        val KEY_PASS="pass"

        public fun isEmailValid(email: String): Boolean {
            return Regex("""^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$""").matches(email)
        }

        public fun isPasswordValid(password: String): Boolean {
            return password.length > 4
        }
    }

    private var login_text: AutoCompleteTextView? = null
    private var pass_text: EditText? = null
    private var mess_text: TextView? = null
    private var sign_in_btn: Button? = null
    private var reg_in_btn: Button? = null
    private var progress_bar: View? = null
    private var login_form: View? = null
    private var progress_txt:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_text = findViewById(R.id.login_txt) as AutoCompleteTextView
        pass_text = findViewById(R.id.password_txt) as EditText

        mess_text = findViewById(R.id.mess_txt) as TextView
        progress_txt = findViewById(R.id.progress_txt) as TextView
        progress_bar = findViewById(R.id.login_progress)
        login_form = findViewById(R.id.login_form)

        sign_in_btn = findViewById(R.id.sign_in_btn) as Button
        reg_in_btn = findViewById(R.id.reg_in_btn) as Button

        login_text!!.setText(getLog())
        pass_text!!.setText(getPass())

        sign_in_btn!!.setOnClickListener {
            attemptLogin()
        }
        reg_in_btn!!.setOnClickListener {
            showRegistrationActivity()
        }

        LDbHelper.initLDbHelper(applicationContext)
    }

    private fun attemptLogin() {
        //showMainActivity()
        mess_text!!.text=""

        login_text!!.error = null
        pass_text!!.error = null

        val login = login_text!!.text.toString()
        val password = pass_text!!.text.toString()

        var cancel = false
        var focusView: View? = null

        if (TextUtils.isEmpty(password) ) {
            pass_text!!.error = "Пароль пуст"
            focusView = pass_text
            cancel = true
        }else{
            if(!isPasswordValid(password)){
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
            if(!isEmailValid(login)){
                login_text!!.error = "Почта не корректна"
                focusView = login_text
                cancel = true
            }
        }

        if (cancel) {
            // если данные некорректы показать focusView
            focusView!!.requestFocus()
        } else {
            //если всё гуд, запускаем поток и показываем прогрессбар
            progress_txt!!.text="Авторизация"
            showProgress(true)

            var doLogin=LoginManager(applicationContext,login,password)//UserLoginTask(login, password)
            doLogin.onSuccess={success->
                if(success) {
                    saveLastUser(login, password)
                    progress_txt!!.text="Загрузка данных."
                    LDbHelper.clearLocalDataBase()
                    var cacheDB=DataManager(applicationContext)
                    cacheDB.onSuccess={
                        showMainActivity()
                    }
                    cacheDB.onFailur={
                        showProgress(false)
                        mess_text!!.text = "Ошибка при загрузки данных."
                    }
                    cacheDB.cacheFreshDB()
                }
                else{
                    showProgress(false)
                    mess_text!!.text = "Неверный логин или пароль."
                }
            }
            doLogin.onFailur={mess->
                showProgress(false)
                mess_text!!.text= "Ошибка сервера: "+mess
            }
            doLogin.LogIn()
            /*
            if(login=="test@test.ru") {
                saveLastUser(login, password)
                progress_txt!!.text = "Загрузка данных."
                //LDbHelper.deleteLocalDataBase(applicationContext)
                var cacheDB = DataManager(applicationContext)
                cacheDB.cacheFreshDB()
                cacheDB.onSuccess = {
                    showMainActivity()
                }
                cacheDB.onFailur = {
                    showProgress(false)
                    mess_text!!.text = "Ошибка при загрузки данных."
                }
            }
            else{
                mess_text!!.text = "Неверный логин или пароль."
            }*/
        }
    }


    private fun showRegistrationActivity(){
        var intent= Intent(applicationContext,RegistrationActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showMainActivity(){
        var intent= Intent(applicationContext,MainActivity::class.java)
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

    private fun getLog():String{
        val sh = getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE)
        return sh.getString(KEY_LOGIN, "")
    }
    private fun getPass():String{
        val sh = getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE)
        return sh.getString(KEY_PASS, "")
    }
    private fun saveLastUser(l:String,p:String){
        var sh = getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE)
        var editor=sh.edit()
        editor.putString(KEY_LOGIN,l)
        editor.putString(KEY_PASS,p)
        editor.apply()
    }

    override fun onBackPressed() {
        System.exit(0)
    }

}