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