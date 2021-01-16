package net.ddns.whomagoo.laserchess.game.piece;

import net.ddns.whomagoo.laserchess.game.Board;
import net.ddns.whomagoo.laserchess.game.Directions;
import net.ddns.whomagoo.laserchess.game.move.Move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hypercube extends GamePiece {
  public Hypercube(String teamName) {
    super(teamName);
    typeName = "Hypercube";
  }

  @Override
  public List<String> hit(String direction) {
    return Collections.singletonList(Directions.opposite(direction));
  }

  @Override
  public List<Move> getAllPossibleMoves() {
    return Move.movingMoves(xPos, yPos, this);
  }

  @Override
  public List<Move> getValidMoves(List<Move> moves, Board gp) {
    ArrayList<Move> result = new ArrayList<Move>(moves.size());

    for(Move m : moves){
      if (m.getName().startsWith("Move")
          && gp.hasTeleported()
          && !gp.isNeutral(m.getTargetX(), m.getTargetY())){
        continue;
      }


      result.add(m);
    }

    return result;
  }
}
