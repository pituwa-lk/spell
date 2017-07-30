package lk.pituwa.capture


import lk.pituwa.model.{InfoMap, Link, Response, Word}
import net.ruippeixotog.scalascraper.browser.HtmlUnitBrowser
import net.ruippeixotog.scalascraper.browser.HtmlUnitBrowser._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.model._
import org.w3c.dom.html.HTMLParagraphElement


/**
  * Created by nayana on 27/7/17.
  */
class TextExtractor {

  def extract(response: Response):List[String] = {

    val browser: HtmlUnitBrowser = HtmlUnitBrowser.typed()
    browser.underlying.getOptions.setJavaScriptEnabled(false)
    val doc: HtmlUnitDocument = browser.parseString(response.body)
    val pText = doc >> allText("p")
    val dText = doc >> allText("div")
    (pText.split(" ") ++ dText.split(" ")).toList
  }
}
