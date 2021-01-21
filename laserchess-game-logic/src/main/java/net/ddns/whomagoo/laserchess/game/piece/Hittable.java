package net.ddns.whomagoo.laserchess.game.piece;

import java.util.List;

public interface Hittable {
  public List<String> hit(String direction);
}
