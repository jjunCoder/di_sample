package com.jjuncoder.distudy.di.example

import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * 재료 재사용의 나쁜짓을 하지 않기 위해 Scope 는 지정하지 않습니다.
 * 모듈은 @Singleton 등의 스코프를 지정해주지 않으면 요청시 매번 다른 객체를 제공해줍니다.
 */
@Module
class IngredientModule {
    @Provides
    fun provideCoffeeBean(): CoffeeBean {
        return CoffeeBean()
    }

    @Provides
    fun provideWater(): Water {
        return Water()
    }

    @Provides
    fun provideMilk(): Milk {
        return Milk()
    }
}

interface Drink

class Espresso @Inject constructor(
    private val water: Water,
    private val coffeeBean: CoffeeBean
) : Drink

class Americano @Inject constructor(
    private val water: Water,
    private val espresso: Espresso
) : Drink

class Latte @Inject constructor(
    private val milk: Milk,
    private val espresso: Espresso
) : Drink

class Milk
class Water
class CoffeeBean





