package au.com.agl.kotlincats.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import au.com.agl.kotlincats.data.model.exceptions.GenericException
import au.com.agl.kotlincats.data.model.exceptions.NetworkException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import au.com.agl.kotlincats.domain.usecase.CatUseCases
import au.com.agl.kotlincats.presentation.adapter.DataItem
import au.com.agl.kotlincats.presentation.adapter.HeaderDataItem
import au.com.agl.kotlincats.presentation.adapter.PetDataItem
import org.junit.Rule
import java.lang.Exception

@ExperimentalCoroutinesApi
class CatViewModelTest {

    private lateinit var viewModel: CatViewModel
    private lateinit var useCase: CatUseCases
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        useCase = mock(CatUseCases::class.java)
        viewModel = CatViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `loadCatOwners success`() {
        val expectedDataItems = listOf<DataItem>(
            HeaderDataItem("Male"),
            PetDataItem("Garfield"),
            PetDataItem("Fido"),
            HeaderDataItem("Female"),
            PetDataItem("Garfield")
        )
        `when`(useCase.getAllOwners()).thenReturn(expectedDataItems)

        // Act
        viewModel.loadCatOwners()
        // Assert
        verify(useCase).getAllOwners()
        assert(viewModel.owners.value == State.success(expectedDataItems))
    }

    @Throws(NetworkException::class)
    fun `loadCatOwners network error`() {
        `when`(useCase.getAllOwners()).thenThrow(NetworkException())

        viewModel.loadCatOwners()

        verify(useCase).getAllOwners()
    }

  @Throws(GenericException::class)
    fun `loadCatOwners generic error`() {
        val errorMessage = "Generic error"
        `when`(useCase.getAllOwners()).thenThrow(Exception(errorMessage))

        viewModel.loadCatOwners()

        verify(useCase).getAllOwners()
    }
}
