package utils

import lk.pituwa.utils.LanguageDetector
import org.scalatest.FlatSpec

/**
  * Created by nayana on 27/7/17.
  */
  /**
    * Created by nhettiarachc on 6/16/17.
    */
class LanguageDetectorSpec extends FlatSpec {

    "english text" should "provide english result " in {
      val lang = LanguageDetector.calculate("some english text for detection purpose thand ehte hrfuer")
      assert(lang === "en")
    }

    "sinhala text" should "provide sinhala result " in {
      val lang = LanguageDetector.calculate("අ ද ද ේහරයි ටඉබටරඑට යැටැරමසටයරැචසමටයැචුසද රයටැ අරදට")
      assert(lang === "si")
    }
}

