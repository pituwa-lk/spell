package lk.pituwa.service

import com.rockymadden.stringmetric.similarity.{DiceSorensenMetric, JaroWinklerMetric, LevenshteinMetric}
import com.typesafe.scalalogging.Logger
import lk.pituwa.repository.WordRepository

import scala.concurrent.Future

/**
  * Created by nayana on 28/7/17.
  */
object WordService {

  val nGramSize = 2

  val logger = Logger("wordService")

  var alpaTree = scala.collection.mutable.Map[String, List[String]]().withDefaultValue(List())

  var betaTree = scala.collection.mutable.Map[String, List[String]]().withDefaultValue(List())

  var errors = scala.collection.mutable.ArrayStack[String]()

  lazy val wordTree = betaTree

  lazy val charTree = alpaTree

  WordRepository.get.filter(_.length >= 2).foreach(word => {
    val chr = word.substring(0, 1)
    val prefix = word.substring(0, 2)
    if (chr.matches("\u200D")) errors.push(word)
    alpaTree.update(chr, alpaTree(chr) :+ word)
    betaTree.update(prefix, betaTree(prefix) :+ word)
  })

  lazy val alphaStats = {
    alpaTree.keys.map(key =>  key -> alpaTree(key).size).toSeq.sortBy(- _._2)
  }

  lazy val betaStats = {
    betaTree.keys.map(key =>  key -> alpaTree(key).size).toSeq.sortBy(- _._2)
  }

  def diceSorensenMetric(p1: String):List[String] = {
    val prefix = p1.substring(0, nGramSize)
    val chr = p1.substring(0, 1)
    val tree = wordTree.get(prefix) match {
      case Some(v) => v
      case None => List("vv")
    }
    val aTree = alpaTree.get(chr) match {
      case Some(v) => v
      case None => List("vv")
    }
    (tree.map(word => word -> (DiceSorensenMetric(1).compare(p1, word) match  {
      case Some(i) => i
      case None => 0.00
    })
    ).sortBy(- _._2).slice(0, 10).map(_._1) ++ aTree.map(word => word -> (DiceSorensenMetric(1).compare(p1, word) match  {
      case Some(i) => i
      case None => 0.00
    })
    ).sortBy(- _._2).slice(0, 10).map(_._1)).distinct
  }

  def jaroWinklerMap(p1: String):List[String] = {
    val prefix = p1.substring(0, nGramSize)
    val chr = p1.substring(0, 1)
    val tree = wordTree.get(prefix) match {
      case Some(v) => v
      case None => List("vv")
    }
    val aTree = alpaTree.get(chr) match {
      case Some(v) => v
      case None => List("vv")
    }
    (tree.map(word => word -> (JaroWinklerMetric.compare(p1, word) match  {
      case Some(i) => i
      case None => 0.00
    })
    ).sortBy(- _._2).slice(0, 10).map(_._1) ++ aTree.map(word => word -> (JaroWinklerMetric.compare(p1, word) match  {
      case Some(i) => i
      case None => 0.00
    })
    ).sortBy(- _._2).slice(0, 10).map(_._1)).distinct
  }

  def levenshteinMap(p1: String):List[String] = {
    val prefix = p1.substring(0, nGramSize)
    val chr = p1.substring(0, 1)
    val tree = wordTree.get(prefix) match {
      case Some(v) => v
      case None => List("vv")
    }
    val aTree = alpaTree.get(chr) match {
      case Some(v) => v
      case None => List("vv")
    }
    (tree.map(word => word -> (LevenshteinMetric.compare(p1, word) match  {
      case Some(i) => i
      case None => 9999
    })
    ).sortBy(_._2).slice(0, 10).map(_._1) ++ aTree.map(word => word -> (LevenshteinMetric.compare(p1, word) match  {
      case Some(i) => i
      case None => 9999
    })
    ).sortBy(_._2).slice(0, 10).map(_._1)).distinct
  }

  def isFound(word: String):Boolean = {
    if (word.length >= nGramSize) {
      val prefix = word.substring(0, nGramSize)
      val tree = wordTree.get(prefix) match {
        case Some(v) => v
        case None => List("vv")
      }
      tree.contains(word)
    } else {
      val tree = alpaTree.get(word) match {
        case Some(v) => v
        case None => List("vv")
      }
      tree.contains(word)
    }
  }

  def isNotFound(word: String) = !isFound(word)

  def sanitize(input: String): String = {
    logger.info("received: {}", input)
    println(input)
    input.replaceAll("\\?|\\.|\\,|\\!|&nbsp;|\\u00a0", " ")
  }

  def spellCheck(document: String): Future[Map[String, List[String]]] =  {
    import scala.concurrent.ExecutionContext.Implicits.global
    val words = sanitize(document).trim().split(" ").map(v => v.trim).distinct.filter(_.length > 2)
    val potential = words.filter(_.nonEmpty).filter(isNotFound).toList
    val fLeven = Future(potential.map(word => word -> levenshteinMap(word)).toMap)
    val fJaro  = Future(potential.map(word => word -> jaroWinklerMap(word)).toMap)
    val fDice  = Future(potential.map(word => word -> diceSorensenMetric(word)).toMap)

    fLeven.flatMap(v => {
        fJaro.flatMap(j => {
            fDice.map(d => {
                 potential.map(p => {
                   p -> d(p).intersect(j(p).intersect(v(p)))
                 }).toMap
            })
        })
    })
  }

  def lookup(word: String): Map[String, List[String]] = {
    val lev = levenshteinMap(word)
    val jaro = jaroWinklerMap(word)
    val dice = diceSorensenMetric(word)
    List(word -> lev.intersect(jaro.intersect(dice))).toMap
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
