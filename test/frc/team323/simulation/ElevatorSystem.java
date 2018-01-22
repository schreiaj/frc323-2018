package test.frc.team323.simulation;

public class ElevatorSystem extends System {
  public double position;
  double mass;
  public double pulleyRadius;
  public boolean homingSwitch;
  public double force;
  public double accel;

  public ElevatorSystem() {

    // TODO find correct loading value
    mass = 5.0; // kg

    pulleyRadius = .0254; // m
    position = 0.0; // m
    // linear velocity of mass
    velocity = 0.0;
    // Set up our reduction (can literally put in gears here)
    reduction = 12.0/85.0 * 14.0/ 60.0 * 22.0 / 44.0;

    // We know we're driven by 2x 775s
    motors = new Motor[] {Motor.Vex775Pro()};
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
      torque += m.t(voltage, w_pulley/reduction);
    }
    force = (torque * (1.0/reduction))/pulleyRadius;
    // t = rF
    // F = r/t
    // F= mA
    // A = F/m
    accel = force / mass;
    velocity += accel * dt;
    /*
      velocity = 1m/s
      r_pulley = .0254 m
      reduction = 1/300
      load = 5kg
      w_pulley = velocity /  r_pulley => 39.37/s
      w_motor = w_pulley / reduction => 11,811.024/s
      w_motor * 60s / 2 / 3.1415 => 112,790.294
      torque = .26N*m
      torque_pulley = torque / reduction => 78 N*m
      force = torque_pulley / r_pulley => 3,070.866 N
      acceleration = force/load => 614.173 N/kg
    */
  }

}
