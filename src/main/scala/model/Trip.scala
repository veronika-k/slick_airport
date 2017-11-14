package model
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import java.util.Date

import scala.concurrent.Future
import java.sql._
  /* Created by inoquea on 13.11.17.
  */
case class Trip (id: Option[Int],
                 companyId: Int,
                 plane :String,
                 townFrom: String,
                 townTo :String,
                 timeIn: Date,
                 timeOut: Date
//                 timeIn: Timestamp,
//                 timeOut: Timestamp
                )
class TripTable(tag:Tag) extends Table[Trip](tag, "trips"){
  val id = column[Int]("trip_no", O.PrimaryKey)
  val companyId= column[ Int]("ID_comp")
  val plane = column[String]("plane")
  val townFrom= column[ String]("town_from")
  val townTo = column[String]("town_to")
  val timeIn= column[Date]("time_in")
  val timeOut= column[Date]("time_out")

//    val timeIn= column[Timestamp]("time_in")
//    val timeOut= column[Timestamp]("time_out")
  def * =(id.?,  companyId,  plane,  townFrom,  townTo,  timeIn,  timeOut) <> (Trip.apply _ tupled, Trip.unapply)

}

object TripTable {
  val table =TableQuery[TripTable]
}

class TripRepository(db:Database){
  val table =TableQuery[TripTable]
  def create(trip: Trip): Future[Trip] =
    db.run(TripTable.table returning TripTable.table += trip)
  def update (trip: Trip): Future[Int] =
    db.run(TripTable.table.filter(_.id === trip.id).update(trip))
  def delete (tripId: Int): Future[Int] =
    db.run(TripTable.table.filter(_.id === tripId).delete)
  def getById(tripId: Int): Future[Option[Trip]] =
    db.run(TripTable.table.filter(_.id === tripId).result.headOption)
}