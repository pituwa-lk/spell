package lk.pituwa.model

import com.netaporter.uri.Uri.parse


/**
  * Created by nayana on 27/7/17.
  */
case class Request(uri: Link) {

  lazy val url = parse(uri.link)

  lazy val prefix:String = url.scheme.get + "://" + url.hostParts.mkString(".")
}

case class Response(request: Request, body: String)
case class Document(response: Response, links: List[Link], infoMap: List[InfoMap])