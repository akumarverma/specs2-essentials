import org.specs2.mutable.Specification

class StringSpec extends Specification{

  val testString = "Hello World"

  "A String Test Suite" >> {
    "The length of testString should be" >> {
      testString must have length(11)
    }
    "test string should Starts with" >> {
      testString must startWith("Hello")
    }
    "test string should ends with" >> {
      testString must endWith("World")
    }

    "test string contains ello" >> {
      testString must contain("ello")
    }

    "test string equals" >> {
      testString must beEqualTo("Hello World").ignoreCase
    }

  }

}
