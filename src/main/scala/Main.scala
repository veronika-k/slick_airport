/**
  * Created by inoquea on 13.11.17.
  */
import model._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App{
  val db = Database.forURL(
    "jdbc:postgresql://localhost/airport?user=inoquea&password=11111111"
  )
  val companyRepository = new CompanyRepository(db)
  val passengerRepository = new PassengerRepository(db)
  val tripRepository = new TripRepository(db)
  val passInTripRepository = new PassInTripRepository(db)
  //init()
  def init(): Unit =
  {
    Await.result(db.run(CompanyTable.table.schema.create), Duration.Inf)
    Await.result(db.run(PassengerTable.table.schema.create), Duration.Inf)
    Await.result(db.run(TripTable.table.schema.create), Duration.Inf)
    Await.result(db.run(PassInTripTable.table.schema.create), Duration.Inf)
  }

  //fullAll()
  def fullAll():Unit = {
    for (i <- dataLists.compListForCreate) {
      Await.result(companyRepository.create((Company.apply _).tupled(i)), Duration.Inf) }

    for (i <- dataLists.passListForCreate) {
      Await.result(passengerRepository.create((Passenger.apply _).tupled(i)), Duration.Inf) }

    for (i <- dataLists.tripListForCreate) {
      Await.result(tripRepository.create((Trip.apply _).tupled(i)), Duration.Inf) }

    for (i <- dataLists.passInTripListForCreate) {
      Await.result(passInTripRepository.create((PassInTrip.apply _).tupled(i)), Duration.Inf)}
  }
  def exec[T](action: DBIO[T]): T =
    Await.result(db.run(action), 2.seconds)

  select1()
  def select1():Unit = {
    val query  = PassInTripTable.table.join(PassengerTable.table).on(_.passId === _.id).
      groupBy{case (pit, p) => (pit.place, pit.passId, p.name)}.
      map{case ((place, passId, name), group) => (group.size, name)}.
      filter(p => p._1 >1).map(_._2).result
    exec(query).foreach(println)
  }

}
