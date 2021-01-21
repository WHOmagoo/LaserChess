package net.ddns.whomagoo.laserchess.game.piece;

import net.ddns.whomagoo.laserchess.game.Directions;
import net.ddns.whomagoo.laserchess.game.move.Move;

import java.util.Collections;
import java.util.List;

public class TriangularMirror extends GamePiece{
  public TriangularMirror(String teamName) {
    super(teamName);
    typeName = PieceNames.TriangularMirror;
  }

  @Override
  public List<String> hit(String direction) {
    if(facing.equals(direction)){
      return Collections.singletonList(Directions.clockwise(direction));
    } else if(Directions.clockwise(facing).equals(direction)){
      return Collections.singletonList(Directions.counterClockwise(direction));
    } else {
      return Collections.singletonList(Directions.DESTROYED);
    }
  }

  @Override
  public List<Move> getAllPossibleMoves() {
    return Move.defaultMoves(xPos, yPos, this);
  }
}
