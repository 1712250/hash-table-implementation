import hashtable._
import scala.util.control.Breaks._

object Main {
  val ht: HashTable = new QuadraticProbing

  def main(args: Array[String]): Unit = {
    interactive()
  }

  def interactive() {
    println("*****************************************")
    println("*********   INTERACTIVE SHELL   *********")
    println("*****************************************")
    breakable(
      while (true) {
        print("> ")
        val word = readLine().trim
        val arr = word.split(" ")
        arr(0) match {
          case "exit" => break
          case "h"    =>
          case _      =>
        }

        breakable {
          arr(0) match {
            case "size" => {
              benchmark(() => {
                print(s"Hash table current size: ${ht.size}")
              })
              break
            }
            case _ =>
          }

          if (arr.length < 2) {
            println("Incorrect syntax!")
            break
          }

          arr(0) match {
            case "i" =>
              if (arr.length < 3) {
                println("Incorrect syntax!")
                break
              }
              benchmark(() => {
                ht.insert(arr(1), arr(2))
                print("Inserted")
              })
            case "rm" =>
              benchmark(() => {
                ht.remove(arr(1))
                print("Removed")
              })
            case "c" =>
              benchmark(() => {
                if (ht.contains(arr(1))) print("Key is exist!")
                else print("Key is not exist!")
              })
            case "s" =>
              benchmark(() => {
                ht.get(arr(1)) match {
                  case Some(str) => print(s"Value: $str")
                  case None      => print("Key not found!")
                }
              })
            case s => println(s"Unknown command: $s")
          }
        }
      }
    )
  }

  def benchmark(f: () => Unit) {
    val t = System.nanoTime
    f()
    val duration = (System.nanoTime - t) / 1e6d
    println(s"\t -- in $duration milisecond(s)")
  }
}
