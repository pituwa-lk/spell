package lk.pituwa.capture

import javax.net.ssl.HostnameVerifier

import com.netaporter.uri.Uri
import com.netaporter.uri.decoding.UriDecodeException
import com.typesafe.scalalogging.Logger
import lk.pituwa.model.{Link, Response}
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

  var logger = Logger("links")

  def extract(response: Response):List[String] = {

    val browser: HtmlUnitBrowser = HtmlUnitBrowser.typed()
    browser.underlying.getOptions.setJavaScriptEnabled(false)
    val doc: HtmlUnitDocument = browser.parseString(response.body)
    ( doc >> pElementList("a") match {
      case elms: List[HtmlUnitElement] => {
        elms.map(elm => {
          if (elm.hasAttr("href") && !elm.underlying.getAttribute("href").contains("mailto")) {
            Some(elm.underlying.getAttribute("href"))
          } else {
            None
          }
        })
      }
      case _ => List.empty
    } ).filter(_.nonEmpty).filter(!_.get.startsWith("#")).map(link => {
      import com.netaporter.uri.dsl._
      try {
        val vx: Uri = link.get
        Right(vx)
      } catch {
        case e:UriDecodeException => {
          logger.info("error on url {}", link.get)
          Left(e)
        }
      }
    }).map(_.right.toOption).filter(_.nonEmpty).map(_.get).map(vx => {

      val prot = vx.scheme match {
        case None => response.request.url.scheme.get
        case Some(protx) => protx
      }

      val host = vx.host match {
        case None => response.request.url.host.get
        case Some(hostx) => hostx
      }

      val path = vx.path
      val query = vx.queryString

      prot + "://" + host + path + query
    }).filter(p = v => {
      import com.netaporter.uri.dsl._
      val vx:Uri = v
      vx.host.get == response.request.url.host.get
    })
  }
}
