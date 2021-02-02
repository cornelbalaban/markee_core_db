package repositories

import DbOperationsInterface
import models.*
import models.CompanyCustomer
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class CompanyRepository(private val database: Database) : DbOperationsInterface<CompanyModel, Int,DaoResponse<CompanyModel>> {

    private val companiesCache: Map<Int, CompanyModel> = mutableMapOf()


    override fun create(companyDetails: CompanyModel): DaoResponse<CompanyModel> {
        val createdCompanyId = transaction(database) {
            CompanyCustomer.insert {
                it[customerName] = companyDetails.customerName
                it[customerAdmin] = companyDetails.customerAdmin
            } get CompanyCustomer.customerId
        }

        companyDetails.customerId = createdCompanyId

        return DaoResponse(DaoResponseCode.CUSTOMER_CREATED, DaoResponseMessage.CUSTOMER_CREATED, companyDetails)
    }

    override fun update(company: CompanyModel): DaoResponse<CompanyModel> {
        //TODO  implement if needed later on
        return DaoResponse(DaoResponseCode.CUSTOMER_UDPATED, DaoResponseMessage.CUSTOMER_UDPATED, company)
    }

    override fun delete(companyId: Int): Int {
        return transaction(database) {
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