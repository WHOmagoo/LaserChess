package net.ddns.whomagoo.laserchess.game.move;

import javafx.util.Pair;
import net.ddns.whomagoo.laserchess.game.Board;
import net.ddns.whomagoo.laserchess.game.Directions;
import net.ddns.whomagoo.laserchess.game.LaserSegment;
import net.ddns.whomagoo.laserchess.game.piece.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FireLaser implements MoveDoer {
  @Override
  public void doMove(Board board, Move move) {
    Piece p = move.getSource();

    Pair<Integer, Integer> startLoc = Directions.getLocationInDirection(new Pair<>(p.xPos(), p.yPos()), p.facing());
    List<LaserSegment> result = propagateLaser(startLoc, move.getSource().facing(), board);
    board.setLaserPath(result);

  }

  private List<LaserSegment> propagateLaser(Pair<Integer, Integer> loc, String directionPrevExit, Board board){
    Piece p = board.getPiece(loc.getKey(), loc.getValue());

    if(p == null){
      ArrayList<LaserSegment> result = new ArrayList<>(Collections.singleton(new LaserSegment(Directions.opposite(directionPrevExit), directionPrevExit, loc)));
      Pair<Integer, Integer> newLoc = Directions.getLocationInDirection(loc, directionPrevExit);
      if(board.inBounds(newLoc.getKey(), newLoc.getValue())){
        result.addAll(propagateLaser(newLoc, directionPrevExit, board));
      }

      return result;
    }

    List<String> directionsResult = p.hit(Directions.opposite(directionPrevExit));
    ArrayList<LaserSegment> result = new ArrayList<>(directionsResult.size());
    for(String directionResult : directionsResult){
      result.add(new LaserSegment(Directions.opposite(directionPrevExit), directionResult, loc));
      if(!Directions.DESTROYED.equals(directionResult)){
        Pair<Integer, Integer> newLoc = Directions.getLocationInDirection(new Pair<>(p.xPos(), p.yPos()), directionResult);
        if(board.inBounds(newLoc.getKey(), newLoc.getValue())){
          result.addAll(propagateLaser(newLoc, directionResult, board));
        }
      }
    }

    return result;
  }
}
