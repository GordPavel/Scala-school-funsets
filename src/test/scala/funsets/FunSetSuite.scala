package funsets

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite :

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   * val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets:
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * .ignore annotation.
   */
  test("singleton set one contains one") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets :
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
  }

  test("union contains all elements of each set") {
    new TestSets :
      val s = union(s1, s2)
      assert(contains(s, 1), "Test 1")
      assert(contains(s, 2), "Test 2")
      assert(!contains(s, 3), "Test 3")
  }

  test("intersect contains intersection elements of each set") {
    new TestSets :
      val oneAndTwo   = union(s1, s2)
      val twoAndThree = union(s2, s3)
      val two         = intersect(oneAndTwo, twoAndThree)
      assert(!contains(two, 1), "Test 1")
      assert(contains(two, 2), "Test 2")
      assert(!contains(two, 3), "Test 3")
  }

  test("diff contains elements from first without second") {
    new TestSets :
      val oneAndTwo   = union(s1, s2)
      val twoAndThree = union(s2, s3)
      val two         = diff(oneAndTwo, twoAndThree)
      assert(contains(two, 1), "Test 1")
      assert(!contains(two, 2), "Test 2")
      assert(!contains(two, 3), "Test 3")
  }

  test("filter contains elements from set satisfying predicate") {
    new TestSets :
      val oneAndTwo = union(s1, s2)
      val even      = filter(oneAndTwo, _ % 2 == 0)
      assert(!contains(even, 1), "Test 1")
      assert(contains(even, 2), "Test 2")
      assert(!contains(even, 3), "Test 3")
  }

  test("forAll returns true if all elements from set satisfy predicate") {
    new TestSets :
      val oneAndTwo = union(s1, s2)
      assert(!forall(oneAndTwo, _ % 2 == 0), "Not all in 1 and 2 are even")
      assert(forall(oneAndTwo, _ < 5), "All in 1 and 2 are less then 5")
  }

  test("Exists return if set contains value satisfying predicate") {
    new TestSets :
      val oneAndTwo = union(s1, s2)
      assert(exists(oneAndTwo, _ % 2 == 0), "2 is in set and even")
      assert(!exists(oneAndTwo, _ > 5), "No value which is greater then 5")
  }

  test("Map returns set with applyed function to each element") {
    new TestSets :
      val doubled1 = map(s1, _ * 2)
      assert(!contains(doubled1, 1), "Test 1")
      assert(contains(doubled1, 2), "Test 2")
      assert(!contains(doubled1, 3), "Test 3")
  }

  import scala.concurrent.duration.*

  override val munitTimeout = 10.seconds
