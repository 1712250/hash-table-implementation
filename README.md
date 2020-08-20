# Hash Table

An implementation of Hash Table in Scala.

## Hash Table Structure

A Hash Table usually consists of multiple buckets, stored in an array for the purpose of randomly access, and a set of hash functions.

When adding a key-value pair to the hash table, the key will be hashed, the result of this operation is its index in the array. The key-value pair is then stored in the array at that index.

## Collisions

What happens when multiple key has the same hash? Collision!

There's multiple way of handling collisions. Here are some of those:

- [Double Hashing](/src/scala/hashtable/DoubleHashing.scala): Assume we want to add the `(k,v)` pair to the hash table, `h = hash(k)` and the bucket `h` is occupied by another pair.

  - Because the bucket `h` is already filled, we need to stored our pair in another bucket.
  - Here, we calculate that bucket by this formula: `h = h + i * hash2(k)`, where `hash2` is another hash function, `i` is the number of times we need to find another bucket.
  - While bucket `h` is still occupied, we calculate next h using the above formula, until an empty bucket found.

- [Quadratic Probing](/src/scala/hashtable/QuadraticProbing.scala): Same as `Double Hashing`, but the next bucket is `h, h + 1, h + 4, ..., h + i*i`.

- [Separate Chaining](/src/scala/hashtable/SeparateChaining.scala): Each bucket is a linked list, those key having the same hash are added to the same list.
