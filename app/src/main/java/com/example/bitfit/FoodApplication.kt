package com.example.bitfit

import android.app.Application

class FoodApplication: Application() {
    val db by lazy {AppDataBase.getInstance(this)}
}