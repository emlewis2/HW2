package lewis.libby.hw2.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import lewis.libby.hw2.components.SimpleText
import lewis.libby.hw2.repository.ContactWithAddressesDto

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