package frc.team323.robot.controllers;

public class ElevatorController {
  private double _position;
  private double _goal;
  private double _SEEK_DISTANCE = .05; //m
  private double _V_MAX = 12;
  private double _P_MAX = 1.2;
  private double _k_p = 0.1;
  private double _k_d = 0.0;
  private double _lastError = 0.0;
  private ElevatorState _currentState = ElevatorState.INITIALIZE;
  private enum ElevatorState {
    INITIALIZE, GOTO_POSITION, ERROR
  };

  public double step(double position, double goal, boolean homingSwitch) {
    _position = position;
    _goal = goal;
    switch (_currentState) {
      case INITIALIZE:
        if(homingSwitch) {
          _currentState = ElevatorState.GOTO_POSITION;
          break;
        }
        // If we've traveled further than negative our max height something is wrong
        if(_position <= -_P_MAX) {
          _currentState = ElevatorState.ERROR;
        }
        // set the goal to where we are minus the seek distance
        _goal = _position - _SEEK_DISTANCE;
        break;
      case GOTO_POSITION:
        break;
      case ERROR:
        // Handle error
        _V_MAX = 0.0;
        break;
    }
    double output = _k_p * (_goal - _position) + _k_d * _lastError;
    _lastError = _goal - _position;
    return Math.min(Math.max(output, -_V_MAX),_V_MAX);
  }

}
