package services

import java.time.LocalDateTime

import domain.sites.Site
import org.scalatest.FunSuite
import services.sites.SiteService
import scala.concurrent.duration._
import scala.concurrent.Await

class siteServicesTest extends FunSuite{

  val dateCreated:LocalDateTime = LocalDateTime.now
  val site=Site("001","publisher",Some("this is publisher's site"),dateCreated)
  val services=SiteService


  test("createSite")
  {
    val result = Await.result(services.apply.saveEntity(site), 2.minutes)
    assert(result)

  }

  test("readSite"){
    val result = Await.result(services.apply.getEntity(site.siteId), 2.minutes)

    assert( result.get.siteName=="publisher")
  }

  test("SiteUpdate"){
    val result = Await.result(services.apply.getEntity(site.siteId), 2.minutes)
    val updatedSite = result.get.copy(siteName= "editor")
    val savedResult = Await.result(services.apply.saveEntity(updatedSite), 2.minutes)
    val newRequest = Await.result(services.apply.getEntity(site.siteId), 2.minutes)
    assert( newRequest.get.siteName=="editor")
  }

  test("readAllSite"){
    val result = Await.result(services.apply.getEntities, 2.minutes)
    assert(result.size>0)
  }

  test("deleteSite"){
    val result = Await.result(services.apply.deleteEntity(site), 2.minutes)
    assert(result)
  }













}
