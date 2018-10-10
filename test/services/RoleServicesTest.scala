package services

import domain.roles.Role
import org.scalatest.FunSuite
import services.roles.RoleService
import scala.concurrent.duration._

import scala.concurrent.Await



class RoleServicesTest extends FunSuite{
  val role = Role("2","ADMIN","This is The Admin Role")
  val services= RoleService

  test("createRole"){
    val result = Await.result(services.apply.saveEntity(role), 2.minutes)
    assert(result)
  }
  test("readRole"){
    val result = Await.result(services.apply.getEntity(role.id), 2.minutes)

    assert( result.get.roleName=="ADMIN")

  }

  test("roleUpdate"){
    val result = Await.result(services.apply.getEntity(role.id), 2.minutes)
    val updatedRole = result.get.copy(roleName = "NORMALUSER")
    val savedResult = Await.result(services.apply.saveEntity(updatedRole), 2.minutes)
    val newRequest = Await.result(services.apply.getEntity(updatedRole.id), 2.minutes)
    assert( newRequest.get.roleName=="NORMALUSER")
  }

  test("readAllRoles"){
    val result = Await.result(services.apply.getEntities, 2.minutes)
    assert(result.size>0)
  }

  test("deleteRoles"){
    val result = Await.result(services.apply.deleteEntity(role), 2.minutes)
    assert(result)
  }






}
