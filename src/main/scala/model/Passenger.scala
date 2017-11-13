package model
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by inoquea on 13.11.17.
  */
case class Passenger (id: Option[Int],
                      name: String
                     )

class PassengerTable(tag:Tag) extends Table[Passenger](tag, "passengers"){
  val id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  val name = column[String]("name")
  def * =(id.?,  name) <> (Passenger.apply _ tupled, Passenger.unapply)
}
