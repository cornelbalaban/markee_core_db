package core_db.repositories

import core_db.interfaces.DbOperationsInterface
import core_db.models.DaoResponse
import core_db.models.DaoResponseCode
import core_db.models.DaoResponseMessage
import core_db.models.ProjectModel
import core_db.models.Project
import core_db.models.Project.projectId
import core_db.models.Project.projectName
import core_db.models.Project.projectOwner
import core_db.models.Project.projectParentCompany
import core_db.models.Project.projectType
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction


class ProjectsRepository(private val database: Database) :
    DbOperationsInterface<ProjectModel, Int, DaoResponse<ProjectModel>> {

    override fun create(projectDetails: ProjectModel): DaoResponse<ProjectModel> {

        transaction(database) {
            var createdProjectId = Project.insert {
                it[projectName] = projectDetails.projectName
                it[projectOwner] = projectDetails.ownerId
                it[projectParentCompany] = projectDetails.companyId
                it[projectType] = projectDetails.type
            } get projectId

            projectDetails.projectId = createdProjectId
        }

        return DaoResponse(DaoResponseCode.PROJECT_CREATED, DaoResponseMessage.PROJECT_CREATED, projectDetails)

    }

    override fun update(forObject: ProjectModel): DaoResponse<ProjectModel> {
        TODO("Not yet implemented")
        return DaoResponse(DaoResponseCode.PROJECT_UPDATED, DaoResponseMessage.PROJECT_UPDATED, forObject)
    }

    override fun delete(projectId: Int): Int {
        return (transaction(database) {
            Project.deleteWhere { Project.projectId eq projectId }
        })
    }

    fun getProject(forProjectId: List<Int>): List<ProjectModel> {

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
                        it[projectParentCompany],
                        it[projectOwner],
                        it[projectType],
                        it[projectName],
                        it[projectId]
                )

                projectsForOwner.add(project)
            }
        }

        return projectsForOwner
    }

    fun getProjectsForUser(forUserId: Int): List<ProjectModel> {

        val projectsForUser: MutableList<ProjectModel> = mutableListOf()

/*
        transaction(database) {

            val projectIdstoUser: MutableList<Long> = mutableListOf()

            UsersToProjectsMapping.select { UsersToProjectsMapping.userId eq forUserId.toLong() }.forEach {
                projectIdstoUser.add(it[projectId])
            }

            if (projectIdstoUser.size > 0) {

                projectsForUser.addAll(getProject(projectIdstoUser))
            }
        }*/

        return projectsForUser
    }

}
