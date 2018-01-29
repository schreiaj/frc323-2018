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
    kFreeSpeed = freeSpeed / 60.0 * 2.0 * Math.PI;
    kFreeCurrent = freeCurrent;
    kStallCurrent = stallCurrent;
    kStallTorque = stallTorque;
    kVoltageSpec = voltage;
    // V = IR + W/k_v
    // At stall:
    //  V = stallCurrent/R
    //  R = V/stallCurrent
    k_r = kVoltageSpec/kStallCurrent;
    // At free speed:
    //  V = freeCurrent * R + freeSpeed/k_v
    //  V - (freeCurrent * R) = freeSpeed/k_v
    //  K_v(V - (freeCurrent * R)) = FreeSpeed
    //  K_v = freeSpeed/ (V - (freeCurrent * R))
    k_v = kFreeSpeed / (kVoltageSpec - kFreeCurrent * k_r);
    // t = I * k_t
    // t/I = k_t
    k_t = kStallTorque / kStallCurrent;
  }

  public double t(double voltage, double w) {
    double _w = w;
    // t = I * k_t
    // V = IR + w/k_v
    // V - w/k_v = IR
    // (V - w/k_v)/R = I
    // t = ((V - w/k_v)/R) * k_t
    return Math.min(((voltage - _w/k_v)/k_r) * k_t, kStallTorque);
  }

  // The math on this one needs some work
  // public double a(double voltage, double w) {
  //   return Math.min(((voltage - w/k_v)/k_r), kStallCurrent);
  // }

  public double w(double voltage, double t) {
      // V = IR + W/k_v
      // (V - IR)*k_v = W
      // t = I * k_t
      // t/k_t = I
      // k_v * (V - tR/k_t) = W
      return Math.min(k_v * (voltage - (t*k_r/k_t)), kFreeSpeed);
  }


  public static Motor CIM() {
    return new Motor(5330, 2.7, 2.41, 131, 12.0);
  }

  public static Motor Vex775Pro() {
    return new Motor(18730, 0.7, .71, 134, 12.0);
  }

}
