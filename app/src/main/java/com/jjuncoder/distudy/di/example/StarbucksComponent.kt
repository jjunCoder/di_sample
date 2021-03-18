package com.jjuncoder.distudy.di.example

import com.jjuncoder.distudy.di.example.usecase.Partner
import com.jjuncoder.distudy.di.example.usecase.Starbucks
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Top-Down 분석
 * 스타벅스 클래스에 의존성을 주입해줄 컨테이너는 StarbucksComponent 라는 이름의 interface 로 만들어주고 @Component 어노테이션을 붙여줍니다.
 * Starbucks Component 는 member-injection method 역할을 하는 추상 메소드(fun inject)를 갖습니다.
 * Dagger 는 컴파일 시 Component 의 접두어로 Dagger 를 붙여 클래스를 생성하여 인터페이스를 구현합니다. ( DaggerStarbucksComponent ) 
 * Starbucks Component 는 Starbucks Class 에 @Inject 어노테이션이 붙은 곳에 의존성을 주입해 줍니다.
 * ( 이 예에서는 partnerProvider: Provider<Partner> 멤버를 주입해줍니다.)
 */
@Singleton
@Component(modules = [IngredientModule::class, StarbucksModule::class])
interface StarbucksComponent {
    fun inject(starbucks: Starbucks)
}

/**
 * SubComponent 의 정의는 상위 컴포넌트의 모듈에 선언합니다.
 */
@Module(subcomponents = [PartnerComponent::class])
class StarbucksModule {

    /**
     * 의존성 주입에 필요한 객체를 관리해줍니다.
     *
     * Partner 클래스에 PartnerComponent Factory 를 전달해 주어
     * 내부에서 PartnerComponent 를 create 한 뒤에 inject 할 수 있게 해줍니다.
     *
     * 이 factory 는 PartnerComponent 로 부터 주입받습니다.
     */
    @Provides
    fun providePartner(factory: PartnerComponent.Factory) = Partner(factory)
}

