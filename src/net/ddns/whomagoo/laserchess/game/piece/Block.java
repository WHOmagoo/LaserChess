package net.ddns.whomagoo.laserchess.game.piece;

import net.ddns.whomagoo.laserchess.game.Directions;
import net.ddns.whomagoo.laserchess.game.move.Move;

import java.util.Collections;
import java.util.List;

public class Block extends GamePiece {

  public Block(String teamName) {
    super(teamName);
    super.typeName = "Block";
  }

  @Override
  public List<String> hit(String direction) {
    if(facing.equals(direction)){
      return Collections.singletonList(Directions.opposite(direction));
    } else {
      return Collections.singletonList(Directions.DESTROYED);
    }
  }

  @Override
  public List<Move> getAllPossibleMoves() {
    return Move.defaultMoves(xPos, yPos, this);
  }

}
