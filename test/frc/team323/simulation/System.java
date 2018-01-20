package test.frc.team323.simulation;

// Implements a contract that each inheriting System
// needs to follow to get picked up by the simulation controller
public abstract class System {
  private Motor[] motors;
  private double velocity;
  private double load;
  private double timeStep;

  public abstract void init();
  public abstract void step();

}
