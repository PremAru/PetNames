package au.com.agl.kotlincats.data.repositories.api

import au.com.agl.kotlincats.data.model.Owner
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    @GET("people.json")
    fun getOwners(): Call<List<Owner>>

    companion object {
        private const val BASE_URL = "https://agl-developer-test.azurewebsites.net/"

        fun create(): ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}