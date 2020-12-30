import dao.CompanyRepository
import models.CompanyModel
import org.jetbrains.exposed.sql.Database
import org.junit.After
import org.junit.Before
import org.junit.Test

class CompanyRepositoryTests {

    private var dbConnector: MarkeeDbConnector? = null
    private var companyRepository: CompanyRepository? = null
    var companyId: Int = 15


    @Before
    fun setup() {
        dbConnector = MarkeeDbConnector()

        dbConnector?.let {
            companyRepository = CompanyRepository(it.usersDbConnection())
        }

    }

    @Test
    fun testCreateCompany() {

    }

    @Test
    fun testGetCompanyModel() {
        println("--------------- Retrieving company -------------")
        companyRepository?.let {
            val companySelected: CompanyModel? = it.getCompanyByCompanyId(companyId)

            assert(companySelected != null)
            companySelected?.let {

                println("*********** Company Retrieved ***********")
                println("********* Company Retrieved ${it.customerName} *********")
                println("********* Company Retrieved ${it.customerId} *********")

                assert(it.customerId == companyId)
            }
        }

        println("---------------Retrieving company -------------")

    }

    @After
    fun tearDown() {
        companyRepository = null
        dbConnector = null
    }
}