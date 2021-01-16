package net.ddns.whomagoo.laserchess.game.piece;

import net.ddns.whomagoo.laserchess.game.Board;
import net.ddns.whomagoo.laserchess.game.Directions;
import net.ddns.whomagoo.laserchess.game.move.Move;

import java.util.ArrayList;
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
      return Collections.singletonList(direction);
    } else {
      return Collections.singletonList(Directions.DESTROYED);
    }
  }

  @Override
  public List<Move> getAllPossibleMoves() {
    return Move.defaultMoves(xPos, yPos, this);
  }

  @Override
  public List<Move> getValidMoves(List<Move> moves, Board gp) {
    ArrayList<Move> result = new ArrayList<Move>(moves.size());

    for(Move m : moves){
      if (m.getName().startsWith("Move") && gp.isSameTeam(m.getTargetX(), m.getTargetY(), teamName)){
        continue;
      }

      if(m.getName().equals(Move.FIRE_LASER)){
        //Calculates directly in front of the Laser in in bounds
        if (!gp.inBounds((m.getTargetX() + m.getSource().xPos()) / 2, (m.getTargetY() + m.getSource().yPos()) / 2)){
          continue;
        }
      }

      result.add(m);
    }

    return result;
  }
}
