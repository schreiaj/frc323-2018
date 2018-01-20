package test.frc.team323;

import test.frc.team323.simulation.Motor;

import org.junit.Test;
import static com.google.common.truth.Truth.*;


public class BasicTest {

  @Test
  public void MotorTest() {
    Motor myMotor = Motor.CIM();
    // These tests deal with floating point numbers so we have a range of allowable results
    assertWithMessage("Incorrect stall torque").that(myMotor.t(12, 0)).isWithin(1.0e-10).of(2.41);
    assertWithMessage("Incorrect free speed").that(myMotor.w(12, 0)).isWithin(1.0e-10).of(5330);
  }
}
