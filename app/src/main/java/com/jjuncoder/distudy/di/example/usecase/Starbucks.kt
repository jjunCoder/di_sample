package com.jjuncoder.distudy.di.example.usecase

import android.util.Log
import com.jjuncoder.distudy.di.example.Americano
import com.jjuncoder.distudy.di.example.DaggerStarbucksComponent
import javax.inject.Inject
import javax.inject.Provider

/**
 * Dagger 는 어떻게 Starbucks Class 의 멤버변수에 인스턴스를 주입할 수 있는걸까요?
 *
 * Starbucks Class 는 init 시점에서 미리 컴파일되어있는 DaggerStarbucksComponent 클래스의 builder 로 부터 인스턴스를 얻습니다.
 * 그 후 위에서 설명한 DaggerStarbucksComponent#inject 메소드에 Starbucks 클래스의 인스턴스를 넘깁니다.
 * Dagger 는 이 클래스에 맞는 Provide 메소드를 통해 멤버 인젝션을 수행합니다.
 */
class Starbucks {
    companion object {
        val LOG_TAG = Starbucks::class.java.simpleName
    }

    /**
     * Dagger 가 이 필드에 인스턴스를 주입해줍니다. (field injection, Provider injection)
     * Dagger 는 @Module 에서 이 필드의 자료형과 맞는 @Provides 를 찾습니다.
     */
    @Inject
    lateinit var partnerProvider: Provider<Partner>

    init {
        Log.d(LOG_TAG, "Starbucks Grand Opening")

        /**
         * Dagger 가 만들어준 [DaggerStarbucksComponent] 에 member injection
         * 이를 통해 위 @Inject 표시가 된 필드에 의존성이 주입됩니다.
         */
        val starbucksComponent = DaggerStarbucksComponent.create()
        starbucksComponent.inject(this)

        /**
         * 여기서부터 partnerProvider 를 사용 할 수 있습니다.
         * 그럼, Partner 의 의존성들은 어떻게 관리가 된 것일까요?
         * 여기서는 알 필요가 없습니다. [Partner] Class 에서 관리합니다.
         */
        val partner1 = partnerProvider.get()
        val partner2 = partnerProvider.get()

        Log.d(LOG_TAG, "partner1: ${partner1.partnerId}")
        Log.d(LOG_TAG, "partner2: ${partner2.partnerId}")

        for (i in 1..10) {
            val americano = partner1.makeDrink(Americano::class.java)
            Log.d(LOG_TAG, "Americano: $americano")
        }
    }
}

