package test.frc.team323.simulation;

// Implements a contract that each inheriting System
// needs to follow to get picked up by the simulation controller
public class System {
  // This is for testing, optimally there'd be getters and setters
  public Motor[] motors;
  public double velocity;
  public double reduction;

  public void init(){};
  public void step(double voltage, double dt){};

}
