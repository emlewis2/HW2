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

sealed interface Screen             //Sealed interface
object ContactList: Screen          //Screen for all contacts
object AddressList: Screen          //Screen for all addresses
data class ContactScreen(           //Screen for individual contact
    val id: String
): Screen
data class AddressScreen(           //Screen for individual address
    val id: String
): Screen

//View model for Contact
class ContactViewModel(application: Application) : AndroidViewModel(application) {
    //Private instance of repository
    private val repository: ContactRepository = ContactDatabaseRepository(application)

    var screen by mutableStateOf<Screen>(ContactList)
        private set

    //Flow properities for all contacts and all addresses
    val contactsFlow = repository.contactsFlow
    val addressesFlow = repository.addressesFlow

    //Get Contact with Addresses
    suspend fun getContactWithAddresses(id: String) =
        repository.getContactWithAddresses(id)

    //Get Contact
    suspend fun getContact(id: String) =
        repository.getContact(id)

    //Get Address
    suspend fun getAddress(id: String) =
        repository.getAddress(id)

    //Switching screens
    fun switchTo(screen: Screen) {
        this.screen = screen
    }

    //Reset database
    fun resetDatabase() {
        viewModelScope.launch {
            repository.resetDatabase()
        }
    }
}