package com.jjuncoder.distudy.di.example.general

import android.util.Log
import com.jjuncoder.distudy.di.example.*
import java.util.*
import kotlin.reflect.KClass

class StarbucksNoDi {
    companion object {
        val LOG_TAG = StarbucksNoDi::class.java.simpleName
    }

    private val partner1 = PartnerNoDi()
    private val partner2 = PartnerNoDi()
    private val partner3 = PartnerWithMenu(
        mapOf<KClass<*>, Drink>(
            Latte::class to Latte(Milk(), Espresso(Water(), CoffeeBean()))
        )
    )

    init {
        Log.d(LOG_TAG, "partner1: ${partner1.partnerId}")
        Log.d(LOG_TAG, "partner2: ${partner2.partnerId}")

        for (i in 1..10) {
            val americano = partner1.makeDrink(Americano::class)
            Log.d(LOG_TAG, "Americano: $americano")

            val latte = partner3.makeDrink(Latte::class)
            Log.d(LOG_TAG, "Latte: $latte")
        }
    }
}

class PartnerNoDi {
    val partnerId = UUID.randomUUID().toString()
    private val menuMap = mapOf<KClass<*>, Drink>(
        Espresso::class to Espresso(Water(), CoffeeBean()),
        Americano::class to Americano(Water(), Espresso(Water(), CoffeeBean())),
        Latte::class to Latte(Milk(), Espresso(Water(), CoffeeBean())),
    )

    fun makeDrink(type: KClass<*>) = menuMap[type]
}

class PartnerWithMenu(private val menuMap: Map<KClass<*>, Drink>) {
    val partnerId = UUID.randomUUID().toString()

    fun makeDrink(type: KClass<*>) = menuMap[type]
}