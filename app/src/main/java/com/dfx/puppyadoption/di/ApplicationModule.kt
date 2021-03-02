package com.dfx.puppyadoption.di

import android.content.Context
import com.dfx.puppyadoption.MyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    @Singleton
    fun provideApp(@ApplicationContext context: Context): MyApplication {
        return context as MyApplication
    }
}