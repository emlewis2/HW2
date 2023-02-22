package lewis.libby.hw2.screens

import androidx.compose.runtime.Composable
import lewis.libby.hw2.components.SimpleText
import lewis.libby.hw2.repository.ContactDto

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