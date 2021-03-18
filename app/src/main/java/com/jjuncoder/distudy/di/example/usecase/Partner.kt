package com.jjuncoder.distudy.di.example.usecase

import com.jjuncoder.distudy.di.example.Drink
import com.jjuncoder.distudy.di.example.PartnerComponent
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

/**
 * Partner
 */
class Partner @Inject constructor(
    factory: PartnerComponent.Factory
) {
    @Inject
    @Named("partnerId")
    lateinit var partnerId: String

    /**
     * PartnerComponent 가 의존성을 주입해줍니다.
     * 클래스를 키로 매번 새로운 음료를 PartnerModule 로부터 제공받습니다.
     */
    @Inject
    @JvmSuppressWildcards
    lateinit var menuMap: Map<Class<*>, Provider<Drink>>

    init {
        /**
         * [PartnerComponent] 가 [Partner] 의 의존성을 관리해줍니다.
         */
        factory.create().inject(this)
    }

    /**
     * 클래스 타입에 따른 Value 는 Provider<Drink> 자료형으로, get() 을 호출해 새로운 인스턴스를 제공받습니다.
     */
    @JvmSuppressWildcards
    fun makeDrink(type: Class<*>) = menuMap[type]?.get()
}
