# Specs2

### SBT installation
* go to page: https://etorreborre.github.io/specs2/guide/SPECS2-4.10.0/org.specs2.guide.Installation.html
```aidl

libraryDependencies += "org.specs2" %% "specs2-core" % "4.10.0" % "test"

scalacOptions in Test ++= Seq("-Yrangepos")
```
### Structure
* specs2 specifies two aspect, 1. a informal test describing the intended purpose and 2. scala code
* in functional testing, informal text and code are seperated
* in unit test, text and code are kept together 
* acceptance testing extends org.specs2.Specification
* unit testing extends org.specs2.mutable.Specification
* unit testing uses the >> operator to create “blocks” containing Texts and Examples
* The >> blocks can be nested 
* this allows you to structure your specification so that the outermost blocks describe a general context(Test suite) while the innermost ones describe more specific contexts(test case)
* functional testing encourage one expectation per example while unit testing can have several expectation per example
* with unit testing, you get “thrown expectations” by default. 
* When an expectation fails, it throws an exception, and the rest of the example is not executed.
```aidl
class MySpecification extends org.specs2.mutable.Specification {
  "This is my example" >> {
    1 must_== 2 // this fails
    1 must_== 1 // this is not executed
  }
}
```
### Matchers
* comparing the actual value with expected value is done using matchers
##### Equality matcher
refer : https://etorreborre.github.io/specs2/guide/SPECS2-4.10.0/org.specs2.guide.Matchers.html

```aidl
1 must beEqualTo(1)
1 must be_==(1)
1 must_==1
1 mustEqual 1
1 should_ ==1
1===1
1 must be equalTo(1)
```
##### String Matcher
```aidl
have length
have size
be empty
beEqualTo(b).ignoreCase
contains(b)
startsWith(s)
endsWith(s)
```
### Runners
##### using sbt
* Sbt recognizes specs2 as a “test framework”.
* any class extending Specification abstract class is recognized as test classes
* The test command will run all the specifications in your project
* test classes should be in the src/test/scala directory
```aidl
sbt> test
```
* only one test class can be executed using testOnly
* it also allow us to pass parameters
```aidl
sbt> testOnly org.acme.secret.KillerAppSpec
sbt> testOnly org.acme.secret.KillerAppSpec -- xonly
```
##### SBT options
* exclude some specifications: 
```
testOptions := Seq(Tests.Filter(s => Seq("Spec", "Unit").exists(s.endsWith)))
```
* Test result output could be in console, junitxml,html,markdown, notifier,printer
### Contexts
* **BeforeEach**: The org.specs2.specification.BeforeEach trait defines an action that will be executed before each example:
* **AfterEach**: execute after each test case
* **BeforeAfterEach**: You might also want to mix the two BeforeEach & AfterEach
* BeforeAll inserts a Step before all the examples
* AfterAll inserts a Step after all the examples
* BeforeAfterAll inserts one Step before all the examples and one Step after all of them
* **AroundEach** : execute some code in the context of a database transaction or a web request.
* **ForEach**
* Sometimes you need to create a specific context for each example but you also want to make it accessible to each example. 
* Here is a specification having examples using an active database transaction:
### Mokito
* Mockito allows us to specify stubbed values and verify that call has been made to your objects
* Need to extend the org.specs2.mock.Mockito trait
* creating a mock object
```aidl
val m = mock[java.util.List[String]]
val m = mock[List[String]].as("list1")
val m = mock[List[String]].verbose
val m = mock[List[String]].defaultReturn(10)
```
##### Stubbing
* Stubbing values is as simple as calling a method on the mock and declaring what should be returned or thrown:
```aidl
m.get(1) returns "one"
m.get(2) throws new RuntimeException("forbidden")

m.get(1) returns "one" thenReturns "two"
m.get(2) throws new RuntimeException("forbidden") thenReturns "999"
```
##### Mocking and Stubbing at the same time
```aidl
val mocked: java.util.List[String] = mock[java.util.List[String]].contains("o") returns true
mocked.contains("o") must beTrue
```
##### Verification
```aidl
there was one(m).get(0)              // one call only to get(0)
there was no(m).get(0)               // no calls to get(0)

// were can also be used
there were two(m).get(0)             // 2 calls exactly to get(0)
there were three(m).get(0)           // 3 calls exactly to get(0)
there were 4.times(m).get(0)         // 4 calls exactly to get(0)

there was atLeastOne(m).get(0)       // at least one call to get(0)
there was atLeastTwo(m).get(0)       // at least two calls to get(0)
there was atLeastThree(m).get(0)     // at least three calls to get(0)
there was atLeast(4)(m).get(0)       // at least four calls to get(0)

there was atMostOne(m).get(0)        // at most one call to get(0)
there was atMostTwo(m).get(0)        // at most two calls to get(0)
there was atMostThree(m).get(0)      // at most three calls to get(0)
there was atMost(4)(m).get(0)        // at most four calls to get(0)

// the combinators above, except `atMost`, can also be used with a timeout
there was after(10.millis).one(m).get(0)
there was after(2.seconds).two(m).get(0)
```
* It is also possible to add all verifications inside a block, when several mocks are involved:
```aidl
got {
  one(m).get(0)
  two(m).get(1)
}
```
##### Order of calls
* The order of method calls can be checked by creating an InOrder implicit and chaining calls with andThen:
```aidl
val m1 = mock[java.util.List[String]]

implicit val order = inOrder(m1)

m1.get(0)
m1.get(1)

there was one(m1).get(0) andThen one(m1).get(1)
```
* Several mocks can also be declared as having ordered calls:
```aidl
val m1 = mock[java.util.List[String]]
val m2 = mock[java.util.List[String]]
val m3 = mock[java.util.List[String]]

// here the order of mock objects doesn't matter
implicit val order = inOrder(m1, m3, m2)

m1.get(1); m2.get(2); m3.get(3)

there was one(m1).get(1) andThen one(m2).get(2) andThen one(m3).get(3)
```
##### Spies
* Spies can be used to do “partial mocking” of real objects:
```aidl
val spiedList = spy(new LinkedList[String])

// methods can be stubbed on a spy
spiedList.size returns 100

// other methods can also be used
spiedList.add("one")
spiedList.add("two")

// and verification can happen on a spy
there was one(spiedList).add("one")
```
