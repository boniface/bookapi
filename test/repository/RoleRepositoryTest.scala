package repository

import domain.roles.Role
import org.scalatest.FunSuite
import repository.roles.RoleRepository

import scala.concurrent.Await
import scala.concurrent.duration._
class RoleRepositoryTest extends FunSuite{
  val role = Role("2","AD","This is The Admin Role")
  val repository = RoleRepository

  test("createRole"){
    val result = Await.result(repository.apply.saveEntity(role), 2.minutes)
    assert(result)
  }
  test("readRole"){
    val result = Await.result(repository.apply.getEntity(role.id), 2.minutes)

    assert( result.get.roleName=="ADMIN")
  }

  test("roleUpdate"){
    val result = Await.result(repository.apply.getEntity(role.id), 2.minutes)
    val updatedRole = result.get.copy(roleName = "NORMALUSER")
    val savedResult = Await.result(repository.apply.saveEntity(updatedRole), 2.minutes)
    val newRequest = Await.result(repository.apply.getEntity(role.id), 2.minutes)
    assert( newRequest.get.roleName=="NORMALUSER")
  }

  test("readAllRoles"){
    val result = Await.result(repository.apply.getEntities, 2.minutes)
    assert(result.size>0)
  }

  test("deleteRoles"){
    val result = Await.result(repository.apply.deleteEntity(role), 2.minutes)
    assert(result)
  }

}
