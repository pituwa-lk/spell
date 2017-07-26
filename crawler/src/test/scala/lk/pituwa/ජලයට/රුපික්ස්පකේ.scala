package lk.pituwa.ජලයට

import net.ruippeixotog.scalascraper.browser.HtmlUnitBrowser
import org.scalatest.FlatSpec


/**
  * Created by nayana on 27/7/17.
  */
class රුපික්ස්පකේ extends FlatSpec {

  "crawler" should "return the body text of the page" in {
    val rupek = new lk.pituwa.ජලයට.රුපික්(lk.registry.registered.head)
    val output = rupek.බාලාකැපීම
    assert(output.nonEmpty)



  }
}


