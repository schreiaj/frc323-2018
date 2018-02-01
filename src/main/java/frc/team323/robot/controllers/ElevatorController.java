package frc.team323.robot.controllers;

public class ElevatorController {
  private double _position;
  private double _goal, _lastGoal;
  private double _SEEK_DISTANCE = .1; //m
  private double _V_MAX = 12;
  private double _P_MAX = 2.1;
  private double _k_p = 7.0;
  private double _k_i = 0.07;
  private double _k_d = 9.8;
  private double _lastError = 0.0;
  private double _sumError = 0.0;
  private ElevatorState _currentState = ElevatorState.INITIALIZE;
  private enum ElevatorState {
    INITIALIZE, GOTO_POSITION, ERROR
  };

  public double step(double position, double goal, boolean homingSwitch) {
    _position = position;
    _goal = goal;
    _sumError = _goal == _lastGoal ? _sumError : 0.0;

    _lastGoal = _goal;
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
    double output = _k_p * (_goal - _position) + _k_i * _sumError + _k_d * _lastError;
    _sumError += (_goal - _position);
    _lastError = _goal - _position;
    return Math.min(Math.max(output, -_V_MAX),_V_MAX);
  }

}
