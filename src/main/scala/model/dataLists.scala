package model

import java.util.Date

/**
  * Created by inoquea on 13.11.17.
  */
object dataLists {
  val compList = List((1,"Don_avia"),
    (2,"Aeroflot"),
    (3,"Dale_avia"),
    (4,"air_France"),
    (5,"British_AW")
  )
  val passList = List((1,"Bruce Willis"),
    (2,"George Clooney"),
    (3,"Kevin Costner"),
    (4,"Donald Sutherland"),
    (5,"Jennifer Lopez"),
    (6,"Ray Liotta"),
    (7,"Samuel L. Jackson"),
    (8,"Nikole Kidman"),
    (9,"Alan Rickman"),
    (10,"Kurt Russell"),
    (11,"Harrison Ford"),
    (12,"Russell Crowe"),
    (13,"Steve Martin"),
    (14,"Michael Caine"),
    (15,"Angelina Jolie"),
    (16,"Mel Gibson"),
    (17,"Michael Douglas"),
    (18,"John Travolta"),
    (19,"Sylvester Stallone"),
    (20,"Tommy Lee Jones"),
    (21,"Catherine Zeta-Jones"),
    (22,"Antonio Banderas"),
    (23,"Kim Basinger"),
    (24,"Sam Neill"),
    (25,"Gary Oldman"),
    (26,"Clint Eastwood"),
    (27,"Brad Pitt"),
    (28,"Johnny Depp"),
    (29,"Pierce Brosnan"),
    (30,"Sean Connery"),
    (31,"Bruce Willis"),
    (37,"Mullah Omar")
  )

  val tripList = List(
    (1100,4,"Boeing","Rostov","Paris","19000101 14:30:00.000","19000101 17:50:00.000"),
    (1101,4,"Boeing","Paris","Rostov","19000101 08:12:00.000","19000101 11:45:00.000"),
    (1123,3,"TU-154","Rostov","Vladivostok","19000101 16:20:00.000","19000101 03:40:00.000"),
    (1124,3,"TU-154","Vladivostok","Rostov","19000101 09:00:00.000","19000101 19:50:00.000"),
    (1145,2,"IL-86","Moscow","Rostov","19000101 09:35:00.000","19000101 11:23:00.000"),
    (1146,2,"IL-86","Rostov","Moscow","19000101 17:55:00.000","19000101 20:01:00.000"),
    (1181,1,"TU-134","Rostov","Moscow","19000101 06:12:00.000","19000101 08:01:00.000"),
    (1182,1,"TU-134","Moscow","Rostov","19000101 12:35:00.000","19000101 14:30:00.000"),
    (1187,1,"TU-134","Rostov","Moscow","19000101 15:42:00.000","19000101 17:39:00.000"),
    (1188,1,"TU-134","Moscow","Rostov","19000101 22:50:00.000","19000101 00:48:00.000"),
    (1195,1,"TU-154","Rostov","Moscow","19000101 23:30:00.000","19000101 01:11:00.000"),
    (1196,1,"TU-154","Moscow","Rostov","19000101 04:00:00.000","19000101 05:45:00.000"),
    (7771,5,"Boeing","London","Singapore","19000101 01:00:00.000","19000101 11:00:00.000"),
    (7772,5,"Boeing","Singapore","London","19000101 12:00:00.000","19000101 02:00:00.000"),
    (7773,5,"Boeing","London","Singapore","19000101 03:00:00.000","19000101 13:00:00.000"),
    (7774,5,"Boeing","Singapore","London","19000101 14:00:00.000","19000101 06:00:00.000"),
    (7775,5,"Boeing","London","Singapore","19000101 09:00:00.000","19000101 20:00:00.000"),
    (7776,5,"Boeing","Singapore","London","19000101 18:00:00.000","19000101 08:00:00.000"),
    (7777,5,"Boeing","London","Singapore","19000101 18:00:00.000","19000101 06:00:00.000"),
    (7778,5,"Boeing","Singapore","London","19000101 22:00:00.000","19000101 12:00:00.000"),
    (8881,5,"Boeing","London","Paris","19000101 03:00:00.000","19000101 04:00:00.000"),
    (8882,5,"Boeing","Paris","London","19000101 22:00:00.000","19000101 23:00:00.000")
  )
  val passInTripList =List(
    (1100,"20030429 00:00:00.000",1,"1a"),
    (1123,"20030405 00:00:00.000",3,"2a"),
    (1123,"20030408 00:00:00.000",1,"4c"),
    (1123,"20030408 00:00:00.000",6,"4b"),
    (1124,"20030402 00:00:00.000",2,"2d"),
    (1145,"20030405 00:00:00.000",3,"2c"),
    (1181,"20030401 00:00:00.000",1,"1a"),
    (1181,"20030401 00:00:00.000",6,"1b"),
    (1181,"20030401 00:00:00.000",8,"3c"),
    (1181,"20030413 00:00:00.000",5,"1b"),
    (1182,"20030413 00:00:00.000",5,"4b"),
    (1187,"20030414 00:00:00.000",8,"3a"),
    (1188,"20030401 00:00:00.000",8,"3a"),
    (1182,"20030413 00:00:00.000",9,"6d"),
    (1145,"20030425 00:00:00.000",5,"1d"),
    (1187,"20030414 00:00:00.000",10,"3d"),
    (8882,"20051106 00:00:00.000",37,"1a"),
    (7771,"20051107 00:00:00.000",37,"1c"),
    (7772,"20051107 00:00:00.000",37,"1a"),
    (8881,"20051108 00:00:00.000",37,"1d"),
    (7778,"20051105 00:00:00.000",10,"2a"),
    (7772,"20051129 00:00:00.000",10,"3a"),
    (7771,"20051104 00:00:00.000",11,"4a"),
    (7771,"20051107 00:00:00.000",11,"1b"),
    (7771,"20051109 00:00:00.000",11,"5a"),
    (7772,"20051107 00:00:00.000",12,"1d"),
    (7773,"20051107 00:00:00.000",13,"2d"),
    (7772,"20051129 00:00:00.000",13,"1b"),
    (8882,"20051113 00:00:00.000",14,"3d"),
    (7771,"20051114 00:00:00.000",14,"4d"),
    (7771,"20051116 00:00:00.000",14,"5d"),
    (7772,"20051129 00:00:00.000",14,"1c")
  )

  val passInTripListForCreate = for (s <- passInTripList) yield {
    val format = new java.text.SimpleDateFormat("yyyyMMdd HH:mm:ss")
    val date:Date = format.parse(s._2)
    Tuple4(s._1,date,s._3,s._4)
  }

  val tripListForCreate = for (s <- tripList) yield {
    val format = new java.text.SimpleDateFormat("yyyyMMdd HH:mm:ss")
    val date1:Date = format.parse(s._6)
    val date2:Date = format.parse(s._7)
    val opt = Option(s._1)
    Tuple7(opt,s._2,s._3,s._4,s._5,date1,date2)
  }
  val compListForCreate = for (s <- compList) yield {
    val opt = Option(s._1)
    Tuple2(opt,s._2)
  }
  val passListForCreate = for (s <- passList) yield {
    val opt = Option(s._1)
    Tuple2(opt,s._2)
  }
}
