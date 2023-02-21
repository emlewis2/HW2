package lewis.libby.hw2.repository

import android.content.Context
import lewis.libby.hw2.data.createDao
import kotlinx.coroutines.flow.map

class ContactDatabaseRepository(context: Context): ContactRepository {
    private val dao = createDao(context)

    override val contactsFlow =
        dao.getContactsFlow()
            .map { contacts ->
                contacts.map { it.toDto() }
            }
    override val addressesFlow =
        dao.getAddressesFlow()
            .map { addresses ->
                addresses.map { it.toDto() }
            }

    override suspend fun getContactWithAddresses(id: String): ContactWithAddressesDto =
        dao.getContactWithAddresses(id).toDto()

    override suspend fun getContact(id: String): ContactDto =
        dao.getContact(id).toDto()

    override suspend fun getAddress(id: String): AddressDto =
        dao.getAddress(id).toDto()

    override suspend fun insert(address: AddressDto) = dao.insert(address.toEntity())
    override suspend fun insert(contact: ContactDto) = dao.insert(contact.toEntity())

    override suspend fun update(address: AddressDto) = dao.update(address.toEntity())
    override suspend fun update(contact: ContactDto) = dao.update(contact.toEntity())

    override suspend fun delete(address: AddressDto) = dao.delete(address.toEntity())
    override suspend fun delete(contact: ContactDto) = dao.delete(contact.toEntity())

    override suspend fun resetDatabase() = dao.resetDatabase()
}