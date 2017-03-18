package com.example.sam.myapplication.model

class CurrentUser() {
    companion object {
        var login = ""
        var _id = 0
        var name=""
        public fun canIAddEvewnts():Boolean{
            if(permission==1)
                return true
            else
                return false
        }
        var permission = 0
    }
}