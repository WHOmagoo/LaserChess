package net.ddns.whomagoo.laserchess.game;

import javafx.util.Pair;

import java.util.Objects;

public class LaserSegment {
  String directionSource;
  String directionOut;

  int locX;
  int locY;

  public LaserSegment(String directionSource, String directionOut, Pair<Integer, Integer> loc) {
    this.directionSource = directionSource;
    this.directionOut = directionOut;
    locX = loc.getKey();
    locY = loc.getValue();
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

  public int getLocY() {
    return locY;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((directionSource == null) ? 0 : directionSource.hashCode());
    result = prime * result + ((directionOut == null) ? 0 : directionOut.hashCode());
    result = prime * result + locX * locY + locY;

    return result;
  }

  @Override
  public boolean equals(Object obj){
    if(this == obj){
      return true;
    }

    if(obj == null){
      return false;
    }

    if(!(obj instanceof  LaserSegment)){
      return false;
    }

    LaserSegment seg = (LaserSegment) obj;

    return locX == seg.locX
        && locY == seg.locY
        && Objects.equals(directionSource, seg.directionSource)
        && Objects.equals(directionOut, seg.directionOut);
  }
}
