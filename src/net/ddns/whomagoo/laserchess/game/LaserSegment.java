package net.ddns.whomagoo.laserchess.game;

import javafx.util.Pair;

public class LaserSegment {
  String directionSource;
  String directionOut;

  int locX;
  int loxY;

  public LaserSegment(String directionSource, String directionOut, Pair<Integer, Integer> loc) {
    this.directionSource = directionSource;
    this.directionOut = directionOut;
    locX = loc.getKey();
    loxY = loc.getValue();
  }

  public String getDirectionSource() {
    return directionSource;
  }

  public String getDirectionOut() {
    return directionOut;
  }

  public int getLocX() {
    return locX;
  }

  public int getLoxY() {
    return loxY;
  }
}
