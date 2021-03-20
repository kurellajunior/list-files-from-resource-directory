package org.kurella.jar

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

/**
 */
class SpecReaderTest extends AnyFunSuite with Matchers {

  test("test ReadSpecMessage A") {
    new SpecReader().readSpecMessage("A") shouldEqual "This will add nicely to a long test message!"
  }

  test("test ReadSpecMessage B") {
    new SpecReader().readSpecMessage("B") shouldEqual "Yet another message to read for the upcoming test."
  }

}
