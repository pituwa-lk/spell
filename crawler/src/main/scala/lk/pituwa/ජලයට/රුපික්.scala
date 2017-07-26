package lk.pituwa.ජලයට

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._


/**
  * Created by nayana on 27/7/17.
  */
class රුපික්(අඩවිලිපිණය:String) {

  def බාලාකැපීම: String = {
    val browser  = JsoupBrowser()
    val doc = browser.get(අඩවිලිපිණය)
    doc >> text("body")
  }
}
