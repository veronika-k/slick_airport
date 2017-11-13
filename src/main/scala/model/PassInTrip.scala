package model
import java.util.Date
import slick.jdbc.PostgresProfile.api._
/**
  * Created by inoquea on 13.11.17.
  */
case class PassInTrip(tripId: Int, passId : Int, date: Date, place: String)

class PassInTripTable(tag:Tag) extends Table[PassInTrip](tag, "pass_in_trip"){
  val tripId = column[Int]("trip_no")
  val passId = column[Int]("id_psg")
  val date = column[Date]("[date]")
  val place = column[String]("place")
  val pk = primaryKey("pass_in_trip_pk", (tripId, passId, date))

  def * =(tripId, passId , date, place) <> (PassInTrip.apply _ tupled, PassInTrip.unapply)
}