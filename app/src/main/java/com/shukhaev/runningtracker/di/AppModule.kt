package com.shukhaev.runningtracker.di

import android.content.Context
import androidx.room.Room
import com.shukhaev.runningtracker.utils.Util.RUNNING_DATABASE_NAME
import com.shukhaev.runningtracker.db.RunningDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRunningDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, RunningDatabase::class.java, RUNNING_DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideRunDao(db: RunningDatabase) = db.getRunDao()
}