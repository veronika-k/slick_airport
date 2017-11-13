package model
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import java.util.Date

  /* Created by inoquea on 13.11.17.
  */
case class Trip (id: Option[Int],
                 companyId: Int,
                 plane :String,
                 townFrom: String,
                 townTo :String,
                 timeIn: Date,
                 timeOut: Date
                )
class TripTable(tag:Tag) extends Table[Trip](tag, "trips"){
  val id = column[Int]("trip_no", O.PrimaryKey, O.AutoInc)
  val companyId= column[ Int]("ID_comp")
  val plane = column[String]("plane")
  val townFrom= column[ String]("town_from")
  val townTo = column[String]("town_to")
  val timeIn= column[Date]("time_in")
  val timeOut= column[Date]("time_out")
  def * =(id.?,  companyId,  plane,  townFrom,  townTo,  timeIn,  timeOut) <> (Trip.apply _ tupled, Trip.unapply)

}

