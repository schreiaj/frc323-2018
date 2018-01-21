package test.frc.team323.simulation;

public class ElevatorSystem extends System {
  public double position;
  double mass;
  public double pulleyRadius;
  public boolean homingSwitch;
  public double generatedTorque;
  public double accel;

  public ElevatorSystem() {

    // TODO find correct loading value
    mass = 5.0; // kg

    pulleyRadius = .05; // m
    position = 0.0; // m
    // linear velocity of mass
    velocity = 0.0;
    // Set up our reduction (can literally put in gears here)
    reduction = 12.0/85.0 * 14.0/ 60.0 * 16.0/36.0;

    // We know we're driven by 2x 775s
    motors = new Motor[] {Motor.Vex775Pro(), Motor.Vex775Pro()};
  }

  public void init() {
    // We initialize the elevator at an unknown location to test auto homing
    position = 0.4; // m
    homingSwitch = false;
  }

  public void step(double voltage, double dt) {
    position += velocity * dt;
    homingSwitch = (position <= 0.0); // Used a <= here because floating point error
    // double torqueLoad = mass * pulleyRadius;
    double w_pulley = velocity/pulleyRadius;
    double torque = 0.0;
    for (Motor m : motors) {
      torque += m.t(voltage, w_pulley*reduction);
    }
    generatedTorque = torque/reduction;
    accel = (generatedTorque / pulleyRadius) / mass;
    velocity += accel * dt;
  }

}
