package net.ddns.whomagoo.laserchess.game.piece;

import net.ddns.whomagoo.laserchess.game.Board;
import net.ddns.whomagoo.laserchess.game.Directions;
import net.ddns.whomagoo.laserchess.game.move.Move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class King extends GamePiece{
  public King(String teamName){
    super(teamName);
    this.typeName = "King";
  }

  @Override
  public List<String> hit(String direction) {
    return Collections.singletonList(Directions.DESTROYED);
  }


  @Override
  public List<Move> getAllPossibleMoves() {
    return Move.movingMoves(xPos, yPos, this);
  }

  @Override
  public List<Move> getValidMoves(List<Move> moves, Board gp){
    //TODO implement this
    return super.getValidMoves(moves, gp);
  }
}
