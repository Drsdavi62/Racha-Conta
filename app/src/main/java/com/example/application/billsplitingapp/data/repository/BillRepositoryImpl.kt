package com.example.application.billsplitingapp.data.repository


import com.example.application.billsplitingapp.data.cache.BillDao
import com.example.application.billsplitingapp.data.cache.PersonDao
import com.example.application.billsplitingapp.data.cache.ProductDao
import com.example.application.billsplitingapp.data.cache.ProductWithPeopleDao
import com.example.application.billsplitingapp.data.cache.model.*
import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.domain.model.Person
import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class BillRepositoryImpl @Inject constructor(
    private val billDao: BillDao,
    private val productDao: ProductDao,
    private val personDao: PersonDao,
    private val productWithPeopleDao: ProductWithPeopleDao
) : BillRepository {

    override fun getBills(): Flow<List<Bill>> {
        return billDao.getBills().map { billEntities ->
            var final: MutableList<BillEntity>
            billEntities.forEach { billEntity ->
                billEntity.people = personDao.getStaticPeopleFromBill(billEntity.id)
            }
            billEntities.map { it.toBill() }
        }
    }

    override suspend fun getBillById(id: Int): Bill? {
        return try {
            val bill = billDao.getBillById(id).toBill()
            bill
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun insertBill(bill: Bill): Int {
        val insertedId = billDao.insertBill(bill.toBillEntity())
        return insertedId.toInt()
    }

    override suspend fun deleteBill(bill: Bill) {
        billDao.deleteBill(bill.toBillEntity())
    }

    override suspend fun insertProduct(product: Product) {
        val lId = productDao.insertProduct(product.toProductEntity())
        productWithPeopleDao.deleteRelationsForProduct(lId.toInt())
        product.people.forEach { person ->
            productWithPeopleDao.insertProductWithPeople(
                ProductWithPeopleEntity(
                    lId.toInt(),
                    person.id
                )
            )
        }
    }

    override fun fetchPeopleFromBill(billId: Int): Flow<List<Person>> {
        return personDao.getPeopleFromBill(billId)
            .map { personEntities -> personEntities.map { it.toPerson() } }
    }

    override fun getProductsFromBill(billId: Int): Flow<List<Product>> {
        return productWithPeopleDao.getProductsFromBill(billId)
            .map { productEntities -> productEntities.map { it.toProduct() } }
    }

    override suspend fun updateBillValue(billId: Int, value: Float) {
        billDao.updateBillValue(billId, value)
    }

    override suspend fun updateProductAmount(productId: Int, amount: Int) {
        productDao.updateAmount(productId, amount)
    }

    override suspend fun getProductById(id: Int): Product? {
        return try {
            productWithPeopleDao.getProductById(id)?.toProduct()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product.toProductEntity())
    }

    override fun getFullProductsFromBill(billId: Int): Flow<List<Product>> {
        return productWithPeopleDao.getProductsFromBill(billId)
            .map { productEntities -> productEntities.map { it.toProduct() } }
    }

    override suspend fun getFullProductById(id: Int): Product? {
        return try {
            productWithPeopleDao.getProductById(id)?.toProduct()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun insertPerson(person: Person) {
        val personId = personDao.insertPerson(person.toPersonEntity())
        person.products.forEach { product ->
            productWithPeopleDao.insertProductWithPeople(
                ProductWithPeopleEntity(
                    productId = product.id,
                    personId.toInt()
                )
            )
        }
    }

    override suspend fun getPeopleFromBillList(billId: Int): List<Person> {
        return personDao.getStaticPeopleFromBill(billId).map { it.toPerson() }
    }

    override fun getFullPeopleFromBill(billId: Int): Flow<List<Person>> {
        return productWithPeopleDao.getPeopleFromBill(billId).map { personEntities ->
            personEntities.map { personEntity ->
                val p = personEntity.toPerson()
                p.value = p.products.map { it.fullValue / productWithPeopleDao.getRelationAmountForProduct(it.id) }.sum()
                p
            }
        }
    }

    override suspend fun getFullPersonById(id: Int): Person? {
        return try {
            productWithPeopleDao.getPersonById(id)?.toPerson()
        } catch (e: Exception) {
            null
        }
    }
}