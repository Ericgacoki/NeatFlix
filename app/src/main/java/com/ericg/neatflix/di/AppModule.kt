package com.ericg.neatflix.di

import android.app.Application
import androidx.room.Room
import com.ericg.neatflix.data.local.WatchListDatabase
import com.ericg.neatflix.data.repository.GenreRepository
import com.ericg.neatflix.data.repository.MoviesRepository
import com.ericg.neatflix.data.repository.SearchRepository
import com.ericg.neatflix.data.repository.WatchListRepository
import com.ericg.neatflix.model.APIService
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
    fun providesAPI(okHttpClient: OkHttpClient): APIService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(APIService::class.java)
    }

    @Singleton
    @Provides
    fun provideMoviesRepository(api: APIService) = MoviesRepository(api = api)

    @Singleton
    @Provides
    fun provideSearchRepository(api: APIService) = SearchRepository(api = api)

    @Singleton
    @Provides
    fun providesGenresRepository(api: APIService) = GenreRepository(api)

    @Singleton
    @Provides
    fun providesWatchListRepository(watchListDatabase: WatchListDatabase) =
        WatchListRepository(database = watchListDatabase)

    @Provides
    @Singleton
    fun providesWatchListDatabase(application: Application): WatchListDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            WatchListDatabase::class.java,
            "watch_list_table"
        ).fallbackToDestructiveMigration().build()
    }
}
