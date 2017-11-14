/**
  * Created by inoquea on 14.11.17.
  */
package model

import com.github.tminglei.slickpg._

trait MyPostgresDriver extends ExPostgresProfile
  with PgArraySupport
  with PgDate2Support
  with PgDateSupport
  with PgEnumSupport
  with PgRangeSupport
  with PgHStoreSupport
  with PgSearchSupport {

  override val api = new MyAPI {}

  //////
  trait MyAPI extends API
    with ArrayImplicits
    with DateTimeImplicits
    with RangeImplicits
    with HStoreImplicits
    with SearchImplicits
    with SearchAssistants
}

object MyPostgresDriver extends MyPostgresDriver
