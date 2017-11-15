/**
  * Created by inoquea on 13.11.17.
  */
import model._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration._
import java.util.Date
import java.sql.{Date, Time, Timestamp}
import java.text.SimpleDateFormat

import scala.concurrent.ExecutionContext.Implicits.global
import com.github.tminglei.slickpg._
import slick.lifted.QueryBase

import scala.{None, Option}

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

  //select63()
  def select63(): Unit = {
    val query = passInTripRepository.table.join(passengerRepository.table).on(_.passId === _.id).
      groupBy { case (pit, p) => (pit.place, pit.passId, p.name) }.
      map { case ((place, passId, name), group) => (group.size, name) }.
      filter(p => p._1 > 1).map(_._2).result
    exec(query).foreach(println)
  }

  //select67()
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

  //select68()
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

  //select72()

  def select72(): Unit = {
    val passAndComp = passInTripRepository.table.join(tripRepository.table).on(_.tripId === _.id).
      groupBy { case (pit, p) => (pit.passId, p.companyId) }.
      map { case ((passId, companyId), group) => (passId, companyId, group.size) }

    val companyPassMax = passAndComp.
      map { case (passId, companyId, s) => s }.
      groupBy { _ => true }.
      map { case (_, group) => group.max }

    val passIdWithMax = passAndComp.
      filter { case pac => pac._3 in companyPassMax }.
      map { case pac => (pac._1, pac._3) }


    val passNames = passIdWithMax.join(passengerRepository.table).on(_._1 === _.id).
      map { case (pIdMax, pas) => (pas.name, pIdMax._2) }

    exec(passNames.result).foreach(println)
  }



  //select77()
  def select77(): Unit = {
    val query = tripRepository.table.
      filter(_.townFrom === "Rostov").
      join(passInTripRepository.table).on(_.id === _.tripId).
      map{case (t,pit) => (pit.tripId, pit.date)}.
      groupBy{case (id,date) => date.asColumnOf[java.sql.Date]}.
      map{case (date,group) => (date,group.size)}
    exec(query.result).foreach(println)
  }

  //select88()
  def select88() :Unit ={
    val singlPass = passInTripRepository.table.
      join(passengerRepository.table).on(_.passId === _.id).
      join(tripRepository.table).on{case ((pit, p), t) => pit.tripId === t.id}.
      map{case ((pit, pas), t) => (pit.passId, t.companyId, pit.tripId, pas.name)}.
      groupBy { case (pass, comp, trip, name) => pass}.
      map { case (pass, group) => (
        pass, group.map(_._2).countDistinct, group.map(_._3).size, group.map(_._2).max, group.map(_._4).max)
      }.
      filter{ case (pass, compNum, tripNum, comp, name) => compNum === 1}.map(t=> (t._1,t._3,t._4,t._5))

    val max_time = singlPass.map(_._2).max

    val query = singlPass.join(companyRepository.table).on(_._3 === _.id).
      map{case(spass, comp) => (spass._4, spass._2, comp.name)}.
      filter{case(pass, tripN, comp) => tripN === max_time}

      exec(query.result).foreach(println)
  }


  //select95()
  def select95() :Unit ={
    val queryRepos = passInTripRepository.table.join(tripRepository.table).on(_.tripId === _.id).
      map{case (pit, t) => (t.companyId, pit.tripId, t.plane, pit.passId)}

    val queryCompId = queryRepos.
      groupBy{case (comp,trip,plane,pass) => comp}.
      map{case (comp, group) => (
        comp, group.map(_._2).size,
        group.map(_._3).countDistinct,
        group.map(_._4).countDistinct,
        group.map(_._3).size
      )
      }
    val query = queryCompId.join(companyRepository.table).on(_._1 === _.id).
      map{case (q, c) => (c.name,q._2,q._3, q._4, q._5)}

    exec(query.result).foreach(println)
  }

  //select102()
  def select102(): Unit = {
    val tripRoutes = tripRepository.table.map{
      f => (f.id, Case If (f.townTo < f.townFrom) Then f.townTo Else f.townFrom,
        Case If (f.townTo < f.townFrom) Then f.townFrom Else f.townTo)
    }

    val passInTripRoute = tripRoutes.join(passInTripRepository.table).on(_._1 === _.tripId).
      map{case (tr, pit) => (tr._2, tr._3, pit.passId)}.
      join(passengerRepository.table).on(_._3 === _.id).
      map{case (tr, p) => ( p.name, tr._1, tr._2)}.
      groupBy{case (passname,town1, town2) => passname}.
      map{ case (passname, group) => (passname, group.map(_._2).countDistinct,group.map(_._3).countDistinct)}.
      filter{s => (s._2 === 1) &&(s._3 === 1)}

    exec(passInTripRoute.result).foreach(println)
  }

  //select103()
  def select103(): Unit = {
    val tripMax = tripRepository.table.sortBy(_.id.desc).take(3).map(_.id)
    val tripMin = tripRepository.table.sortBy(_.id.asc).take(3).map(_.id)
    val query = tripMin.take(1).join(tripMin.drop(1).take(1)).
      join(tripMin.drop(2).take(1)).map{case (s1,s2) => (s1._1, s1._2, s2)}.
      join(tripMax.drop(2).take(1)).map{case (s1,s2) => (s1._1, s1._2, s1._3,s2)}.
      join(tripMax.drop(1).take(1)).map{case (s1,s2) => (s1._1, s1._2, s1._3,s1._4, s2)}.
      join(tripMax.take(1)).map{case (s1,s2) => (s1._1, s1._2, s1._3,s1._4, s1._5, s2)}

      println(exec(query.result))
  }

  //select107()
  def select107(): Unit = {
    val format1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("20030401 00:00:00")
    val date1 = new java.sql.Date (format1.getTime)
    val format2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("20030501 00:00:00")
    val date2 = new java.sql.Date (format2.getTime)

    val queryRepos = passInTripRepository.table.join(tripRepository.table).on(_.tripId === _.id).
      join(companyRepository.table).on{case ((pit, t), c) => t.companyId === c.id}.
      map{case ((pit, t), c) => (t.townFrom, c.name, pit.tripId, pit.date, pit.passId)}.
      filter{s => s._1 === "Rostov"}.
      filter{s => s._4.asColumnOf[java.sql.Date] > date1 && s._4.asColumnOf[java.sql.Date] < date2}.
      sortBy{s => s._4.desc}.drop(4).take(1).
      map{s => (s._2, s._3, s._4)}

    exec(queryRepos.result).foreach(println)
  }

  //select84()
  def select84(): Unit = {
    val format = new SimpleDateFormat("yyyyMMdd HH:mm:ss")
    val date1 = new java.sql.Date (format.parse("20030401 00:00:00").getTime)
    val date2 = new java.sql.Date (format.parse("20030411 00:00:00").getTime)
    val date3 = new java.sql.Date (format.parse("20030421 00:00:00").getTime)
    val date4 = new java.sql.Date (format.parse("20030501 00:00:00").getTime)
    println(date1, date2,date3,date4)

    val queryRepos = passInTripRepository.table.join(tripRepository.table).on(_.tripId === _.id).
      join(companyRepository.table).on{case ((pit, t), c) => t.companyId === c.id}.
      map{case ((pit, t), c) => (c.name, pit.date, pit.passId)}

    val query1 = queryRepos.
        filter{s => s._2.asColumnOf[java.sql.Date] >= date1 && s._2.asColumnOf[java.sql.Date] < date2}.
        groupBy{case (comp,date,pass) => comp}.
        map{case (comp,group) => (comp, group.size)}

    val query2 =queryRepos.
      filter{s => s._2.asColumnOf[java.sql.Date] >= date2 && s._2.asColumnOf[java.sql.Date] < date3}.
      groupBy{case (comp,date,pass) => comp}.
      map{case (comp,group) => (comp, group.size)}

    val query3 =queryRepos.
      filter{s => s._2.asColumnOf[java.sql.Date] >= date3 && s._2.asColumnOf[java.sql.Date] < date4}.
      groupBy{case (comp,date,pass) => comp}.
      map{case (comp,group) => (comp, group.size)}

    val query1per = for {(e, p) <- companyRepository.table.map(_.name).
      joinLeft(query1).on(_ === _._1) } yield (e, p.map(_._2))

    val query12per = for {
      (e, p) <- query1per.joinLeft(query2).on(_._1 === _._1)} yield
      (e._1, e._2, p.map(_._2))

    val query123period = for {
      (e, p) <- query12per.joinLeft(query3).on(_._1 === _._1)} yield
      (e._1, e._2, e._3, p.map(_._2))

    exec(query123period.result).foreach(println)
  }

  //select114()
  def select114(): Unit = {
    val queryRepos = passInTripRepository.table.join(passengerRepository.table).on(_.passId === _.id).
      map{case (pit,p) => (p.name, pit.place)}.
      groupBy{case (name, place) => (name, place)}.
      map{case ((name,place), group) => (name, group.size)}

    val maxSize = queryRepos.map(_._2).
      groupBy { _ => true }.
      map { case (_, group) => group.max }

    val query = queryRepos.
      filter{case (name, times) => times in maxSize}

    exec(query.result).foreach(println)
  }
}




