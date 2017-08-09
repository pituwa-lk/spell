package lk.pituwa.capture

import lk.pituwa.model.Response
import net.ruippeixotog.scalascraper.browser.HtmlUnitBrowser
import net.ruippeixotog.scalascraper.browser.HtmlUnitBrowser._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._


/**
  * Created by nayana on 27/7/17.
  */
class TextExtractor {

  def extract(response: Response):String = {

    val browser: HtmlUnitBrowser = HtmlUnitBrowser.typed()
    browser.underlying.getOptions.setJavaScriptEnabled(false)
    val doc: HtmlUnitDocument = browser.parseString(response.body)
    val pText = doc >> allText("p")
    val dText = doc >> allText("div")
    val fText = doc >> allText("font")
    pText + " " + dText + " " + fText
  }
}
