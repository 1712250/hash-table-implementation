package hashtable

import hashtable.hashfunc._
import hashtable.util._

class DoubleHashing(
    private var initialCapacity: Int = 8,
    private val hashFunc1: HashFunc = new BasePrimeHash(151),
    private val hashFunc2: HashFunc = new BasePrimeHash(163)
) extends HashTable {
  private val DELETED_ITEM = ("deteted" -> "item")
  private var count = 0
  private var capacity = Prime.nextPrime(initialCapacity)
  private var hashtable = new Array[Tuple2[String, String]](capacity)

  def contains(key: String): Boolean = {
    var attempt = 0
    var index = getHash(key, attempt)
    var item = hashtable(index)
    while (item != null) {
      if (item.ne(DELETED_ITEM) && item._1.compareTo(key) == 0)
        return true
      attempt += 1
      index = getHash(key, attempt)
      item = hashtable(index)
    }
    false
  }

  def get(key: String): Option[String] = {
    var attempt = 0
    var index = getHash(key, attempt)
    var item = hashtable(index)
    while (item != null) {
      if (item.ne(DELETED_ITEM) && item._1.compareTo(key) == 0)
        return Some(item._2)
      attempt += 1
      index = getHash(key, attempt)
      item = hashtable(index)
    }
    None
  }

  def insert(key: String, value: String): Unit = {
    var attempt = 0
    var index = getHash(key, attempt)
    var item = hashtable(index)
    while (
      item != null && item.ne(DELETED_ITEM) && item._1.compareTo(key) != 0
    ) {
      attempt += 1
      index = getHash(key, attempt)
      item = hashtable(index)
    }
    if (item == null || item.eq(DELETED_ITEM))
      count += 1
    hashtable(index) = (key -> value)

    if (count * 100 / capacity > 70)
      resizeUp
  }

  def remove(key: String): Unit = {
    var attempt = 0
    var index = getHash(key, attempt)
    var item = hashtable(index)
    while (item != null) {
      if (item.ne(DELETED_ITEM) && item._1.compareTo(key) == 0) {
        hashtable(index) = DELETED_ITEM
        count -= 1

        if (count * 100 / capacity < 10)
          resizeDown
        return
      }
      attempt += 1
      index = getHash(key, attempt)
      item = hashtable(index)
    }
  }

  def size(): Int = {
    return count
  }

  private def getHash(key: String, attempt: Int): Int = {
    val hash1 = hashFunc1.getHash(key, capacity)
    val hash2 = hashFunc2.getHash(key, capacity)
    (hash1 + (attempt * (hash2 + 1))) % capacity
  }

  private def resize(newCapacity: Int): Unit = {
    if (newCapacity < 8) return
    initialCapacity = newCapacity
    capacity = Prime.nextPrime(newCapacity)
    count = 0

    val oldHT = hashtable
    hashtable = new Array[Tuple2[String, String]](capacity)
    for (item <- oldHT) {
      if (item != null && item.ne(DELETED_ITEM)) {
        insert(item._1, item._2)
      }
    }
  }

  private def resizeUp: Unit = {
    resize(initialCapacity * 2)
  }

  private def resizeDown: Unit = {
    resize(initialCapacity / 2)
  }
}
