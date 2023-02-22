package lewis.libby.hw2.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

//Creating an Address class using Room annotations
@Entity(
    indices = [
        Index(value = ["contactId"])
    ],
)
data class Address(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var type: String,
    var street: String,
    var city: String,
    var state: String,
    var zip: String,
    var contactId: String,
)