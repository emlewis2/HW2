package lewis.libby.hw2.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//Simple button for clicking
@Composable
fun SimpleButton(
    text: String,
    onClick: () -> Unit,
) =
    Button(onClick = onClick, modifier = Modifier.padding(8.dp)) {
        Text(text = text, modifier = Modifier.padding(8.dp))
    }
