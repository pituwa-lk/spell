package lk.pituwa.capture

import javax.net.ssl.HostnameVerifier

import com.netaporter.uri.Uri
import lk.pituwa.model.Response
import net.ruippeixotog.scalascraper.browser.{HtmlUnitBrowser, JsoupBrowser}
import net.ruippeixotog.scalascraper.browser.HtmlUnitBrowser._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.scraper._
/**
  * Created by nayana on 27/7/17.
  */
class LinkExtractor {

  def extract(response: Response):List[String] = {

    val browser: HtmlUnitBrowser = HtmlUnitBrowser.typed()
    val doc: HtmlUnitDocument = browser.parseString(response.body)
    ( doc >> pElementList("a") match {
      case elms: List[HtmlUnitElement] => {
        elms.map(elm => {
          if (elm.hasAttr("href")) {
            Some(elm.underlying.getAttribute("href"))
          } else {
            None
          }
        })
      }
      case _ => List.empty
    } ).filter(_.nonEmpty).map(link => {

      import com.netaporter.uri.dsl._
      val vx: Uri = link.get

      val prot = vx.protocol match {
        case None => response.request.url.protocol.get
        case Some(prot) => prot
      }

      val host = vx.host match {
        case None => response.request.url.host.get
        case Some(host) => host
      }

      val path = vx.path
      val query = vx.queryString

      prot + "://" + host + path + query
    })
  }
}
