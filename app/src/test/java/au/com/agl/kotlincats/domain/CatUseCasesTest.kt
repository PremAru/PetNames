import au.com.agl.kotlincats.data.model.Owner
import au.com.agl.kotlincats.data.model.Pet
import au.com.agl.kotlincats.data.model.exceptions.GenericException
import au.com.agl.kotlincats.data.model.exceptions.NetworkException
import au.com.agl.kotlincats.data.repositories.NetworkRepository
import au.com.agl.kotlincats.domain.usecase.CatUseCases
import au.com.agl.kotlincats.presentation.adapter.DataItem
import au.com.agl.kotlincats.presentation.adapter.HeaderDataItem
import au.com.agl.kotlincats.presentation.adapter.PetDataItem
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CatUseCasesTest {

    @Mock
    private lateinit var networkRepository: NetworkRepository

    private lateinit var catUseCases: CatUseCases

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        catUseCases = CatUseCases(networkRepository)
    }

    @Test
    fun `test getAllOwners success`() {
        // Mock data
        val mockPeople = listOf(
            Owner(
                name = "Bob",
                age = 23,
                gender = "Male",
                pets = listOf(Pet("Garfield", type = "Cat"), Pet("Fido", type = "Dog"))
            ),
            Owner(
                name = "Jennifer",
                age = 18,
                gender = "Female",
                pets = listOf(Pet("Garfield", type = "Cat"))
            )
        )

        // Mock behavior of the network repository
        `when`(networkRepository.getCatOwners()).thenReturn(mockPeople)

        val expectedDataItems = listOf<DataItem>(
            HeaderDataItem("Male"),
            PetDataItem("Garfield"),
            PetDataItem("Fido"),
            HeaderDataItem("Female"),
            PetDataItem("Garfield")
        )

        val resultDataItems = catUseCases.getAllOwners()
        assertEquals(expectedDataItems, resultDataItems)
    }

    @Throws(NetworkException::class)
    fun `test getAllOwners network error`() {
        `when`(networkRepository.getCatOwners()).thenThrow(NetworkException())

        catUseCases.getAllOwners()
    }

    @Throws(GenericException::class)
    fun `test getAllOwners generic error`() {
        `when`(networkRepository.getCatOwners()).thenThrow(Exception())

        catUseCases.getAllOwners()
    }
}