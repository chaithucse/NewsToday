package com.techchai.newstoday.data.io

import android.content.Context
import com.techchai.newstoday.BuildConfig
import com.techchai.newstoday.common.AppConstants
import com.techchai.newstoday.common.AppUtils
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Retrofit API client
 * @author Chaitanya
 */
class ApiClient {

    companion object {
        private var instance: Retrofit? = null

        fun getRetrofitInstance(context: Context): Retrofit {
            val httpCacheDirectory = File(context.cacheDir, "http-cache")
            val cache = Cache(httpCacheDirectory, AppConstants.CACHE_SIZE.toLong())

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            // Interceptor to cache data and prevent it loading again and again in a minute
            val networkCacheInterceptor = Interceptor { chain ->
                val response = chain.proceed(chain.request())

                val cacheControl = CacheControl.Builder()
                    .maxAge(1, TimeUnit.MINUTES)
                    .build()

                response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", cacheControl.toString())
                    .build()
            }

            val offlineCacheInterceptor = Interceptor { chain ->
                var request: Request = chain.request()

                if (!AppUtils.isNetworkAvailable(context)) {
                    request = request.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + 2419200)
                        .build()
                }
                chain.proceed(request)

            }

            //Creating Auth Interceptor to add api_key query in front of all the requests.
            val authInterceptor = Interceptor {chain->
                val newUrl = chain.request().url()
                    .newBuilder()
                    .addQueryParameter("apiKey", "7b7b069ede844ae692b76ca98f2b78bc")
                    .build()

                val newRequest = chain.request()
                    .newBuilder()
                    .url(newUrl)
                    .build()

                chain.proceed(newRequest)
            }

            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(networkCacheInterceptor)
                .addNetworkInterceptor(offlineCacheInterceptor)
                .addInterceptor(authInterceptor)
                .cache(cache)
                .addInterceptor(interceptor).build()

            instance = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return instance!!
        }
    }
}