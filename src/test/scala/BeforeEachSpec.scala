import org.specs2.mutable.Specification
import org.specs2.specification.{BeforeAfterAll, BeforeAfterEach, BeforeEach}

class BeforeEachSpec extends Specification with BeforeAfterEach with BeforeAfterAll {

  def before() = println("before")
  def after() = println("after")
  def beforeAll()= println("before All")
  def afterAll() = println("after All")

  "A context test suite" should {
    "i should be equal to" in{
      1 must be_==(1)
    }
    "5 should be greater than 2" in{
      5 must be_>=(2)
    }

  }
}
