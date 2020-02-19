package types

class Cell[+T](init: T) {
    def set[U >: T](x: U): Cell[U] = new Cell[U](x)
    def get[U >: T]: U = init
}

class BasicData {
    def shout: Unit = println("YOOO!")
}

class AdvancedData extends BasicData {
    def whisper: Unit = println("psst... yo...")
}

class DataClass(init: AdvancedData) extends Cell(init) {
    def set(x: BasicData): Cell[BasicData] = new Cell[BasicData](x)
}