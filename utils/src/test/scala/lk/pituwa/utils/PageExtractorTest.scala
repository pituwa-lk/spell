package lk.pituwa.utils

import java.io.File

import org.scalatest.FlatSpec

/**
  * Created by nayana on 2/8/17.
  */
class PageExtractorTest extends FlatSpec {

  "divayona" should "contain text" in {
    import net.ruippeixotog.scalascraper.browser.HtmlUnitBrowser
    import net.ruippeixotog.scalascraper.browser.HtmlUnitBrowser._
    import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
    import net.ruippeixotog.scalascraper.dsl.DSL._
        val html = io.Source.fromResource("pages/divayina.html").getLines().mkString(" ")
        val browser: HtmlUnitBrowser = HtmlUnitBrowser.typed()
        browser.underlying.getOptions.setJavaScriptEnabled(false)
        val doc: HtmlUnitDocument = browser.parseString(html)
        val pText = doc >> allText("p")
        val dText = doc >> allText("div")
        val fText = doc >> allText("font")
        val txt = pText + " " + dText + " " + fText
        assert(txt != "")
  }

}
