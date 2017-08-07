package lk.pituwa.service

import com.rockymadden.stringmetric.similarity.{DiceSorensenMetric, JaroWinklerMetric, LevenshteinMetric}
import lk.pituwa.repository.WordRepository

import scala.concurrent.Future

/**
  * Created by nayana on 28/7/17.
  */
object WordService {

  lazy val wordTree: Map[String, List[String]] = {
    WordRepository.get.map(word => {
      val prefix = word.substring(0, nGramSize)
      prefix -> List(word)
    }).toMap
  }

  val nGramSize = 2

  def diceSorensenMetric(p1: String):(String, List[String]) = {
    val prefix = p1.substring(0, nGramSize)
    val tree = wordTree.get(prefix) match {
      case Some(v) => v
      case None => throw new Exception("subtree not found")
    }
    p1 -> tree.map(word => word -> (DiceSorensenMetric(1).compare(p1, word) match  {
      case Some(i) => i
      case None => 0.00
    })
    ).sortBy(_._2).slice(0, 10).map(_._1)
  }

  def jaroWinklerMap(p1: String):(String, List[String]) = {
    val prefix = p1.substring(0, nGramSize)
    val tree = wordTree.get(prefix) match {
      case Some(v) => v
      case None => throw new Exception("subtree not found")
    }
    p1 -> tree.map(word => word -> (JaroWinklerMetric.compare(p1, word) match  {
      case Some(i) => i
      case None => 0.00
    })
    ).sortBy(_._2).slice(0, 10).map(_._1)
  }

  def levenshteinMap(p1: String):(String, List[String]) = {
    val prefix = p1.substring(0, nGramSize)
    val tree = wordTree.get(prefix) match {
      case Some(v) => v
      case None => throw new Exception("subtree not found")
    }
    p1 -> tree.map(word => word -> (LevenshteinMetric.compare(p1, word) match  {
      case Some(i) => i
      case None => 9999
    })
    ).sortBy(_._2).slice(0, 10).map(_._1)
  }

  def isFound(word: String):Boolean = {
    val prefix = word.substring(0, nGramSize)
    val tree = wordTree.get(prefix) match {
      case Some(v) => v
      case None => throw new Exception("indexed miss matched")
    }
    tree.contains(word)
  }

  def isNotFound(word: String) = !isFound(word)

  def sanitize(input: String): String = input.replaceAll("\\?|\\.|\\,|\\!", " ")

  def spellCheck(document: String): Future[Map[String, List[String]]] =  {
    val words = sanitize(document.trim()).split(" ").distinct
    val potential = words.filter(isNotFound).toList
    val fLeven = Future(potential.map(levenshteinMap).toMap)
    val fJaro  = Future(potential.map(jaroWinklerMap).toMap)
    val fDice  = Future(potential.map(diceSorensenMetric).toMap)

    fLeven.flatMap(v => {
        fJaro.flatMap(j => {
            fDice.map(d => {
                 potential.map(p => {
                   p -> d(p).intersect(j.(p).intersect(v.(p)))
                 }).toMap
            })
        })
    })
  }

  def lookup(word: String): Map[String, List[String]] = {
    val lev = levenshteinMap(word)
    val jaro = jaroWinklerMap(word)
    val dice = diceSorensenMetric(word)
    List(word -> lev._2.intersect(jaro._2.intersect(dice._2))).toMap
  }

  def score(p1: String, p2: String, matched: Int = 0):Int = {
    if (p1.length == 0 || p2.length == 0) matched
    else {
      p1.charAt(0) == p2.charAt(0) match {
        case true =>
          if (p1.length > 1 && p2.length > 1) {
            score(p1.substring(1), p2.substring(1), matched + 1)
          } else {
            matched + 1
          }
        case false => matched
      }
    }
  }
}
