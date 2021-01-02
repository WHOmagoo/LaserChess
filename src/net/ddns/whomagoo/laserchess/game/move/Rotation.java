package net.ddns.whomagoo.laserchess.game.move;

import net.ddns.whomagoo.laserchess.game.Board;

public class Rotation implements MoveDoer {
  boolean clockwise;

  public Rotation(boolean clockwise){
    this.clockwise = clockwise;
  }

  @Override
  public void doMove(Board board, Move move) {
    move.getSource().rotate(clockwise);
  }
}
