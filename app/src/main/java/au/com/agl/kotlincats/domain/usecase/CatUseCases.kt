package au.com.agl.kotlincats.domain.usecase

import android.util.Log
import au.com.agl.kotlincats.data.model.Pet
import au.com.agl.kotlincats.data.model.exceptions.GenericException
import au.com.agl.kotlincats.data.model.exceptions.NetworkException
import au.com.agl.kotlincats.data.repositories.NetworkRepository
import au.com.agl.kotlincats.presentation.adapter.DataItem
import au.com.agl.kotlincats.presentation.adapter.HeaderDataItem
import au.com.agl.kotlincats.presentation.adapter.PetDataItem
import javax.inject.Inject

class CatUseCases @Inject constructor(
    private val networkRepository: NetworkRepository
) {

    private val tag = "useCase"
    fun getAllOwners(): List<DataItem> {

        try {
            val people = networkRepository.getCatOwners()
            val petNamesByGender = mutableMapOf<String, MutableList<String>>()
            people.forEach { person ->
                val gender = person.gender
                val petNames = person.pets?.map { it.name } ?: emptyList()
                petNamesByGender.computeIfAbsent(gender) { mutableListOf() }.addAll(petNames)
            }

            // Print the map
            petNamesByGender.forEach { (gender, petNames) ->
                println("$gender pets: $petNames")
            }
            val dataItems = mutableListOf<DataItem>()
            petNamesByGender.forEach { (gender, petNames) ->
                dataItems.add(HeaderDataItem(gender))
                petNames.forEach { petName ->
                    dataItems.add(PetDataItem(petName))
                }
            }
            return dataItems.toList()
        } catch (networkException: NetworkException) {
            Log.d(tag, "a network exception occurred, rethrowing it")
            throw networkException
        } catch (exception: Exception) {
            Log.w(tag, "an exception occurred: ${exception.message}", exception)
            throw GenericException()
        }
    }
}