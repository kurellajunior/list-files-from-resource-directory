package org.kurella.jar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

/**
 */
class SpecReaderTest extends AnyFunSuite with Matchers {

  test("test ReadSpecMessage A Java") {
    new SpecReader("A").readSpecMessage() shouldEqual "This will add nicely to a long test message!"
  }

  test("test ReadSpecMessage B Java") {
    new SpecReader("B").readSpecMessage() shouldEqual "Yet another message to read for the upcoming test."
  }

  test("test ReadSpecMessage A Scala") {
    new SpecReader("A").readSpecMessage() shouldEqual "This will add nicely to a long test message!"
  }

  test("test ReadSpecMessage B Scala") {
    new SpecReader("B").readSpecMessage() shouldEqual "Yet another message to read for the upcoming test."
  }

}
