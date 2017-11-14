/**
  * Created by inoquea on 13.11.17.
  */
import model._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Await
import scala.concurrent.duration._
import java.util.Date
import java.sql.{Date, Time, Timestamp}
import scala.concurrent.ExecutionContext.Implicits.global
import com.github.tminglei.slickpg._

object Main extends App {
  val db = slickProfile.api.Database.forURL(Config.url)
  val companyRepository = new CompanyRepository(db)
  val passengerRepository = new PassengerRepository(db)
  val tripRepository = new TripRepository(db)
  val passInTripRepository = new PassInTripRepository(db)

  //init()
  def init(): Unit = {
    Await.result(db.run(companyRepository.table.schema.create), Duration.Inf)
    Await.result(db.run(passengerRepository.table.schema.create), Duration.Inf)
    Await.result(db.run(tripRepository.table.schema.create), Duration.Inf)
    Await.result(db.run(passInTripRepository.table.schema.create), Duration.Inf)
  }

  //fullAll()
  def fullAll(): Unit = {
    for (i <- dataLists.compListForCreate) {
      Await.result(companyRepository.create((Company.apply _).tupled(i)), Duration.Inf)
    }

    for (i <- dataLists.passListForCreate) {
      Await.result(passengerRepository.create((Passenger.apply _).tupled(i)), Duration.Inf)
    }

    for (i <- dataLists.tripListForCreate) {
      Await.result(tripRepository.create((Trip.apply _).tupled(i)), Duration.Inf)
    }

    for (i <- dataLists.passInTripListForCreate) {
      Await.result(passInTripRepository.create((PassInTrip.apply _).tupled(i)), Duration.Inf)
    }
  }

  def exec[T](action: DBIO[T]): T =
    Await.result(db.run(action), 10.seconds)

  //select1()
  def select63(): Unit = {
    val query = passInTripRepository.table.join(passengerRepository.table).on(_.passId === _.id).
      groupBy { case (pit, p) => (pit.place, pit.passId, p.name) }.
      map { case ((place, passId, name), group) => (group.size, name) }.
      filter(p => p._1 > 1).map(_._2).result
    exec(query).foreach(println)
  }

  //select2()
  def select67(): Unit = {

    val max_size = tripRepository.table.
      groupBy { case t => (t.townFrom, t.townTo) }.
      map { case ((townFrom, townTo), group) => group.size }.
      groupBy { _ => true }.
      map { case (_, group) => group.max }

    val query = tripRepository.table.
      groupBy { case t => (t.townFrom, t.townTo) }.
      map { case ((townFrom, townTo), group) => (townFrom, townTo, group.size) }.
      filter { case (townFrom, townTo, size) => size in max_size }.length.result

    println(exec(query))
  }

  //select3()
  def select68(): Unit = {
    val q = (tripRepository.table.map(t => (t.id, t.townFrom, t.townTo)) unionAll
      tripRepository.table.map(t => (t.id, t.townTo, t.townFrom))).
      groupBy { case t => (t._2, t._3) }.
      map { case (_, group) => group.size }

    val max_size = q.
      groupBy { _ => true }.
      map { case (_, group) => group.max }

    val query = q.filter {
      _ in max_size
    }.length.result
    println(exec(query) / 2)
  }

  //select4()

  def select72(): Unit = {
    val passAndComp = passInTripRepository.table.join(tripRepository.table).on(_.tripId === _.id).
      groupBy { case (pit, p) => (pit.passId, p.companyId) }.
      map { case ((passId, companyId), group) => (passId, companyId, group.size) }

    val companyPassMax = passAndComp.
      //map{case (passId, companyId, s) => (companyId, s)}.
      //groupBy{case pac => pac._1}.
      //map{case (companyId, group) => (companyId, group.map(_._2).max)}
      map { case (passId, companyId, s) => s }.
      groupBy { _ => true }.
      map { case (_, group) => group.max }

    //    val passIdWithMax = passAndComp.join(companyPassMax).on(_._2 === _._1).
    //      filter{case (pac,cpm) => pac._3 === cpm._2}.
    //      map{case (pac,cpm) => (pac._1, pac._3)}

    val passIdWithMax = passAndComp.
      filter { case pac => pac._3 in companyPassMax }.
      map { case pac => (pac._1, pac._3) }


    val passNames = passIdWithMax.join(passengerRepository.table).on(_._1 === _.id).
      map { case (pIdMax, pas) => (pas.name, pIdMax._2) }

    exec(passNames.result).foreach(println)
  }

  //exec(clearQuery.result).foreach(println)
  //  groupBy{ case t => t._3}.
  //    map{case (size, group) => (group, group.map(_._3).max)}.

  //select5()

  def select77(): Unit = {
    val query = tripRepository.table.
      filter(_.townFrom === "Rostov").
      map(_.timeOut.asColumnOf[Timestamp])
    exec(query.result).foreach(println)
  }
  def select88() :Unit ={

  }

}
