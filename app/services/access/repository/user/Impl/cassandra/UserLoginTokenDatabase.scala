package services.access.repository.user.Impl.cassandra

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.database.Database
import configuration.connections.DataConnection
import services.access.repository.user.Impl.cassandra.table.UserLoginTokenTable

class UserLoginTokenDatabase(override val connector: KeySpaceDef) extends Database[UserLoginTokenDatabase](connector) {

  object userLoginTokenTable extends UserLoginTokenTable with connector.Connector

}
object  UserLoginTokenDatabase extends UserLoginTokenDatabase(DataConnection.connector)
