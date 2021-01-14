import org.specs2.mutable.Specification

class CollectionSpec extends Specification{


  "A list test suite" should {
    "Length of list should be 3" >> {
      List(1,2,3) must have length (3)
    }
    "Length of list should be 0" >> {
      List() must be empty
    }
    "Length contains 1 and 2" >> {
      List(1,2,3) must contain(1,2)
    }
    "All list element should be greater than 0" >> {
      List(1,2,3) must contain(be_>=(0))
    }
    "at Least one element should be greater than 4" in {
      List(2,4,5) must contain(be_>=(4)).atLeastOnce
    }

    "All element should be greater than 2" in {
      List(2,4,5) must contain(be_>=(2)).forall
    }
  }

}
