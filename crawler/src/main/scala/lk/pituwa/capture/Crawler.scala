package lk.pituwa.capture

import lk.pituwa.model.{Request, Response}

import scalaj.http._

/**
  * Created by nayana on 26/7/17.
  *
  * This comment block contains curse sentence for each time the zwj on (prefix or suffix) shifted chars and cause havoc
  *
  * අසර  වි ංංදචව දවඤසදච.වචට ද
  * achigey redda thopi unicode walin sinhala kala(කාලා\\පේනවනේ\\අනිත්\\අකුරු\\වලින්\\ලිව්වම\\නෑ\\තේරේන්නේ\\) thiyenney pako!!!
  * //බලපන් පල්ල හා දාලතිය න තායිබාසා ගහපු\\අකුර\\වැදි\\ලා\\ඇති\\කාටහරි\\අමතකවලො\\කොන්ටුෝල්\\කීස්\\දාලා\\තියවා\\අගට\\ස්පේස්\\එබුවම\\අන්තිම\\අකුර\\පනිවවා\\
  * pakoooooo!!! penawada thopi zwj pukey araganin issara passta dala nikan guuu godak karaya pakooo!!!!
  *
  * 111777111
  *
  * */
class Crawler(request: Request) {

  /**
    *
    * @return
    */
  def crawl: Response = {
    val response: HttpResponse[String] = Http(request.uri).asString
    response.code match {
      case 200 => Response(request, response.body)
      case _ => throw new Exception("server returned error" + response.code)
    }
  }

  def sniff: Response = {
    val response: HttpResponse[String] = Http(request.uri).method("HEAD").asString
    response.code match {
      case 200 => response.header("Content-Type") match {
        case Some(header) => Response(request, header)
        case None => Response(request, "application/unknown")
      }
      case _ => throw new Exception("server returned error" + response.code)
    }
  }
}
