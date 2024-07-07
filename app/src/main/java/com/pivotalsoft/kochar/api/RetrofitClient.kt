package com.pivotalsoft.kochar.api

import com.pivotalsoft.kochar.response.SchoolDataResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://mobilevakil.com/vasu/kochar/index.php/api/Doctor/"


    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()


            val requestBuilder = original.newBuilder()

                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }
        .retryOnConnectionFailure(false)
        .build()

    val instance: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(Api::class.java)
    }
//    class Egg{
//
//        suspend fun getdat(): Call<SchoolDataResponse> {
//            return instance.getSchoolDetails()
//        }
//    }

}
