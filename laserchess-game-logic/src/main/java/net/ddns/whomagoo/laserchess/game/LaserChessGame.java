package net.ddns.whomagoo.laserchess.game;

import net.ddns.whomagoo.laserchess.game.move.Move;
import net.ddns.whomagoo.laserchess.game.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class LaserChessGame {
  Board b;
  ArrayList<String> teamOrder;


  public List<Move> getMoves(int x, int y, String team){
    Piece p = b.getPiece(x, y);
    if(p != null && p.teamName().equals(team)){
      return p.getValidMoves(p.getAllPossibleMoves(), b);
    }

    return new ArrayList<>();
  }

  public void doMove(Move m){
    m.doMove(b);
  }
}
