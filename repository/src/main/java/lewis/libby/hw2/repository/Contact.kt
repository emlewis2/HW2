package lewis.libby.hw2.repository

import lewis.libby.hw2.data.Contact
import lewis.libby.hw2.data.ContactWithAddresses

//Contact DTO class
data class ContactDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val homePhone: String,
    val workPhone: String,
    val mobilePhone: String,
    val email: String,
)

//Convert to DTO
internal fun Contact.toDto() =
    ContactDto(id = id, firstName = firstName, lastName = lastName, homePhone = homePhone,
        workPhone = workPhone, mobilePhone = mobilePhone, email = email)
//Convert to Entity
internal fun ContactDto.toEntity() =
    Contact(id = id, firstName = firstName, lastName = lastName, homePhone = homePhone,
        workPhone = workPhone, mobilePhone = mobilePhone, email = email)

//Contact with Addresses DTO
data class ContactWithAddressesDto(
    val contact: ContactDto,
    val addresses: List<AddressDto>
)

//Convert to DTO, don't need convert to entity
internal fun ContactWithAddresses.toDto() =
    ContactWithAddressesDto(
        contact = contact.toDto(),
        addresses = addresses.map { it.toDto() },
    )
