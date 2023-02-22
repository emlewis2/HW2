package lewis.libby.hw2.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import lewis.libby.hw2.ContactList
import lewis.libby.hw2.AddressList
import lewis.libby.hw2.AddressScreen
import lewis.libby.hw2.ContactScreen
import lewis.libby.hw2.ContactViewModel
import lewis.libby.hw2.components.SimpleButton

//Setting up the test screen
@Composable
fun TestScreen(
    viewModel: ContactViewModel = viewModel()
) {
    //Buttons for Reset, Contacts, and Addresses
    Column {
        Row {
            SimpleButton("Reset") {
                viewModel.resetDatabase()
            }
            SimpleButton("Contacts") {
                viewModel.switchTo(ContactList)
            }
        }
        Row {
            SimpleButton("Addresses") {
                viewModel.switchTo(AddressList)
            }
        }

        //Collect flows
        val contacts by viewModel.contactsFlow.collectAsState(initial = emptyList())
        val addresses by viewModel.addressesFlow.collectAsState(initial = emptyList())

        //Switching screens
        when(val screen = viewModel.screen) {
            //Screen for all contacts
            ContactList -> ContactList(contacts = contacts) { id ->
                viewModel.switchTo(ContactScreen(id))
            }
            //Screen for all addresses
            AddressList -> AddressList(addresses = addresses) { id ->
                viewModel.switchTo(AddressScreen(id))
            }
            //Screen for individual contact
            is ContactScreen -> ContactDisplay(
                contactId = screen.id,
                fetchContactWithAddresses = { id ->
                    viewModel.getContactWithAddresses(id)
                },
                onContactClick = { id -> viewModel.switchTo(AddressScreen(id))}
            )
            //Screen for individual address
            is AddressScreen -> AddressDisplay(
                id = screen.id,
                fetchAddress = { id ->
                    viewModel.getAddress(id)
                }
            )
        }
    }
}