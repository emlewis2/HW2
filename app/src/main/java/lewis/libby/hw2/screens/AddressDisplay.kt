package lewis.libby.hw2.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import lewis.libby.hw2.components.SimpleText
import lewis.libby.hw2.repository.AddressDto

//Display screen setup for Address
@Composable
fun AddressDisplay(
    id: String,     //Address id
    fetchAddress: suspend (String) -> AddressDto,       //Returns AddressDto
) {
    var addressDto by remember { mutableStateOf<AddressDto?>(null) }

    //Launching coroutine
    LaunchedEffect(key1 = id) {
        addressDto = fetchAddress(id)
    }

    //Display all info for an individual address from the AddressDto
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