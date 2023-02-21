package lewis.libby.hw2

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import lewis.libby.hw2.repository.ContactDatabaseRepository
import lewis.libby.hw2.repository.ContactRepository
import kotlinx.coroutines.launch

sealed interface Screen
object ContactList: Screen
object AddressList: Screen
data class ContactScreen(
    val id: String
): Screen

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ContactRepository = ContactDatabaseRepository(application)

    //Might need to change to AddressList
    var screen by mutableStateOf<Screen>(ContactList)
        private set

    val contactsFlow = repository.contactsFlow
    val addressesFlow = repository.addressesFlow

    suspend fun getContactWithAddresses(id: String) =
        repository.getContactWithAddresses(id)

    suspend fun getContact(id: String) =
        repository.getContact(id)

    suspend fun getAddress(id: String) =
        repository.getAddress(id)

    fun switchTo(screen: Screen) {
        this.screen = screen
    }

    fun resetDatabase() {
        viewModelScope.launch {
            repository.resetDatabase()
        }
    }
}