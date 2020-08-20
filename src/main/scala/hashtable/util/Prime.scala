package hashtable.util

object Prime {
  def isPrime(number: Int): Boolean = {
    if (number < 2) return false
    if (number < 4) return true
    if (number % 2 == 0) return false

    var i = 3
    while (i <= math.floor(math.sqrt(number))) {
      if (number % i == 0) return false
      i += 2
    }
    return true
  }

  def nextPrime(number: Int): Int = {
    var n = if (number % 2 == 0) (number + 1) else (number + 2)
    while (!isPrime(n)) {
      n += 2
    }
    n
  }
}
