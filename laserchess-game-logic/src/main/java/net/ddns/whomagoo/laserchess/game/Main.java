package net.ddns.whomagoo.laserchess.game;

public class Main{

  public static void main(String[] args) {
    System.out.println("Hello World");
    Board b = GameFactory.makeDefaultBoard();
    System.out.println("Finished");
    System.out.println(b);
  }
}