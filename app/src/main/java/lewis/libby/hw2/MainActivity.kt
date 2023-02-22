package lewis.libby.hw2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import lewis.libby.hw2.repository.AddressDto
import lewis.libby.hw2.repository.ContactDto
import lewis.libby.hw2.repository.ContactWithAddressesDto
import lewis.libby.hw2.ui.theme.HW2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HW2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Greeting("Android")
                    TestScreen()
                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}

@Composable
fun SimpleButton(
    text: String,
    onClick: () -> Unit,
) =
    Button(onClick = onClick, modifier = Modifier.padding(8.dp)) {
        Text(text = text, modifier = Modifier.padding(8.dp))
    }

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    HW2Theme {
//        Greeting("Android")
//    }
//}

@Composable
fun SimpleText(
    text: String,
    onClick: () -> Unit = {},
) =
    Text(
        text = text,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick()
            }
    )

@Composable
fun TestScreen(
    viewModel: ContactViewModel = viewModel()
) {
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

        val contacts by viewModel.contactsFlow.collectAsState(initial = emptyList())
        val addresses by viewModel.addressesFlow.collectAsState(initial = emptyList())

        when(val screen = viewModel.screen) {
            ContactList -> ContactList(contacts = contacts) {id ->
                viewModel.switchTo(ContactScreen(id))
            }
            AddressList -> AddressList(addresses = addresses) {id ->
                viewModel.switchTo(AddressScreen(id))
            }
            is ContactScreen -> ContactDisplay(
                contactId = screen.id,
                fetchContactWithAddresses = { id ->
                    viewModel.getContactWithAddresses(id)
                },
                onContactClick = { id -> viewModel.switchTo(AddressScreen(id))}
            )
            is AddressScreen -> AddressDisplay(
                id = screen.id,
                fetchAddress = { id ->
                    viewModel.getAddress(id)
                }
            )
        }
    }
}

@Composable
fun ContactList(
    contacts: List<ContactDto>,
    onContactClick: (String) -> Unit,
) {
    SimpleText(text = "Contacts")
    contacts.forEach {
        SimpleText(text = "${it.firstName} ${it.lastName}") {
            onContactClick(it.id)
        }
    }
}

@Composable
fun AddressList(
    addresses: List<AddressDto>,
    onContactClick: (String) -> Unit,
) {
    SimpleText(text = "Addresses")
    addresses.forEach {
        SimpleText(text = "${it.type}: ${it.street}") {
            onContactClick(it.id)
        }
    }
}

//@Composable
//fun ContactDisplay(
//    contact: ContactDto
//) {
//    SimpleText(text = "Contact")
//    Row {
//        SimpleText(text = "First Name")
//        SimpleText(text = contact.firstName)
//    }
//    Row {
//        SimpleText(text = "Email")
//        SimpleText(text = contact.email)
//    }
//}

@Composable
fun ContactDisplay(
    contactId: String,
    fetchContactWithAddresses: suspend (String) -> ContactWithAddressesDto,
    onContactClick: (String) -> Unit,
) {
    var contactWithAddressesDto by remember { mutableStateOf<ContactWithAddressesDto?>(null) }

    LaunchedEffect(key1 = contactId) {
        contactWithAddressesDto = fetchContactWithAddresses(contactId)
    }

    SimpleText(text = "Contact")
    contactWithAddressesDto?.let { contactWithAddresses ->
        Row {
            SimpleText(text = "Name: ${contactWithAddresses.contact.firstName} " +
                    contactWithAddresses.contact.lastName
            )
        }
        Row {
            SimpleText("Home Phone: ${contactWithAddresses.contact.homePhone}")
        }
        Row {
            SimpleText(text = "Work Phone: ${contactWithAddresses.contact.workPhone}")
        }
        Row {
            SimpleText(text = "Mobile Phone: ${contactWithAddresses.contact.mobilePhone}")
        }
        Row {
            SimpleText(text = "Email: ${contactWithAddresses.contact.email}")
        }
        contactWithAddresses.addresses.forEach { address ->
            SimpleText(text = "${address.type}: ${address.street}") {
                onContactClick(address.id)
            }
        }
    }
}

@Composable
fun AddressDisplay(
    id: String,
    fetchAddress: suspend (String) -> AddressDto,
) {
    var addressDto by remember { mutableStateOf<AddressDto?>(null) }

    LaunchedEffect(key1 = id) {
        addressDto = fetchAddress(id)
    }

    SimpleText(text = "Address")
    addressDto?.let { address ->
        Row {
            SimpleText(text = "Type: ${address.type}")
        }
        Row {
            SimpleText(text = "Street: ${address.street}")
        }
        Row {
            SimpleText(text = "City: ${address.city}")
        }
        Row {
            SimpleText(text = "State: ${address.state}")
        }
        Row {
            SimpleText(text = "Zip: ${address.zip}")
        }
    }
}