package model
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
/**
  * Created by inoquea on 13.11.17.
  */
case class Company (id: Option[Int],
                    name: String
                   )
//object a {
//  val a = (Company.apply _) tupled ((1,"ewew"))
//}


class CompanyTable(tag:Tag) extends Table[Company](tag, "companies"){
val id = column[Int]("id", O.PrimaryKey)
val name = column[String]("name")
def * =(id.?,  name) <> (Company.apply _ tupled, Company.unapply)
}

object CompanyTable {
  val table =TableQuery[CompanyTable]
}

class CompanyRepository(db:Database){
  def create(company: Company): Future[Company] =
    db.run(CompanyTable.table returning CompanyTable.table += company)
  def update (company: Company): Future[Int] =
    db.run(CompanyTable.table.filter(_.id === company.id).update(company))
  def delete (companyId: Int): Future[Int] =
    db.run(CompanyTable.table.filter(_.id === companyId).delete)
  def getById(companyId: Int): Future[Option[Company]] =
    db.run(CompanyTable.table.filter(_.id === companyId).result.headOption)
}