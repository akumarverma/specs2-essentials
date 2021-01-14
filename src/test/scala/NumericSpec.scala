import org.specs2.mutable.Specification

class NumericSpec extends Specification{

  "A numeric test suite" should {

    "The number should be less than or equal to 5" in {
      3 must be_<=(5)
    }

    "The number should be greater than" in {
      10 must be_>(5)
    }

    "The number should be greater than or equal to 5" in {
      10 must be_>=(10)
    }

    "The number should be equal to 5" in {
      5 must be_==(5)
    }
  }

}
