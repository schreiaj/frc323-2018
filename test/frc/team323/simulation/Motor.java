package test.frc.team323.simulation;

import java.lang.Math;

/*
Equations that drive this

  V = IR + W/k_V
  V = voltage
  I = Current
  R = Resistance
  k_V = voltage constant
  W = rpm

  t = I * k_t
  t = torque
  k_t = torque constant
*/


public class Motor {
  private double kFreeSpeed;
  private double kFreeCurrent;
  private double kStallCurrent;
  private double kStallTorque;
  private double kVoltageSpec = 12.0;
  private double k_t;
  private double k_v;
  private double k_r;

  public Motor(double freeSpeed, double freeCurrent, double stallTorque, double stallCurrent, double voltage) {
    // Austin converts to rads/s but I don't like thinking in those.
    kFreeSpeed = freeSpeed ;/// 60.0 * 2.0 * Math.PI;
    kFreeCurrent = freeCurrent;
    kStallCurrent = stallCurrent;
    kStallTorque = stallTorque;
    kVoltageSpec = voltage;
    k_r = kVoltageSpec/kStallCurrent;
    k_v = kFreeSpeed / (kVoltageSpec - kFreeCurrent * k_r);
    k_t = kStallTorque / kStallCurrent;
  }

  public double t(double voltage, double w) {
    return Math.max(((voltage - w/k_v)/k_r*k_t), kStallTorque);
  }

  public double w(double voltage, double t) {
      return Math.max((k_v * (voltage - (t/k_t)*k_r)), kFreeSpeed);
  }

  public static Motor CIM() {
    return new Motor(5330, 2.7, 2.41, 131, 12.0);
  }

  public static Motor Vex775Pro() {
    return new Motor(18730, 0.7, .71, 134, 12.0);
  }

}
