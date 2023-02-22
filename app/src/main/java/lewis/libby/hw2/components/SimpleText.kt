package lewis.libby.hw2.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//To display simple text and have onClick function
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
