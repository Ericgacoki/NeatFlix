package com.ericg.neatflix.di

import android.app.Application
import androidx.room.Room
import com.ericg.neatflix.data.local.WatchListDatabase
import com.ericg.neatflix.data.preferences.UserPreferences
import com.ericg.neatflix.data.remote.ApiService
import com.ericg.neatflix.data.repository.*
import com.ericg.neatflix.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun providesAPIService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideMoviesRepository(api: ApiService) = FilmRepository(api = api)

    @Singleton
    @Provides
    fun provideSearchRepository(api: ApiService) = SearchRepository(api = api)

    @Singleton
    @Provides
    fun providesGenresRepository(api: ApiService) = GenreRepository(api)

    @Singleton
    @Provides
    fun providesWatchListRepository(watchListDatabase: WatchListDatabase) =
        WatchListRepository(database = watchListDatabase)

    @Singleton
    @Provides
    fun providesReviewsRepository(api: ApiService): ReviewsRepository = ReviewsRepository(api)

    @Provides
    @Singleton
    fun providesWatchListDatabase(application: Application): WatchListDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            WatchListDatabase::class.java,
            "watch_list_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesDataStore(application: Application): UserPreferences {
        return UserPreferences(application.applicationContext)
    }
}
