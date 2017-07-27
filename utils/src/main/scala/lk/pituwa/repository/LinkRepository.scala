package lk.pituwa.repository

/**
  * Created by nayana on 27/7/17.
  */
object LinkRepository
{
    var links: List[String] = List()

    def add(link: String): Unit = links += link
    def get: String = {
      val ret = links.head
      links = links.tail
      ret
    }
    def empty: Boolean = links.isEmpty

    def bulkAdd(list: List[String]):Unit = links = links ::: list
}
