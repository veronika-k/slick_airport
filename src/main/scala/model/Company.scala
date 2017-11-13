package model
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by inoquea on 13.11.17.
  */
case class Company (id: Option[Int],
                    name: String
                   )

class CompanyTable(tag:Tag) extends Table[Company](tag, "companies"){
val id = column[Int]("id", O.PrimaryKey, O.AutoInc)
val name = column[String]("name")
def * =(id.?,  name) <> (Company.apply _ tupled, Company.unapply)
}
