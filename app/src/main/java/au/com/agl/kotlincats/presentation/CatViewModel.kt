package au.com.agl.kotlincats.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.agl.kotlincats.data.model.Owner
import au.com.agl.kotlincats.data.model.Pet
import au.com.agl.kotlincats.domain.usecase.CatUseCases
import au.com.agl.kotlincats.presentation.adapter.DataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject internal constructor(private val useCase: CatUseCases) :
    ViewModel() {

    val owners: LiveData<State<List<DataItem>>> get() = _owners
    private val _owners = MutableLiveData<State<List<DataItem>>>()

    fun loadCatOwners() {
        _owners.value = State.loading()
        viewModelScope.launch {
            val state = withContext(Dispatchers.Default) {
                try {
                    val ownerValue = useCase.getAllOwners()
                    State.success(ownerValue)
                } catch (exception: Exception) {
                    State.error(exception.message)
                }
            }
            _owners.value = state
        }
    }
}