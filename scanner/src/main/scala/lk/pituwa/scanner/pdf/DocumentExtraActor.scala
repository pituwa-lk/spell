package lk.pituwa.scanner.pdf

import java.io.File

import akka.actor.Actor
import com.typesafe.scalalogging.Logger
import lk.pituwa.repository.WordRepository
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper

/**
  * Created by nayana on 29/7/17.
  */
object DocumentExtraActor {
  case class Peak()
}
class DocumentExtraActor extends Actor {

  lazy val fmMapper = List(
    "›" -> "ශ්‍රී",
    "ff;%" -> "ත්‍රෛ",
    "ffY" -> "ශෛ",
    "ffp" -> "චෛ",
    "ffc" -> "ජෛ",
    "ffk" -> "නෛ",
    "ffl" -> "කෛ",
    "ffu" -> "මෛ",
    "ffm" -> "පෛ",
    "ffo" -> "දෛ",
    "ff;" -> "තෛ",
    "ffO" -> "ධෛ",
    "ffj" -> "වෛ",
    "fm%!" -> "ප්‍රෞ",
    "fIHda" -> "ෂ්‍යෝ",
    "fPHda" -> "ඡ්‍යෝ",
    "fVHda" -> "ඪ්‍යෝ",
    "f>Hda" -> "ඝ්‍යෝ",
    "fLHda" -> "ඛ්‍යෝ",
    "f<Hda" -> "ළ්‍යෝ",
    "fMHda" -> "ඵ්‍යෝ",
    "fGHda" -> "ඨ්‍යෝ",
    "fYHda" -> "ශ්‍යෝ",
    "fCIHda" -> "ක්‍ෂ්‍යෝ",
    "fnHda" -> "බ්‍යෝ",
    "fpHda" -> "ච්‍යෝ",
    "fvHda" -> "ඩ්‍යෝ",
    "f\\*Hda" -> "ෆ්‍යෝ",
    "f.Hda" -> "ග්‍යෝ",
    "fcHda" -> "ජ්‍යෝ",
    "flHda" -> "ක්‍යෝ",
    "f,Hda" -> "ල්‍යෝ",
    "fuHda" -> "ම්‍යෝ",
    "fkHda" -> "න්‍යෝ",
    "fmHda" -> "ප්‍යෝ",
    "foHda" -> "ද්‍යෝ",
    "fiHda" -> "ස්‍යෝ",
    "fgHda" -> "ට්‍යෝ",
    "fjHda" -> "ව්‍යෝ",
    "f;Hda" -> "ත්‍යෝ",
    "fNHda" -> "භ්‍යෝ",
    "fOHda" -> "ධ්‍යෝ",
    "f:Hda" -> "ථ්‍යෝ",
    "fIHd" -> "ෂ්‍යො",
    "fYHd" -> "ශ්‍යො",
    "fLHd" -> "ඛ්‍යො",
    "fCIHd" -> "ක්‍ෂ්‍යො",
    "fnHd" -> "බ්‍යො",
    "fjHd" -> "ව්‍යො",
    "fvHd" -> "ඩ්‍යො",
    "f\\*Hd" -> "ෆ්‍යො",
    "f.Hd" -> "ග්‍යො",
    "fcHd" -> "ජ්‍යො",
    "flHd" -> "ක්‍යො",
    "fuHd" -> "ම්‍යො",
    "fmHd" -> "ප්‍යො",
    "foHd" -> "ද්‍යො",
    "fiHd" -> "ස්‍යො",
    "fgHd" -> "ට්‍යො",
    "fjHd" -> "ව්‍යො",
    "f;Hd" -> "ත්‍යො",
    "fNHd" -> "භ්‍යො",
    "fOHd" -> "ධ්‍යො",
    "f:Hd" -> "ථ්‍යො",
    "fIH" -> "ෂ්‍යෙ",
    "fPH" -> "ඡ්‍යෙ",
    "f<H" -> "ළ්‍යෙ",
    "fKH" -> "ණ්‍යෙ",
    "fpH" -> "ච්‍යෙ",
    "f,H" -> "ල්‍යෙ",
    "fkH" -> "න්‍යෙ",
    "fYH" -> "ශ්‍යෙ",
    "fLH" -> "ඛ්‍යෙ",
    "fCIH" -> "ක්‍ෂ්‍යෙ",
    "fnH" -> "බ්‍යෙ",
    "fvH" -> "ඩ්‍යෙ",
    "f\\*H" -> "ෆ්‍යෙ",
    "f.H" -> "ග්‍යෙ",
    "fcH" -> "ජ්‍යෙ",
    "flH" -> "ක්‍යෙ",
    "fuH" -> "ම්‍යෙ",
    "fmH" -> "ප්‍යෙ",
    "foH" -> "ද්‍යෙ",
    "fiH" -> "ස්‍යෙ",
    "fgH" -> "ට්‍යෙ",
    "fjH" -> "ව්‍යෙ",
    "f;H" -> "ත්‍යෙ",
    "fNH" -> "භ්‍යෙ",
    "fOH" -> "ධ්‍යෙ",
    "f:H" -> "ථ්‍යෙ",
    "hH_" -> "ර්ය",
    "fI%da" -> "ෂ්‍රෝ",
    "f>%da" -> "‍ඝ්‍රෝ",
    "fY%da" -> "ශ්‍රෝ",
    "fCI%da" -> "ක්‍ෂ්‍රෝ",
    "fn%da" -> "බ්‍රෝ",
    "fv%da" -> "ඩ්‍රෝ",
    "f\\*%da" -> "ෆ්‍රෝ",
    "f.%da" -> "ග්‍රෝ",
    "fl%da" -> "ක්‍රෝ",
    "fm%da" -> "ප්‍රෝ",
    "føda" -> "ද්‍රෝ",
    "fi%da" -> "ස්‍රෝ",
    "fg%da" -> "ට්‍රෝ",
    "f;%da" -> "ත්‍රෝ",
    "fY%d" -> "ශ්‍රො",
    "fv%d" -> "ඩ්‍රො",
    "f\\*%d" -> "ෆ්‍රො",
    "f.%d" -> "ග්‍රො",
    "fl%d" -> "ක්‍රො",
    "fm%d" -> "ප්‍රො",
    "fød" -> "ද්‍රො",
    "fi%d" -> "ස්‍රො",
    "fg%d" -> "ට්‍රො",
    "f;%d" -> "ත්‍රො",
    "%a" -> "a%",
    "fYa%" -> "ශ්‍රේ",
    "fí%" -> "බ්‍රේ",
    "fâ%" -> "ඩ්‍රේ",
    "f\\*%a" -> "ෆ්‍රේ",
    "f.%a" -> "ග්‍රේ",
    "fl%a" -> "ක්‍රේ",
    "fm%a" -> "ප්‍රේ",
    "føa" -> "ද්‍රේ",
    "fia%" -> "ස්‍රේ",
    "f;a%" -> "ත්‍රේ",
    "fè%" -> "ධ්‍රේ",
    "fI%" -> "ෂ්‍රෙ",
    "fY%" -> "ශ්‍රෙ",
    "fn%" -> "බ්‍රෙ",
    "f\\*%" -> "ෆ්‍රෙ",
    "f.%" -> "ග්‍රෙ",
    "fl%" -> "ක්‍රෙ",
    "fm%" -> "ප්‍රෙ",
    "fø" -> "ද්‍රෙ",
    "fi%" -> "ස්‍රෙ",
    "f;%" -> "ත්‍රෙ",
    "fN%" -> "භ්‍රෙ",
    "fO%" -> "ධ්‍රෙ",
    "fI!" -> "ෂෞ",
    "fP!" -> "ඡෞ",
    "fY!" -> "ශෞ",
    "fn!" -> "බෞ",
    "fp!" -> "චෞ",
    "fv!" -> "ඩෞ",
    "f\\*!" -> "ෆෞ",
    "f.!" -> "ගෞ",
    "fc!" -> "ජෞ",
    "fl!" -> "කෞ",
    "f,!" -> "ලෞ",
    "fu!" -> "මෞ",
    "fk!" -> "නෞ",
    "fm!" -> "පෞ",
    "fo!" -> "දෞ",
    "fr!" -> "රෞ",
    "fi!" -> "සෞ",
    "fg!" -> "ටෞ",
    "f;!" -> "තෞ",
    "fN!" -> "භෞ",
    "f\\[!" -> "ඤෞ",
    "fIda" -> "ෂෝ",
    "fUda" -> "ඹෝ",
    "fPda" -> "ඡෝ",
    "fVda" -> "ඪෝ",
    "f>da" -> "ඝෝ",
    "fLda" -> "ඛෝ",
    "f<da" -> "ළෝ",
    "f`yda" -> "ඟෝ",
    "fKda" -> "ණෝ",
    "fMda" -> "ඵෝ",
    "fGda" -> "ඨෝ",
    "f~da" -> "ඬෝ",
    "fYda" -> "ශෝ",
    "f\\{da" -> "ඥෝ",
    "fda" -> "ඳෝ",
    "fIda" -> "ෂෝ",
    "fnda" -> "බෝ",
    "fpda" -> "චෝ",
    "fvda" -> "ඩෝ",
    "f\\*da" -> "ෆෝ",
    "f.da" -> "ගෝ",
    "fyda" -> "හෝ",
    "fcda" -> "ජෝ",
    "flda" -> "කෝ",
    "f,da" -> "ලෝ",
    "fuda" -> "මෝ",
    "fkda" -> "නෝ",
    "fmda" -> "පෝ",
    "foda" -> "දෝ",
    "frda" -> "රෝ",
    "fida" -> "සෝ",
    "fgda" -> "ටෝ",
    "fjda" -> "වෝ",
    "f;da" -> "තෝ",
    "fNda" -> "භෝ",
    "fhda" -> "යෝ",
    "f\\[da" -> "ඤෝ",
    "fOda" -> "ධෝ",
    "f:da" -> "ථෝ",
    "fId" -> "ෂො",
    "fUd" -> "ඹො",
    "fPd" -> "ඡො",
    "fVd" -> "ඪො",
    "f>d" -> "ඝො",
    "fLd" -> "ඛො",
    "f<d" -> "ළො",
    "f`yd" -> "ඟො",
    "fKd" -> "ණො",
    "fMd" -> "ඵො",
    "fGd" -> "ඨො",
    "f`Vd" -> "ඬො",
    "fYd" -> "ශො",
    "f\\{d" -> "ඥො",
    "fd" -> "ඳො",
    "fId" -> "ෂො",
    "fnd" -> "බො",
    "fpd" -> "චො",
    "fvd" -> "ඩො",
    "f\\*d" -> "ෆො",
    "f.d" -> "ගො",
    "fyd" -> "හො",
    "fcd" -> "ජො",
    "fld" -> "කො",
    "f,d" -> "ලො",
    "fud" -> "මො",
    "fkd" -> "නො",
    "fmd" -> "පො",
    "fod" -> "දො",
    "frd" -> "රො",
    "fid" -> "සො",
    "fgd" -> "ටො",
    "fjd" -> "වො",
    "f;d" -> "තො",
    "fNd" -> "භො",
    "fhd" -> "යො",
    "f\\[d" -> "ඤො",
    "fOd" -> "ධො",
    "f:d" -> "ථො",
    "fIa" -> "ෂේ",
    "fò" -> "ඹේ",
    "fPa" -> "ඡේ",
    "fVa" -> "ඪේ",
    "f>a" -> "ඝේ",
    "fÄ" -> "ඛේ",
    "f<a" -> "ළේ",
    "f`Na" -> "ඟේ",
    "fKa" -> "ණේ",
    "fMa" -> "ඵේ",
    "fGa" -> "ඨේ",
    "fâ" -> "ඬේ",
    "fYa" -> "ශේ",
    "f\\{a" -> "ඥේ",
    "f|a" -> "ඳේ",
    "fËa" -> "ක්‍ෂේ",
    "fí" -> "බේ",
    "fÉ" -> "චේ",
    "fâ" -> "ඩේ",
    "f\\*a" -> "ෆේ",
    "f.a" -> "ගේ",
    "fya" -> "හේ",
    "fca" -> "ජේ",
    "fla" -> "කේ",
    "f,a" -> "ලේ",
    "fï" -> "මේ",
    "fka" -> "නේ",
    "fma" -> "පේ",
    "foa" -> "දේ",
    "f¾" -> "රේ",
    "fia" -> "සේ",
    "fÜ" -> "ටේ",
    "fõ" -> "වේ",
    "f;a" -> "තේ",
    "fNa" -> "භේ",
    "fha" -> "යේ",
    "f\\[a" -> "ඤේ",
    "fè" -> "ධේ",
    "f:a" -> "ථේ",
    "ta" -> "ඒ",
    "fÊ" -> "ජේ",
    "wE" -> "ඈ",
    "fI" -> "ෂෙ",
    "fU" -> "ඹෙ",
    "ft" -> "ඓ",
    "fP" -> "ඡෙ",
    "fV" -> "ඪෙ",
    "f>" -> "ඝෙ",
    "fL" -> "ඛෙ",
    "f<" -> "ළෙ",
    "f`y" -> "ඟෙ",
    "fK" -> "ණෙ",
    "fM" -> "ඵෙ",
    "fG" -> "ඨෙ",
    "f~" -> "ඬෙ",
    "fY" -> "ශෙ",
    "f\\{" -> "ඥෙ",
    "f|" -> "ඳෙ",
    "fCI" -> "ක්‍ෂෙ",
    "fn" -> "බෙ",
    "fp" -> "චෙ",
    "fv" -> "ඩෙ",
    "f\\*" -> "ෆෙ",
    "f." -> "ගෙ",
    "fy" -> "හෙ",
    "fc" -> "ජෙ",
    "fl" -> "කෙ",
    "f," -> "ලෙ",
    "fu" -> "මෙ",
    "fk" -> "නෙ",
    "fm" -> "පෙ",
    "fo" -> "දෙ",
    "fr" -> "රෙ",
    "fi" -> "සෙ",
    "fg" -> "ටෙ",
    "fj" -> "වෙ",
    "f;" -> "තෙ",
    "fN" -> "භෙ",
    "fh" -> "යෙ",
    "f\\[" -> "ඤෙ",
    "fO" -> "ධෙ",
    "f:" -> "ථෙ",
    "IDD" -> "ෂෲ",
    "YDD" -> "ශෲ",
    "nDD" -> "බෲ",
    "vDD" -> "ඩෲ",
    "\\*DD" -> "ෆෲ",
    ".DD" -> "ගෲ",
    "lDD" -> "කෲ",
    "mDD" -> "පෲ",
    "iDD" -> "සෲ",
    "gDD" -> "ටෲ",
    ";DD" -> "තෲ",
    "NDD" -> "භෲ",
    "ODD" -> "ධෲ",
    "Ï" -> "ඐ",
    "rE" -> "රූ",
    "W!" -> "ඌ",
    "T!" -> "ඖ",
    "Ï" -> "ඐ",
    "Æ" -> "ලූ",
    "re" -> "රු",
    "/" -> "රැ=",
    "ƒ" -> "ඳැ=",
    "/" -> "රැ",
    "R" -> "ඍ",
    "¨" -> "ලූ",
    "§" -> "දී",
    "ø" -> "ද්‍ර",
    "÷" -> "ඳු",
    "ÿ" -> "දු",
    "ü" -> "ඤූ=",
    "û" -> "ඤු=",
    "£" -> "ඳී",
    "°" -> "ඣී",
    "Á" -> "ඨී",
    "Â" -> "ඡී",
    "Ç" -> "ඛී",
    "Í" -> "රී",
    "Ð" -> "ඪී",
    "Ò" -> "ථී",
    "Ô" -> "ජී",
    "Ö" -> "චී",
    "Ú" -> "ඵී",
    "Ý" -> "ඵී",
    "à" -> "ටී",
    "ã" -> "ඞී",
    "é" -> "ඬී",
    "ë" -> "ධී",
    "î" -> "බී",
    "ó" -> "මී",
    "ö" -> "ඹී",
    "ù" -> "වී",
    "Ú" -> "ඵී",
    "Œ" -> "ණී",
    "B" -> "ඊ",
    "b" -> "ඉ",
    "¢" -> "ඳි",
    "È" -> "දි",
    "¯" -> "ඣි",
    "À" -> "ඨි",
    "Å" -> "ඛි",
    "È" -> "දි",
    "ß" -> "රි",
    "Î" -> "ඪි",
    "Ñ" -> "චි",
    "Ó" -> "ථි",
    "á" -> "ටි",
    "ä" -> "ඩි",
    "ç" -> "ඬි",
    "ê" -> "ධි",
    "ì" -> "බි",
    "ñ" -> "මි",
    "ð" -> "ජි",
    "ô" -> "ඹි",
    "ú" -> "වි",
    "Ý" -> "ඵි",
    "ˉ" -> "ඣි",
    "‚" -> "ණි",
    "þ" -> "ඡ්",
    "Ü" -> "ට්",
    "Ù" -> "ඕ",
    "õ" -> "ව්",
    "ò" -> "ඹ්",
    "ï" -> "ම්",
    "í" -> "බ්",
    "è" -> "ධ්",
    "å" -> "ඬ්",
    "â" -> "ඞ්",
    "Ü" -> "ට්",
    "Ù" -> "ඩ්",
    "´" -> " ඕ",
    "¾" -> "ර්",
    "Ä" -> "ඛ්",
    "É" -> "ච්",
    "Ê" -> "ජ්",
    "wd" -> "ආ",
    "we" -> "ඇ",
    "P" -> "ඡ=",
    "X" -> "ඞ",
    "r" -> "ර",
    "Ì" -> "ඏ",
    "“" -> " ර්‍ණ",
    "I" -> "ෂ",
    "U" -> "ඹ",
    "c" -> "ජ",
    "V" -> "ඪ",
    ">" -> "ඝ",
    "CO" -> "ඣ",
    "L" -> "ඛ",
    "<" -> "ළ",
    "`y" -> "ඟ",
    "K" -> "ණ",
    "M" -> "ඵ",
    "G" -> "ඨ",
    "¿" -> "ළු",
    "~" -> "ඬ",
    "Y" -> "ශ",
    "\\{" -> "ඥ",
    "|" -> "ඳ",
    "Ë" -> "ක්‍ෂ",
    "CI" -> "ක්‍ෂ",
    "®" -> "ඣ",
    "Õ" -> "ඟ",
    "×" -> "ඥ",
    "Ø" -> "ඤ",
    "Ì" -> "ඏ",
    "t" -> "එ",
    "w" -> "අ",
    "n" -> "බ",
    "p" -> "ච",
    "v" -> "ඩ",
    "M" -> "ඵ",
    "\\*" -> "ෆ",
    "." -> "ග",
    "y" -> "හ",
    "c" -> "ජ",
    "l" -> "ක",
    "," -> "ල",
    "u" -> "ම",
    "k" -> "න",
    "T" -> "ඔ",
    "m" -> "ප",
    "o" -> "ද",
    "r" -> "ර",
    "i" -> "ස",
    "g" -> "ට",
    "W" -> "උ",
    "j" -> "ව",
    ";" -> "ත",
    "N" -> "භ",
    "h" -> "ය",
    "\\[" -> "ඤ",
    "\\{" -> "ඥ",
    "|" -> "ඳ",
    "~" -> "ඬ",
    "O" -> "ධ",
    ":" -> "ථ",
    "…" -> "ත්‍ව",
    "‡" -> "න්‍ද",
    "†" -> "ත්‍ථ",
    "F" -> "ත්‍",
    "J" -> "න්‍",
    "C" -> "ක්‍",
    "Þ" -> "දා",
    "±" -> "දැ",
    "ˆ" -> "න්‍දා",
    "H" -> "්‍ය",
    "%" -> "‍්‍ර",
    "f" -> "ෙ",
    "e" -> "ැ",
    "E" -> "ෑ",
    "q" -> "ු",
    "s" -> "ි",
    "Q" -> "ූ",
    "=" -> "ු",
    "\\+" -> "ූ",
    "S" -> "ී",
    "D" -> "ෘ",
    "!" -> "ෟ",
    "d" -> "ා",
    "A" -> "්",
    "a" -> "්",
    "x" -> "ං",
    "½" -> "ඃ",
    " ’" -> "ී",
    " ‘" -> "ි",
    "#" -> "ඃ",
    "œ" -> " ර්‍්‍ය",
    "˜" -> "”",
    "—" -> "”",
    "™" -> "{",
    "∙" -> "×",
    "š" -> "}",
    "•" -> "x",
    "²" -> "•",
    "­" -> "÷",
    "¬" -> "+",
    "«" -> "×",
    "}" -> "=",
    "−" -> "÷",
    "" -> "□",
    "æ" -> "!",
    "$" -> "/",
    "&" -> ")",
    "\\(" -> ":",
    "\\)" -> "*",
    "-" -> "-",
    "@" -> "?",
    "Z" -> "’",
    "z" -> "‘",
    "]" -> "%",
    "^" -> "(",
    "&" -> ")",
    "Z" -> "’",
    "¡" -> "•",
    "¤" -> "–",
    "¦" -> ";",
    "³" -> "⋆",
    "µ" -> "i",
    "¶" -> "v",
    "•" -> "x",
    "¸" -> "I",
    "¹" -> "V",
    "º" -> "X",
    "º" -> "X",
    "¹" -> "V",
    "Ã" -> ".",
    "»" -> ",").toMap

