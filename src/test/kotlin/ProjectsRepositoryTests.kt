import org.jetbrains.exposed.sql.Database
import org.junit.After
import org.junit.Before
import org.junit.Test
import repositories.ProjectsRepository

class ProjectsRepositoryTests {

    var markeeDbConnector: MarkeeDbConnector? = null
    var markeeUsersTables: Database? = null
    var projectsRepository: ProjectsRepository? = null


    @Before
    fun setup() {
       markeeDbConnector = MarkeeDbConnector()
       markeeDbConnector?.let {
           markeeUsersTables = it.usersDbConnection()
           projectsRepository = ProjectsRepository(markeeUsersTables!!)//forcing NPE works in case of unit tests
       }


    }

    @After
    fun tearDown() {
        markeeDbConnector = null
        markeeUsersTables = null
        projectsRepository = null
    }

    @Test
    fun testGetProjectsForUser() {
        //this one needs to actually be ceated first
        /*val userId = 61
        val projectsForUser = projectsRepository?.getProjectsForUser(userId)

        assert(projectsForUser?.size != 0)*/
    }


}