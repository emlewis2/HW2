package lewis.libby.hw2.repository

import lewis.libby.hw2.data.Address

//Address DTO class
data class AddressDto(
    val id: String,
    val type: String,
    val street: String,
    val city: String,
    val state: String,
    val zip: String,
    val contactId: String,
)

//Convert to DTO
internal fun Address.toDto() =
    AddressDto(id = id, type = type, street = street, city = city, state = state,
        zip = zip, contactId = contactId)
//Convert to Entity
internal fun AddressDto.toEntity() =
    Address(id = id, type = type, street = street, city = city, state = state,
        zip = zip, contactId = contactId)