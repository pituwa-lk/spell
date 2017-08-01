package lk.pituwa.utils

import java.io.{BufferedWriter, File, FileWriter}

/**
  * Created by nayana on 29/7/17.
  */
object FileOps {

  def readIn(file: String): String = io.Source.fromFile(file, "utf-8").getLines().mkString("")


  def writeOut(text: String, f: String) = {
    val file = new File(f)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(text)
    bw.close()
  }

  import java.security.{MessageDigest, DigestInputStream}
  import java.io.{File, FileInputStream}

  def md5String(text: String): String = {
    import java.security.MessageDigest
    val bytesOfMessage = text.getBytes("UTF-8")

    val md = MessageDigest.getInstance("MD5")
    md.digest(bytesOfMessage).map("%02x".format(_)).mkString
  }

  // Compute a hash of a file
  // The output of this function should match the output of running "md5 -q <file>"
  def computeHash(path: String): String = {
    val buffer = new Array[Byte](8192)
    val md5 = MessageDigest.getInstance("MD5")
    val dis = new DigestInputStream(new FileInputStream(new File(path)), md5)
    try { while (dis.read(buffer) != -1) { } } finally { dis.close() }

    md5.digest.map("%02x".format(_)).mkString
  }
}
