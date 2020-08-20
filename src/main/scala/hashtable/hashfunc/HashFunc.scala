package hashtable.hashfunc

trait HashFunc {
  def getHash(key: String, capacity: Int): Int
  def getName(): String
}
