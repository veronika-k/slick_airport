package model
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by inoquea on 13.11.17.
  */
case class Passenger (id: Option[Int],
                      name: String
                     )

class PassengerTable(tag:Tag) extends Table[Passenger](tag, "passengers"){
  val id = column[Int]("id_psg", O.PrimaryKey)
  val name = column[String]("name")
  def * =(id.?,  name) <> (Passenger.apply _ tupled, Passenger.unapply)
}

object PassengerTable {
  val table =TableQuery[PassengerTable]
}

class PassengerRepository(db:Database){
  val table =TableQuery[PassengerTable]
  def create(passenger: Passenger): Future[Passenger] =
    db.run(PassengerTable.table returning PassengerTable.table += passenger)

  def update (passenger: Passenger): Future[Int] =
    db.run(PassengerTable.table.filter(_.id === passenger.id).update(passenger))

  def delete (passengerId: Int): Future[Int] =
    db.run(PassengerTable.table.filter(_.id === passengerId).delete)

  def getById(passengerId: Int): Future[Option[Passenger]] =
    db.run(PassengerTable.table.filter(_.id === passengerId).result.headOption)
}