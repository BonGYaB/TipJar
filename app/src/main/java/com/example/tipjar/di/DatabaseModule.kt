package com.example.tipjar.di

import android.content.Context
import com.example.tipjar.database.TipDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun providesTipDatabase(
        @ApplicationContext context: Context
    ): TipDatabase {
        return TipDatabase.getDatabase(context)
    }
}