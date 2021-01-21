package net.ddns.whomagoo.laserchess.game.piece;

import net.ddns.whomagoo.laserchess.game.Directions;
import net.ddns.whomagoo.laserchess.game.move.Move;

import java.util.Collections;
import java.util.List;

public class DiagonalMirror extends GamePiece{

  public DiagonalMirror(String teamName) {
    super(teamName);
    typeName = PieceNames.DiagonalMirror;
  }

  @Override
  public List<String> hit(String direction) {
    if(facing.equals(direction) || Directions.opposite(facing).equals(direction)){
      return Collections.singletonList(Directions.clockwise(direction));
    }

    return Collections.singletonList(Directions.counterClockwise(direction));
  }

  @Override
  public List<Move> getAllPossibleMoves() {
    return Move.defaultMoves(xPos, yPos, this);
  }
}
