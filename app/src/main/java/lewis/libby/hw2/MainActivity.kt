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
            AddressList -> AddressList(addresses = addresses)
            is ContactScreen -> ContactDisplay(
                contactId = screen.id,
                fetchContactWithAddresses = { id ->
                    viewModel.getContactWithAddresses(id)
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
        SimpleText(text = it.firstName) {
            onContactClick(it.id)
        }
    }
}

@Composable
fun AddressList(
    addresses: List<AddressDto>
) {
    SimpleText(text = "Addresses")
    addresses.forEach {
        SimpleText(text = it.street)
    }
}

@Composable
fun ContactDisplay(
    contact: ContactDto
) {
    SimpleText(text = "Contact")
    Row {
        SimpleText(text = "First Name")
        SimpleText(text = contact.firstName)
    }
    Row {
        SimpleText(text = "Email")
        SimpleText(text = contact.email)
    }
}

@Composable
fun ContactDisplay(
    contactId: String,
    fetchContactWithAddresses: suspend (String) -> ContactWithAddressesDto,
) {
    var contactWithAddressesDto by remember { mutableStateOf<ContactWithAddressesDto?>(null) }

    LaunchedEffect(key1 = contactId) {
        contactWithAddressesDto = fetchContactWithAddresses(contactId)
    }

    SimpleText(text = "Contact")
    contactWithAddressesDto?.let { contactWithAddresses ->
        SimpleText(text = contactWithAddresses.contact.firstName)
        contactWithAddresses.addresses.forEach { address ->
            SimpleText(text = "Address: ${address.street}")
        }
    }
}