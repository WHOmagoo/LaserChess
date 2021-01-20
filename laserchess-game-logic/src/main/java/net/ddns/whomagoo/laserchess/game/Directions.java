package net.ddns.whomagoo.laserchess.game;

import java.util.Arrays;
import java.util.List;

public class Directions{
  public static final String NORTH = "North";
  public static final String EAST = "East";
  public static final String SOUTH = "South";
  public static final String WEST = "West";
  public static final String DESTROYED = "Destroyed";

  public static final List<String> validDirections = Arrays.asList(NORTH, SOUTH, EAST, WEST);

  public static String counterClockwise(String curDirection){
    switch (curDirection) {
      case NORTH:
        return WEST;
      case EAST:
        return NORTH;
      case SOUTH:
        return EAST;
      case WEST:
        return SOUTH;
      default:
        return curDirection;
    }
  }

  public static String clockwise(String curDirection){
    switch (curDirection) {
      case NORTH:
        return EAST;
      case EAST:
        return SOUTH;
      case SOUTH:
        return WEST;
      case WEST:
        return NORTH;
      default:
        return curDirection;
    }
  }

  public static String rotate(String curDirection, boolean clockwise){
    return clockwise ? clockwise(curDirection) : counterClockwise(curDirection);
  }

  public static Location getLocationInDirection(Location loc, String direction){
    return getLocationInDirection(loc, direction, 1);
  }

  public static Location getLocationInDirection(Location loc, String direction, int distance){
    switch (direction){
      case NORTH:
        return new Location(loc.getKey(), loc.getValue() + distance);
      case SOUTH:
        return new Location(loc.getKey(), loc.getValue() - distance);
      case WEST:
        return new Location(loc.getKey() - distance, loc.getValue());
      case EAST:
        return new Location(loc.getKey() + distance, loc.getValue());
      default:
        return null;
    }
  }

  public static String getDirection(int xOrigin, int yOrigin, int xOther, int yOther){
    if(xOrigin == xOther ^ yOrigin == yOther) {
      if (xOrigin < xOther) {
        return EAST;
      } else if (xOrigin > xOther) {
        return WEST;
      } else if (yOrigin < yOther) {
        return NORTH;
      } else {
        return SOUTH;
      }
    }

    return "UNKOWN";
  }

  public static int getNum(String direction){
    switch (direction){
      case NORTH: return 0;
      case EAST: return 1;
      case SOUTH: return 2;
      case WEST: return 3;
      default: return -1;
    }
  }

  public static String opposite(String curDirection){
    switch (curDirection) {
      case NORTH: return SOUTH;
      case EAST: return WEST;
      case SOUTH: return NORTH;
      case WEST: return EAST;
      default: return curDirection;
    }
  }
}
