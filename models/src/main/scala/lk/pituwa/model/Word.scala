package lk.pituwa.model

/**
  * Created by nayana on 28/7/17.
  */
case class Word(word: String, var id: Int = 0, var count: Int = 0)
case class Link(link: String, visited: Int = 0, var id: Int = 0,  hash: String = "")
case class InfoMap(word: Option[Word], link: Option[Link])
case class PdfDoc(id: Int = 0, hash: String)
//0d80 3456 - 0DFF 3583