package net.ddns.whomagoo.laserchess.game.piece;

import net.ddns.whomagoo.laserchess.game.Directions;
import net.ddns.whomagoo.laserchess.game.move.Move;

import java.util.Collections;
import java.util.List;

public class HorizontalMirror extends GamePiece{
  public HorizontalMirror(String teamName) {
    super(teamName);
    typeName = "Horizontal Mirror";
  }

  @Override
  public List<String> hit(String direction) {

    if(this.facing.equals(direction) || Directions.opposite(facing).equals(direction)){
      return Collections.singletonList(Directions.opposite(direction));
    }

    return Collections.singletonList(direction);
  }

  @Override
  public List<Move> getAllPossibleMoves() {
    return Move.defaultMoves(xPos, yPos, this);
  }
}
