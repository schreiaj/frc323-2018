package test.frc.team323;

import test.frc.team323.simulation.Motor;
import test.frc.team323.simulation.ElevatorSystem;

import frc.team323.robot.controllers.ElevatorController;

import org.junit.Test;
import static com.google.common.truth.Truth.*;
import com.google.gson.Gson;

import java.io.PrintWriter;
import java.util.Vector;
import java.lang.Math;


public class BasicTest {

  @Test
  public void MotorTest() {
    Motor myMotor = Motor.CIM();
    // These tests deal with floating point numbers so we have a range of allowable results
    assertWithMessage("Incorrect stall torque").that(myMotor.t(12, 0)).isWithin(1.0e-10).of(2.41);
    assertWithMessage("Incorrect free speed").that(myMotor.w(12, 0)).isWithin(1.0e-10).of(5330 / 60.0 * 2.0 * Math.PI);
  }

  @Test
  public void ElevatorTest() throws Exception{
    ElevatorSystem sys = new ElevatorSystem();
    assertWithMessage("Elevator properly initialized").that(sys.velocity).isWithin(1.0e-10).of(0.0);
    sys.init();
    ElevatorController controller = new ElevatorController();
    double goal = 1.0;
    double maxTime = 5.0;
    double dt = 1.0/100.0;
    PrintWriter log = new PrintWriter("elevator.csv");
    log.println("t, voltage, pos, vel, accel, homing");
    for(double t = 0.0; t <= maxTime; t+=dt) {
      double pos = sys.position;

      double voltage = controller.step(pos, goal, sys.homingSwitch);
      sys.step(voltage, dt);
      log.printf("%f, %f, %f, %f, %f, %d %n", t, voltage, pos, sys.velocity, sys.accel ,sys.homingSwitch?1:0);
    }
    log.flush();
    log.close();
  }
}
