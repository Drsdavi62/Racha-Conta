package com.example.application.billsplitingapp.data.repository


import com.example.application.billsplitingapp.data.cache.BillDao
import com.example.application.billsplitingapp.data.cache.PersonDao
import com.example.application.billsplitingapp.data.cache.ProductDao
import com.example.application.billsplitingapp.data.cache.model.*
import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.domain.repository.BillRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class BillRepositoryImpl @Inject constructor(
    private val billDao: BillDao,
    private val productDao: ProductDao,
    private val personDao: PersonDao
) : BillRepository {

    override fun getBills(): Flow<List<Bill>> {
        return billDao.getBills().map { billEntities ->
            var final: MutableList<BillEntity>
            billEntities.forEach { billEntity ->
                billEntity.people = personDao.getPeopleFromBill(billEntity.id)
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
        productDao.insertProduct(product.toProductEntity())
    }

    override fun getProductsFromBill(billId: Int): Flow<List<Product>> {
        return productDao.getProductsFromBill(billId).map { productEntities -> productEntities.map { it.toProduct() } }
    }

    override suspend fun updateBillValue(billId: Int, value: Float) {
        billDao.updateBillValue(billId, value)
    }

    override suspend fun updateProductAmount(productId: Int, amount: Int) {
        productDao.updateAmount(productId, amount)
    }

    override suspend fun getProductById(id: Int): Product? {
        return try {
            productDao.getProductById(id)?.toProduct()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product.toProductEntity())
    }
}