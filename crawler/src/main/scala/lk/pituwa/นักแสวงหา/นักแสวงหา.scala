package lk.pituwa.นักแสวงหา

import scalaj.http._

/**
  * Created by nayana on 26/7/17.
  *
  * This comment block contains curse sentence for each time the zwj on (prefix or suffix) shifted chars and cause havoc
  *
  * අසර  වි ංංදචව දවඤසදච.වචට ද
  * achigey redda thopi unicode walin sinhala kala thiyenney pako!!!
  * //බලපන් පල්ල හා දාලතිය න තා යිබාසා වතාිම දවච ව
  * pakoooooo!!! penawada thopi zwj pukey araganin issara passta dala nikan guuu godak karaya pakooo!!!!
  *
  * 111777111
  *
  * */
class นักแสวงหา(เว็บไซต์: String) {

  /**
    *
    * @return
    */
  def การจับกุม: String = {
    val response: HttpResponse[String] = Http(เว็บไซต์).asString
    response.code match {
      case 200 => response.body
      case _ => throw new Exception("server returned error" + response.code)
    }
  }
}
