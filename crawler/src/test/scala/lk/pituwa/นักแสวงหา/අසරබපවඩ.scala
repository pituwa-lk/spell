package lk.pituwa.นักแสวงหา

import net.ruippeixotog.scalascraper.browser.HtmlUnitBrowser
import org.scalatest.FlatSpec

/**
  * Created by nayana on 26/7/17.
  */
class අසරබපවඩ extends FlatSpec
{
  "අසරැවා\\බාගත්\\" should "දත්ත\\හිස්\\නැතුව\\තිබිය\\යුතුය\\" in {
    val Cนักแสวงหา = new นักแสวงหา(lk.registry.registered.head)

    val typedBrowser: HtmlUnitBrowser = HtmlUnitBrowser.typed()
    typedBrowser.parseFile()

    assert(Cนักแสวงหา.การจับกุม.nonEmpty)

  }
}
