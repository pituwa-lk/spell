package lk


import java.net.URL

import lk.pituwa.model.{Link, Request}

/**
  * Created by nayana on 26/7/17.
  */
class Registry
{
    lazy val registered:List[Request] = List(
      "https://si.wikipedia.org/wiki/%E0%B6%B8%E0%B7%94%E0%B6%BD%E0%B7%8A_%E0%B6%B4%E0%B7%92%E0%B6%A7%E0%B7%94%E0%B7%80"
    ).map(url => Request(url))
}

object Registry extends Registry
