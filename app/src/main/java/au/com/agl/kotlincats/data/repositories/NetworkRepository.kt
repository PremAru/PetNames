package au.com.agl.kotlincats.data.repositories

import android.util.Log
import au.com.agl.kotlincats.data.model.Owner
import au.com.agl.kotlincats.data.model.exceptions.GenericException
import au.com.agl.kotlincats.data.model.exceptions.NetworkException
import au.com.agl.kotlincats.data.repositories.api.ApiInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor() {

    private val tag = "networkRepository"
    fun getCatOwners(): List<Owner> {
        val apiInterface = ApiInterface.create()
        val request = apiInterface.getOwners()
        val response = request.execute()

        if (!response.isSuccessful) {
            Log.w(tag, "a network error occured ${response.errorBody()}")
            throw NetworkException()
        }

        val responseBody = response.body()
        if (responseBody == null) {
            Log.w(
                tag,
                "an unexpected error occurred, throwing a GenericException ${response.errorBody()}"
            )
            throw GenericException()
        }

        Log.d(tag, "api call response body: $responseBody")

        return responseBody
    }
}