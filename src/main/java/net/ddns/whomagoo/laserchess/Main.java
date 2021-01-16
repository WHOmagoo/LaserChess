package net.ddns.whomagoo.laserchess;

import net.ddns.whomagoo.laserchess.game.*;

import static net.ddns.whomagoo.laserchess.game.GameFactory.makeDefaultBoard;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello World");
    Board b = makeDefaultBoard();
    System.out.println("Finished");
    System.out.println(b);
  }
}
