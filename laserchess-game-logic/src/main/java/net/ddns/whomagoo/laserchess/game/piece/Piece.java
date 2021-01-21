package net.ddns.whomagoo.laserchess.game.piece;

import net.ddns.whomagoo.laserchess.game.Board;
import net.ddns.whomagoo.laserchess.game.move.Move;

import java.util.List;

public interface Piece extends Hittable{
  public String teamName();

  public String typeName();

  public String facing();

  public void setFacing(String facing);

  public int xPos();

  public void setXPos(int newPos);

  public int yPos();

  public void setYPos(int newPos);

  public List<String> hit(String direction);

  public String toString();

  public List<Move> getValidMoves(List<Move> moves, Board gp);

  public abstract List<Move> getAllPossibleMoves();

  void rotate(boolean clockwise);
}
