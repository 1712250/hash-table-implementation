package hashtable

import hashtable.hashfunc._
import hashtable.util._

import scala.collection.mutable._
import scala.util.control.Breaks._

class SeparateChaining(
    private var initialCapacity: Int = 8,
    private val hashFunc: HashFunc = new BasePrimeHash(151)
) extends HashTable {
  private var count = 0
  private var filledBucket = 0
  private var capacity = Prime.nextPrime(initialCapacity)
  private var hashtable = Array
    .fill[ListBuffer[Tuple2[String, String]]](capacity)(elem = new ListBuffer)

  def contains(key: String): Boolean = {
    val index = hashFunc.getHash(key, capacity)
    val list = hashtable(index)
    for (item <- list) {
      if (item._1.compareTo(key) == 0)
        return true
    }
    false
  }

  def get(key: String): Option[String] = {
    val index = hashFunc.getHash(key, capacity)
    val list = hashtable(index)
    for (item <- list) {
      if (item._1.compareTo(key) == 0)
        return Some(item._2)
    }
    None
  }

  def insert(key: String, value: String): Unit = {
    val list = hashtable(hashFunc.getHash(key, capacity))
    var index = -1

    var i = 0
    breakable {
      for (item <- list) {
        if (item._1.compareTo(key) == 0) {
          index = i
          break
        }
        i += 1
      }
    }

    if (index == -1) {
      count += 1
      if (list.isEmpty) filledBucket += 1
      list += (key -> value)
    } else {
      list.update(index, (key -> value))
    }

    if (filledBucket * 100 / capacity > 70)
      resizeUp
  }

  def remove(key: String): Unit = {
    val list = hashtable(hashFunc.getHash(key, capacity))
    var i = 0
    for (item <- list) {
      if (item._1.compareTo(key) == 0) {
        list.remove(i)
        count -= 1
        if (list.isEmpty) filledBucket -= 1
        if (filledBucket * 100 / capacity < 10)
          resizeDown
      }
      i += 1
    }
  }

  def size(): Int = {
    return count
  }

  private def resize(newCapacity: Int): Unit = {
    if (newCapacity < 8) return
    initialCapacity = newCapacity
    capacity = Prime.nextPrime(newCapacity)
    count = 0
    filledBucket = 0

    val oldHT = hashtable
    hashtable = Array
      .fill[ListBuffer[Tuple2[String, String]]](capacity)(elem = new ListBuffer)
    for (list <- oldHT) {
      for (item <- list)
        insert(item._1, item._2)
    }
  }

  private def resizeUp: Unit = {
    resize(initialCapacity * 2)
  }

  private def resizeDown: Unit = {
    resize(initialCapacity / 2)
  }
}
