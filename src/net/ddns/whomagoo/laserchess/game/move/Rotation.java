package net.ddns.whomagoo.laserchess.game.move;

import net.ddns.whomagoo.laserchess.game.Board;
import net.ddns.whomagoo.laserchess.game.piece.Piece;

public class Rotation implements MoveDoer {
  boolean clockwise;

  public Rotation(boolean clockwise){
    this.clockwise = clockwise;
  }

  @Override
  public void doMove(Board board, Move move) {
    Piece piece = move.getSource();
    if(board.getCurTeamTurn().equals(piece.teamName())) {
      piece.rotate(clockwise);
      board.moveTaken();
    }
  }
}
