package repositories

import DbOperationsInterface
import models.*
import models.Project
import models.Project.projectName
import models.Project.projectOwner
import models.Project.projectParentCompany
import models.Project.projectType
import models.UsersToProjectsMapping
import models.UsersToProjectsMapping.projectId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ProjectsRepository(private val database: Database) : DbOperationsInterface<ProjectModel, Long, DaoResponse<ProjectModel>> {

    override fun create(projectDetails: ProjectModel): DaoResponse<ProjectModel> {

        transaction(database) {
            var createdProjectId = Project.insert {
                it[projectName] = projectDetails.projectName
                it[projectOwner] = projectDetails.ownerId
                it[projectParentCompany] = projectDetails.companyId
                it[projectType] = projectDetails.type
            } get Project.projectId

            projectDetails.projectId = createdProjectId
        }

        return DaoResponse(DaoResponseCode.PROJECT_CREATED, DaoResponseMessage.PROJECT_CREATED, projectDetails)

    }

    override fun update(forObject: ProjectModel): DaoResponse<ProjectModel> {
        TODO("Not yet implemented")
        return DaoResponse(DaoResponseCode.PROJECT_UPDATED, DaoResponseMessage.PROJECT_UPDATED, forObject)
    }

    override fun delete(projectId: Long): Long {
        return (transaction(database) {
            Project.deleteWhere { Project.projectId eq projectId }
        }).toLong()
    }

    fun getProject(forProjectId: List<Long>): List<ProjectModel> {

        val argSize = forProjectId.size
        val selectedProjects : MutableList<ProjectModel> = mutableListOf()

        if (argSize > 1) {

            transaction(database) {
                Project.select { Project.projectId inList forProjectId}
            }.forEach {

                val projectForUser = ProjectModel (
                        it[projectParentCompany],
                        it[projectOwner],
                        it[projectType],
                        it[projectName],
                        it[projectId]
                )
                selectedProjects.add(projectForUser)
            }
        }

        return selectedProjects

    }

    /**
     * returns the projects owned by a company owner
     * @param forOwner contains the user id for which the projects nee to be retrieved
     */
    fun getProjects(forOwner: Int): List<ProjectModel> {

        val projectsForOwner: MutableList<ProjectModel> = mutableListOf()

        transaction(database) {
            val projects = Project.select { Project.projectOwner eq forOwner }.forEach {

                val project = ProjectModel(
                        it[Project.projectParentCompany],
                        it[Project.projectOwner],
                        it[Project.projectType],
                        it[Project.projectName],
                        it[Project.projectId]
                )

                projectsForOwner.add(project)
            }
        }

        return projectsForOwner
    }

    fun getProjectsForUser(forUserId: Int): List<ProjectModel> {

        val projectsForUser: MutableList<ProjectModel> = mutableListOf()


        transaction(database) {

            val projectIdstoUser: MutableList<Long> = mutableListOf()

            UsersToProjectsMapping.select { UsersToProjectsMapping.userId eq forUserId.toLong() }.forEach {
                projectIdstoUser.add(it[projectId])
            }

            if (projectIdstoUser.size > 0) {

                projectsForUser.addAll(getProject(projectIdstoUser))
            }
        }

        return projectsForUser
    }

}