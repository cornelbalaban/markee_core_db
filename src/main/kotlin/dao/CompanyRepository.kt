package dao

import DbOperationsInterface
import models.CompanyCustomer
import models.CompanyModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class CompanyRepository(val database: Database) : DbOperationsInterface<CompanyModel, Int> {

    private val companiesCache: Map<Int, CompanyModel> = mutableMapOf()


    override fun create(companyDetails: CompanyModel): CompanyModel {
        val createdCompanyId = transaction(database) {
            CompanyCustomer.insert {
                it[customerName] = companyDetails.customerName
                it[customerAdmin] = companyDetails.customerAdmin
            } get CompanyCustomer.customerId
        }

        companyDetails.customerId = createdCompanyId

        return companyDetails
    }

    override fun update(forObject: CompanyModel) {
        //TODO  implement if needed later on
    }

    override fun delete(companyId: Int) {
        transaction(database) {
            CompanyCustomer.deleteWhere { CompanyCustomer.customerId eq companyId }
        }
    }

    fun getCompanybyOwnerId(ownerId: Int): List<CompanyModel> {

        var companiesForOwner: MutableList<CompanyModel> = mutableListOf()

        transaction(database) {
            CompanyCustomer
                    .slice(CompanyCustomer.customerId, CompanyCustomer.customerName)
                    .select({ CompanyCustomer.customerAdmin eq ownerId })
                    .forEach {
                        var exposedCompanyModel = CompanyModel(
                                it[CompanyCustomer.customerName],
                                it[CompanyCustomer.customerId]
                        )

                        companiesForOwner.add(exposedCompanyModel)

                    }
        }

        return companiesForOwner

    }

    fun getCompanyByCompanyId(companyId: Int): CompanyModel? {

        var exposedCompanyModel: CompanyModel? = null

        transaction(database) {
            CompanyCustomer
                    .slice(CompanyCustomer.customerId, CompanyCustomer.customerName, CompanyCustomer.customerAdmin)
                    .select({ CompanyCustomer.customerId eq companyId })
                    .forEach {
                        exposedCompanyModel = CompanyModel(
                                it[CompanyCustomer.customerName],
                                it[CompanyCustomer.customerAdmin],
                                it[CompanyCustomer.customerId]
                        )
                    }
        }

        return exposedCompanyModel
    }
}