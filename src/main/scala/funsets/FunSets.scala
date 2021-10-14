package funsets

/**
 * 2. Purely Functional Sets.
 */
trait FunSets extends FunSetsInterface :
  /**
   * We represent a set by its characteristic function, i.e.
   * its `contains` predicate.
   */
  override type FunSet = Int => Boolean

  /**
   * Indicates whether a set contains a given element.
   */
  def contains(s: FunSet, elem: Int): Boolean = s(elem)

  /**
   * Returns the set of the one given element.
   */
  def singletonSet(elem: Int): FunSet = (x: Int) => x == elem

  def rangeSet(range: Range): FunSet = (x: Int) => range.contains(x)

  def iterationSet(vals: Int*): FunSet = (x: Int) => vals.contains(x)

  /**
   * Returns the union of the two given sets,
   * the sets of all elements that are in either `s` or `t`.
   */
  def union(s: FunSet, t: FunSet): FunSet = (x: Int) => contains(s, x) || contains(t, x)


  /**
   * Returns the intersection of the two given sets,
   * the set of all elements that are both in `s` and `t`.
   */
  def intersect(s: FunSet, t: FunSet): FunSet = (x: Int) => contains(s, x) && contains(t, x)

  /**
   * Returns the difference of the two given sets,
   * the set of all elements of `s` that are not in `t`.
   */
  def diff(s: FunSet, t: FunSet): FunSet = (x: Int) => contains(s, x) && !contains(t, x)

  /**
   * Returns the subset of `s` for which `p` holds.
   */
  def filter(s: FunSet, p: Int => Boolean): FunSet = (x: Int) => p(x) && contains(s, x)

  /**
   * The bounds for `forall` and `exists` are +/- 1000.
   */
  val bound = 1000

  import math.abs

  /**
   * Returns whether all bounded integers within `s` satisfy `p`.
   */
  def forall(s: FunSet, p: Int => Boolean): Boolean = {
    def iter(a: Int): Boolean =
      if contains(s, a) && !p(a) then false
      else if abs(a) > bound then true
      else iter(a - 1)

    iter(bound)
  }


  /**
   * Returns whether there exists a bounded integer within `s`
   * that satisfies `p`.
   */
  def exists(s: FunSet, p: Int => Boolean): Boolean = !forall(s, !p(_))

  /**
   * Returns a set transformed by applying `f` to each element of `s`.
   */
  def map(s: FunSet, f: Int => Int): FunSet = (x: Int) => exists(s, f(_) == x)

  /**
   * Displays the contents of a set
   */
  def toString(s: FunSet): String = (for i <- (-bound to bound) if contains(s, i) yield i).mkString("{", ",", "}")

  /**
   * Prints the contents of a set on the console.
   */
  def printSet(s: FunSet): Unit = println(toString(s))
end FunSets

object FunSets extends FunSets

