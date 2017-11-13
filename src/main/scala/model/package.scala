/**
  * Created by inoquea on 13.11.17.
  */
import java.sql._
import java.util.Date

import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import slick.jdbc.PostgresProfile.api._

package object model {
  implicit val DateToTimestamp: JdbcType[Date] with BaseTypedType[Date] =
    MappedColumnType.base[Date, Timestamp](d => new Timestamp(d.getTime()),
  t => new Date(t.getTime()) )
}
