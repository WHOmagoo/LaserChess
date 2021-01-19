package net.ddns.whomagoo.laserchess.game;

public class Location {

  int x;
  int y;

  public Location(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getKey() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getValue() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }
}
