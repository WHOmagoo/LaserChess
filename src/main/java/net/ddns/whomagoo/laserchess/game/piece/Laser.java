package net.ddns.whomagoo.laserchess.game.piece;

import net.ddns.whomagoo.laserchess.game.Directions;
import net.ddns.whomagoo.laserchess.game.move.Move;

import java.util.Collections;
import java.util.List;

public class Laser extends GamePiece {
  public Laser(String teamName){
    super(teamName);
    this.typeName = "Laser";
  }

  @Override
  public List<Move> getAllPossibleMoves() {
    return Move.allMoves(xPos, yPos, this);
  }

  @Override
  public List<String> hit(String direction) {
    return Collections.singletonList(Directions.DESTROYED);
  }
}
