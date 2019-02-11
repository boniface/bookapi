package configuration.connections

import okhttp3.{MediaType, OkHttpClient, Request, RequestBody}

object NetConnection {

  lazy val apiUrl : String = Configuration.config.getString("api.baseUrl")
  implicit val mediaType: MediaType = MediaType.parse("application/json")


  def getClient: OkHttpClient ={
    new OkHttpClient()
  }

  def buildRequest(url:String, jsondata:String) (implicit mediaType: MediaType): Request ={
   new Request.Builder()
      .url(url)
      .post(RequestBody.create(mediaType, jsondata))
      .build()
  }
}
