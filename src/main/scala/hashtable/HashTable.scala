package hashtable

trait HashTable {
  def contains(key: String): Boolean
  def get(key: String): Option[String]
  def insert(key: String, value: String): Unit
  def remove(key: String): Unit
  def size(): Int
}
