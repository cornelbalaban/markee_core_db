package dao

import DbOperationsInterface
import models.CompanyCustomer
import models.CompanyModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class CompanyRepository(val database: Database) : DbOperationsInterface<CompanyModel, Int> {
        
    private val companiesCache: Map<Int, CompanyModel> = mutableMapOf()


    override fun create(companyDetails: CompanyModel, ownerId: Int): Int {
        return transaction (database) {
            CompanyCustomer.insert {
                it[customerName] = companyDetails.customerName
                it[customerAdmin] = ownerId
            } get CompanyCustomer.customerId
        }
    }

    override fun update(forObject: CompanyModel) {
        //TODO("Not yet implemented")
    }

    override fun delete(companyId: Int) {
        //TODO("Not yet implemented")
    }
    
    fun getCompanybyOwnerId(ownerId: Int): CompanyModel? {
        return null
    }

    fun getCompanyByCompanyId(companyId: Int): CompanyModel? {

        var exposedCompanyModel: CompanyModel? = null
        
        transaction (database) {
            CompanyCustomer
                    .slice(CompanyCustomer.customerId, CompanyCustomer.customerName)
                    .select( {CompanyCustomer.customerId eq companyId})
                    .forEach {
                        exposedCompanyModel = CompanyModel(
                                it[CompanyCustomer.customerId],
                                it[CompanyCustomer.customerName]
                        )
                    }
        }

        return exposedCompanyModel
    }
}