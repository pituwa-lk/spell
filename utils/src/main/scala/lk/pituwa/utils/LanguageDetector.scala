package lk.pituwa.utils

import com.cybozu.labs.langdetect.{Detector, DetectorFactory}

/**
  * Created by nayana on 27/7/17.
  */
  /**
    * Created by nhettiarachc on 6/16/17.
    */
object LanguageDetector {

  def profilePath = "utils/resources/lang"

  //lazy val detector = new DetectorFactory.create

  lazy val detector:Detector  = {
    DetectorFactory.loadProfile(profilePath)
    DetectorFactory.create()
  }

  def calculate(text: String):String = {
    detector.append(text)
    detector.detect
  }
}

