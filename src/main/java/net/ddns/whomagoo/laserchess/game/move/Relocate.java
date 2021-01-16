package net.ddns.whomagoo.laserchess.game.move;

import net.ddns.whomagoo.laserchess.game.Board;
import net.ddns.whomagoo.laserchess.game.piece.Piece;

public class Relocate implements MoveDoer {
  @Override
  public void doMove(Board board, Move move) {
    Piece piece = move.getSource();
    if(board.getCurTeamTurn().equals(piece.teamName())) {
      board.movePiece(piece, move.getTargetX(), move.getTargetY());
      board.moveTaken();
    }
  }
}
