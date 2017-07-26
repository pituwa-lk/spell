package lk.pituwa.model

import com.netaporter.uri.Uri._


/**
  * Created by nayana on 27/7/17.
  */
case class Request(uri: String) {

  lazy val url = parse(uri)

  lazy val prefix:String = url.scheme.get + "://" + url.hostParts.mkString(".")
}

case class Response(request: Request, body: String)
case class Document(response: Response, texts: List[String], links: List[String])