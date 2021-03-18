package com.jjuncoder.distudy.di.example

import com.jjuncoder.distudy.di.example.usecase.Partner
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import java.util.*
import javax.inject.Named
import javax.inject.Scope

/**
 * StarbucksComponent 의 Sub Component 이다. 그렇기 때문에 Starbucks Component 의 하위 모듈인
 * Ingredient Module 이 Provide 하는 의존성들을 PartnerComponent 에서도 주입받을 수 있다.
 * 그러므로 PartnerComponent 의 하위 모듈인 PartnerModule 에서 IngredientModule 이 제공하는 의존성을
 * 주입받을 수 있고, PartnerModule 내부에서 [CoffeeBean] [Water] [Milk] 를 인자로 받아 쓸 수 있는것이다.
 */
@PartnerScope
@Subcomponent(modules = [PartnerModule::class])
interface PartnerComponent {

    /**
     * SubComponent 는 Dagger 가 컴파일 시 DaggerSubComponent 클래스를 만들어 주지 않는다.
     * SubComponent 는 반드시 부모컴포넌트가 이 컴포넌트를 어떻게 만들면 되는지 알도록 해야하므로
     * Builder 나 Factory 를 구현해야 한다. -> 둘 중 하나만 구현해야 한다.
     */
    @Subcomponent.Factory
    interface Factory {
        fun create(): PartnerComponent
    }

    fun inject(partner: Partner)
}

@Module
class PartnerModule {

    @Provides
    @PartnerScope
    @Named("partnerId")
    fun providePartnerId() = UUID.randomUUID().toString()

    @Provides
    @IntoMap
    @ClassKey(Americano::class)
    fun provideAmericano(espresso: Espresso, water: Water): Drink {
        return Americano(water, espresso)
    }

    @Provides
    @IntoMap
    @ClassKey(Espresso::class)
    fun provideEspresso(coffeeBean: CoffeeBean, water: Water): Drink {
        return Espresso(water, coffeeBean)
    }

    @Provides
    @IntoMap
    @ClassKey(Latte::class)
    fun provideLatte(espresso: Espresso, milk: Milk): Drink {
        return Latte(milk, espresso)
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PartnerScope