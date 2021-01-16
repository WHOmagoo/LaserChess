package net.ddns.whomagoo.laserchess.game.move;

import javafx.util.Pair;
import net.ddns.whomagoo.laserchess.game.Board;
import net.ddns.whomagoo.laserchess.game.Directions;
import net.ddns.whomagoo.laserchess.game.LaserSegment;
import net.ddns.whomagoo.laserchess.game.piece.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class FireLaser implements MoveDoer {
  @Override
  public void doMove(Board board, Move move) {

    Piece p = move.getSource();
    if(board.getCurTeamTurn().equals(p.teamName())) {

      Pair<Integer, Integer> startLoc = Directions.getLocationInDirection(new Pair<>(p.xPos(), p.yPos()), p.facing());
      HashSet<LaserSegment> result = new HashSet<LaserSegment>();
      propagateLaser(startLoc, move.getSource().facing(), board, result);
      board.setLaserPath(result);
      board.moveTaken();
    }
  }

  private void propagateLaser(Pair<Integer, Integer> loc, String directionPrevExit, Board board, HashSet<LaserSegment> laserSegments){
    Piece p = board.getPiece(loc.getKey(), loc.getValue());

    if(p == null){

      LaserSegment nextMove = new LaserSegment(Directions.opposite(directionPrevExit), directionPrevExit, loc);

      if(!laserSegments.add(nextMove)){
        //Break Condition met
        return;
      }

      Pair<Integer, Integer> newLoc = Directions.getLocationInDirection(loc, directionPrevExit);
      if(board.inBounds(newLoc.getKey(), newLoc.getValue())){
        propagateLaser(newLoc, directionPrevExit, board, laserSegments);
      }
    } else {

      List<String> directionsResult = p.hit(Directions.opposite(directionPrevExit));

      for(String directionResult : directionsResult){
        LaserSegment ls = new LaserSegment(Directions.opposite(directionPrevExit), directionResult, loc);
        if(laserSegments.add(ls) && !Directions.DESTROYED.equals(directionResult)){
          Pair<Integer, Integer> newLoc = Directions.getLocationInDirection(new Pair<>(p.xPos(), p.yPos()), directionResult);
          if(board.inBounds(newLoc.getKey(), newLoc.getValue())){
            propagateLaser(newLoc, directionResult, board, laserSegments);
          }
        }
      }
    }
  }
}
