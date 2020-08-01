package xml

import scala.xml

object Example {
  val a = <p>
            This is some XML.
            Here is a tag: <atag/>
          </p>
  
  val yearMade = 1955
  val old_example = 
  <a>
  {
    if (yearMade < 2000) <old>{yearMade}</old>
    else xml.NodeSeq.Empty
  }
  </a>

  val potential_secutiy_hole = <a> {"<a>Potential security hole</a>"} </a>

  val braces = <a> {{this is inside single braces}} </a>

  val a_text = a.text
  val encoded_text = potential_secutiy_hole.text

  val nested = <a><b><c>hello</c></b></a>

  val find = nested \ "b" // NodeSeq(<b><c>hello</c></b>)
  val not_found_deep = nested \ "c" //  NodeSeq()
  val found_deep = nested \\ "c" // NodeSeq(<c>hello</c>)
  val parent = nested \ "a" // NodeSeq()
  val real_parent = nested \\ "a" // NodeSeq(<a><b><c>hello</c></b></a>)

  val joe = <employee name="Joe" rank="code monkey" serial="123"/>
  val jname = joe \ "@name"

  val therm = new CCTherm {
    val description = "hot dog #5"
    val yearMade = 1952
    val dateObtained = "March 14, 2006"
    val bookPrice = 2199
    val purchasePrice = 500
    val condition = 9
  }
  
  def proc(node: scala.xml.Node): String = 
    node match {
      case <a>{contents}</a> => "It's an a: " + contents
      case <b>{contents}</b> => "It's a b: " + contents
      case _ => "It's something else"
    }
  
  def proc2(node: scala.xml.Node): String = 
    node match {
      case <a>{contents @ _*}</a> => "It's an a:" + contents
      case <b>{contents @ _*}</b> => "It's a b:" + contents
      case _ => "It's something else"
    }
  
  val catalog =
    <catalog>
      <cctherm>
        <description>hot dog #5</description>
        <yearMade>1952</yearMade>
        <dateObtained>March 14, 2006</dateObtained>
        <bookPrice>2199</bookPrice>
        <purchasePrice>500</purchasePrice>
        <condition>9</condition>
      </cctherm>
      <cctherm>
        <description>Sprite Boy</description>
        <yearMade>1964</yearMade>
        <dateObtained>April 28, 2003</dateObtained>
        <bookPrice>1695</bookPrice>
        <purchasePrice>595</purchasePrice>
        <condition>5</condition>
      </cctherm>
    </catalog>

  def run(): Unit = {
    catalog match {
      case <catalog>{therms @ _*}</catalog> =>
        for (therm @ <cctherm>{_*}</cctherm> <- therms)
          println("processing: " + (therm \ "description").text)
    }
  }
}

abstract class CCTherm {
  val description: String
  val yearMade: Int
  val dateObtained: String
  val bookPrice: Int // in US cents
  val purchasePrice: Int // in US cents
  val condition: Int // 1 to 10

  override def toString = description

  def toXML = 
    <cctherm>
      <description>{description}</description>
      <yearMade>{yearMade}</yearMade>
      <dateObtained>{dateObtained}</dateObtained>
      <bookPrice>{bookPrice}</bookPrice>
      <purchasePrice>{purchasePrice}</purchasePrice>
      <condition>{condition}</condition>
    </cctherm>

  def save(): Unit = scala.xml.XML.save("therm.xml", toXML)
}

object CCTherm {
  def fromXML(node: scala.xml.Node): CCTherm = 
    new CCTherm {
      val description: String = (node \ "description").text
      val yearMade: Int = (node \ "yearMade").text.toInt
      val bookPrice: Int = (node \ "bookPrice").text.toInt
      val condition: Int = (node \ "condition").text.toInt
      val dateObtained: String = (node \ "dateObtained").text
      val purchasePrice: Int = (node \ "purchasePrice").text.toInt
    }
  
  def load(file_name: String): CCTherm =
    fromXML(scala.xml.XML.loadFile(file_name))
}