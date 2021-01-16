package net.ddns.whomagoo.laserchess.game.move;

import net.ddns.whomagoo.laserchess.game.Board;

public interface MoveDoer {

  public void doMove(Board board, Move move);
}