  val logger = Logger("textActor")

  var busy = false

  override def receive: Receive = {
    case DocumentExtraActor.Peak() => {
        println("hello")
    }
  }


  def extract(file: File) = {
    logger.info("Start processing " + file.getName)
    try {
      val pdf = PDDocument.load(file)
      val endPage = pdf.getNumberOfPages
      val stripper = new PDFTextStripper
      var page = 3
      for (page <- 4 to endPage) {
        stripper.setStartPage(page)
        stripper.setEndPage(page)
        val txt = Some(stripper.getText(pdf))
        val revised = transform(txt.get)
        val potential = revised.split(" ").filter(_.matches("""^[\u0D80-\u0DFF\u200D]+$""")).toList
        logger.info(s"""Attempting to Add ${potential.size} of words""")
        potential.foreach(word => WordRepository.saveOne(word))
      }
      pdf.close()
      //WordRepository.save
    } catch {
      case t: Throwable =>
        t.printStackTrace
        None
    }
  }

  def transform(fmText: String): String = {
    lk.pituwa.utils.FileOps.writeOut(fmText, "/var/tmp/send.out")
    import sys.process._
    val current = "/usr/bin/nodejs src/main/fmTransform.js" !!

    lk.pituwa.utils.FileOps.readIn("/var/tmp/read.in")
  }
}
