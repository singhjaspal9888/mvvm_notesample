package com.cheezycode.notesample.di

import android.content.Context
import androidx.room.Room
import com.cheezycode.notesample.api.AuthInterceptor
import com.cheezycode.notesample.api.FakeUsers
import com.cheezycode.notesample.api.NoteAPI
import com.cheezycode.notesample.api.UserAPI
import com.cheezycode.notesample.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }


    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder): UserAPI {
        return retrofitBuilder.build().create(UserAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesNoteAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): NoteAPI {
        return retrofitBuilder.client(okHttpClient).build().create(NoteAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesFakeApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): FakeUsers {
        return retrofitBuilder.client(okHttpClient).build().create(FakeUsers::class.java)
    }


}