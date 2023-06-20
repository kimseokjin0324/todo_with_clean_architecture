package fastcampus.aop.part4.todo_with_clean_architecture

import android.app.Application
import fastcampus.aop.part4.todo_with_clean_architecture.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AopPart5Chapter01Application : Application() {
    override fun onCreate() {
        super.onCreate()
        // TODO Koin Trigger
        startKoin {
            androidLogger(Level.ERROR)//-error발생시 로그
            androidContext(this@AopPart5Chapter01Application)
            modules(appModule)   //- module -> DI폴더안에 모듈들을 모아둘것
        }
    }

}