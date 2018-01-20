package test.frc.team323;

import test.frc.team323.simulation.Motor;

import org.junit.Test;
import static com.google.common.truth.Truth.*;


public class BasicTest {

  @Test
  public void MotorTest() {
    Motor myMotor = Motor.CIM();
    // These tests deal with floating point numbers so we have a range of allowable results
    assertWithMessage("Incorrect stall torque").that(myMotor.t(12, 0)).isWithin(1.0e-5).of(2.41);
    // The ugly number in this test is because the motor doesn't draw 0 A at free speed,
    // this adds just enough of a load to account for that, it's literally (stallTorque/stallCurrent)*freeCurrent;
    assertWithMessage("Incorrect free speed").that(myMotor.w(12, .04967175573)).isWithin(1.0e-5).of(5330);
  }
}
