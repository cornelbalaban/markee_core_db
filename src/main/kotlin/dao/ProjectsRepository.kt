package dao

import DbOperationsInterface
import models.Project
import models.ProjectModel
import models.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class ProjectsRepository(val database: Database): DbOperationsInterface<ProjectModel, Int> {

    override fun create(projectDetails: ProjectModel): ProjectModel {

        transaction(database) {
            var createdProjectId = Project.insert {
                it[projectName] = projectDetails.projectName
                it[projectOwner] = projectDetails.ownerId
                it[projectParentCompany] = projectDetails.companyId
                it[projectType] =  projectDetails.type
            } get Project.projectId

            projectDetails.projectId = createdProjectId
        }

        return projectDetails

    }

    override fun update(forObject: ProjectModel) {
        TODO("Not yet implemented")
    }

    override fun delete(forObject: Int) {
        TODO("Not yet implemented")
    }
}