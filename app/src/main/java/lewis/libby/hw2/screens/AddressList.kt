package lewis.libby.hw2.screens

import androidx.compose.runtime.Composable
import lewis.libby.hw2.components.SimpleText
import lewis.libby.hw2.repository.AddressDto

//Screen setup for list of all addresses
@Composable
fun AddressList(
    addresses: List<AddressDto>,
    onContactClick: (String) -> Unit,       //Ability to click on
) {
    SimpleText(text = "Addresses")
    addresses.forEach {
        SimpleText(text = "${it.type}: ${it.street}") {
            onContactClick(it.id)
        }
    }
}