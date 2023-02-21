package lewis.libby.hw2.repository

import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    val contactsFlow: Flow<List<ContactDto>>
    val addressesFlow: Flow<List<AddressDto>>

    suspend fun getContactWithAddresses(id: String): ContactWithAddressesDto

    suspend fun getContact(id: String): ContactDto

    suspend fun getAddress(id: String): AddressDto

    suspend fun insert(address: AddressDto)
    suspend fun insert(contact: ContactDto)

    suspend fun update(address: AddressDto)
    suspend fun update(contact: ContactDto)

    suspend fun delete(address: AddressDto)
    suspend fun delete(contact: ContactDto)

    suspend fun resetDatabase()
}