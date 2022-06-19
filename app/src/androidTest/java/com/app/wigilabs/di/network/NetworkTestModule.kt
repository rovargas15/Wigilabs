package com.app.wigilabs.di.network

import com.example.wigilabs.di.network.NetworkModule
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object NetworkTestModule {

    @Provides
    @Singleton
    fun coroutineDispatcherProvider() = Dispatchers.IO

    @Provides
    @Singleton
    fun httpInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    /**
     * An own OkHttpClient for the main flow of the app
     * @param interceptor it is an interceptor to verify log a request
     */
    @Provides
    @Singleton
    fun okHttpProvider(
        interceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .dispatcher(Dispatcher().apply { maxRequests = MAX_REQUEST })
            .addInterceptor(interceptor)
            .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    /**
     * An own OkHttpClient for the main flow of the app
     * @param client
     */
    @Provides
    @Singleton
    fun retrofitProvider(
        client: OkHttpClient
    ): Retrofit {
        val moshi = Moshi.Builder()
            .build()
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .baseUrl(MOCK_WEB_SERVER_URL)
            .build()
    }
}

private const val TIME_OUT_SECONDS = 5L
private const val MAX_REQUEST = 1
private const val MOCK_WEB_SERVER_URL = "http://localhost:8080"
