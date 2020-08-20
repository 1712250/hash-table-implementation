package hashtable.hashfunc

class BasePrimeHash(private val prime: Int = 151) extends HashFunc {
  def getHash(key: String, capacity: Int): Int = {
    var hash = 0
    for (c <- key) {
      hash = (hash * prime + c.toInt) % capacity
    }
    hash
  }

  def getName(): String = "Base Prime"
}
