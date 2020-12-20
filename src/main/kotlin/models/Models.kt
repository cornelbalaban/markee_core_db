package models

import MarkeeUsersTables
import org.jetbrains.exposed.sql.Table

object User: Table(MarkeeUsersTables.USERS_TABLE.stringValue) {


}

object CompanyCustomer: Table(MarkeeUsersTables.CUSTOMERS_TABLE.stringValue) {


}

object ProjectsKeys: Table(MarkeeUsersTables.PROJECT_KEYS_TABLE.stringValue) {


}