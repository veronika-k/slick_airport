package model
import java.util.Date

import slick.jdbc.PostgresProfile.api._
import slick.lifted.{PrimaryKey, TableQuery}

import scala.concurrent.Future
/**
  * Created by inoquea on 13.11.17.
  */
case class PassInTrip(tripId: Int,  date: Date, passId : Int, place: String)

class PassInTripTable(tag:Tag) extends Table[PassInTrip](tag, "pass_in_trip"){
  val tripId = column[Int]("trip_no")
  val date = column[Date]("[date]")
  val passId = column[Int]("id_psg")
  val place = column[String]("place")
  val trip_id_fk = foreignKey("trip_no_fk", tripId, TableQuery[TripTable])(_.id)
  val pass_id_fk = foreignKey("id_psg_fk", passId, TableQuery[PassengerTable])(_.id)
  val pk = primaryKey("pass_in_trip_pk", (tripId, passId, date))
  def * =(tripId,  date, passId, place) <> (PassInTrip.apply _ tupled, PassInTrip.unapply)
}

object PassInTripTable {
  val table = TableQuery[PassInTripTable]
}

class PassInTripRepository(db:Database){
  val table = TableQuery[PassInTripTable]
  def create(passInTrip: PassInTrip): Future[PassInTrip] =
    db.run(PassInTripTable.table returning PassInTripTable.table += passInTrip)

  def update (passInTrip: PassInTrip): Future[Int] =
    db.run(PassInTripTable.table.filter(p => (p.tripId === passInTrip.tripId) &&
      (p.passId === passInTrip.passId) &&
      (p.date === passInTrip.date)).update(passInTrip))

  def delete (tripId:Int, passId:Int, date:Date ): Future[Int] =
    db.run(PassInTripTable.table.filter(p => (p.tripId === tripId) &&
      (p.passId === passId) &&
      (p.date === date)).delete)

  def getByTripPassDate(tripId:Int, passId:Int, date:Date): Future[Option[PassInTrip]] =
    db.run(PassInTripTable.table.filter(p => (p.tripId === tripId) &&
      (p.passId === passId) &&
      (p.date === date)).result.headOption)
}