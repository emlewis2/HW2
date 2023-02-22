package lewis.libby.hw2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//ContactDAO using Room
@Dao
abstract class ContactDAO {
    //Query to return Flow for all contacts
    @Query("SELECT * FROM Contact")
    abstract fun getContactsFlow(): Flow<List<Contact>>

    //Query to return Flow for all addresses
    @Query("SELECT * FROM Address")
    abstract fun getAddressesFlow(): Flow<List<Address>>

    //Query to return the Contact with the Addresses
    @Transaction
    @Query("SELECT * FROM Contact WHERE id = :id")
    abstract suspend fun getContactWithAddresses(id: String): ContactWithAddresses

    //Query to run a specific contact
    @Transaction
    @Query("SELECT * FROM Contact WHERE id = :id")
    abstract suspend fun getContact(id: String): Contact

    //Query to return a specific address
    @Transaction
    @Query("SELECT * FROM Address WHERE id = :id")
    abstract suspend fun getAddress(id: String): Address

    //Insert functions
    @Insert
    abstract suspend fun insert(vararg contacts: Contact)
    @Insert
    abstract suspend fun insert(vararg addresses: Address)

    //Update functions
    @Update
    abstract suspend fun update(vararg contacts: Contact)
    @Update
    abstract suspend fun update(vararg addresses: Address)

    //Delete functions
    @Delete
    abstract suspend fun delete(vararg contacts: Contact)
    @Delete
    abstract suspend fun delete(vararg addresses: Address)

    //Clear functions
    @Query("DELETE FROM Address")
    abstract suspend fun clearAddresses()
    @Query("DELETE FROM Contact")
    abstract suspend fun clearContacts()

    //Reset database function
    @Transaction
    open suspend fun resetDatabase() {
        clearAddresses()
        clearContacts()

        //Sample data
        insert(
            Contact("c1", "Harry", "Potter", "(111) 111-1111",
                "(222) 222-2222", "(333) 333-3333",
                "TheBoyWhoLived@hogwarts.edu"),
            Contact("c2", "Luke", "Skywalker", "(444) 444-4444",
                "(555) 555-5555", "(666) 666-6666",
                "IveGotABadFeelingAboutThis@theforce.net"),
            Contact("c3", "Peter", "Parker", "(777) 777-7777",
                "(888) 888-8888", "(999) 999-9999",
                "DefinitelyNotSpiderman@theavengers.gov"),
        )

        insert(
            Address("a1", "Home", "4 Pivot Drive", "Little Whinging",
                "Surrey", "11111", "c1"),
            Address("a2", "Work", "1 Wizard Street", "Glasgow", "Scotland",
                "22222", "c1"),
            Address("a3", "Home", "55 Lars Homestead Court",
                "Great Chott Salt Flat", "Tatooine", "33333", "c2"),
            Address("a4", "Work", "23 X-Wing Lane", "Rebel Base City",
                "Yavin 4", "44444", "c2"),
            Address("a5", "Home", "1630 15th Street", "Queens, New York City",
                "New York", "10011", "c3"),
            Address("a6", "Work", "3000 Avengers Drive", "Marvel",
                "New York", "12201", "c3"),
        )
    }
}