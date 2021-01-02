package net.ddns.whomagoo.laserchess.game;

import net.ddns.whomagoo.laserchess.game.piece.*;

public class GameFactory {
  public static Board makeDefaultBoard(){
    Board b = new Board(9);

    String[] teamNames = {"TeamA", "TeamB"};

    GamePiece pieces[][] = new GamePiece[2][18];

    //Positions to set for each piece [team][piceNum][x/y/facing]
    int positions[][][] = new int[][][]{
        //Team1 (Top of board)
        {
            //Triangular Mirrors
            {0, 0, 0}, {1, 0, 0}, {7, 0, 3}, {8, 0, 3}, {0,1,3}, {8,1,0},

            //Blocks
            {1, 1,0}, {2, 1,0}, {6, 1,0}, {7, 1,0},

            //H-Mirror
            {3, 1,0}, {4, 1,1},

            //Splitter
            {5, 1,0},

            //Diagonal Mirror
            {2, 0,1}, {6, 0,0},

            //Hypercube
            {3, 0,0},

            //Laser
            {5,0,0},

            //King
            {4,0, 0}
        },

        //Team2 (Bottom of board)
        {}//WIll be populated using the following loop
    };

    String[] facingsLookup = {Directions.NORTH, Directions.EAST, Directions.SOUTH, Directions.WEST};

    positions[1] = new int[18][3];
    for(int i = 0; i < 18; i++){
      positions[1][i][0] = 8 - positions[0][i][0];
      positions[1][i][1] = 8 - positions[0][i][1];
      //Flip pieces
      positions[1][i][2] = (positions[0][i][2] + 2) % 4;
    }


    for(int team = 0; team < 2; team++) {
      String teamName = teamNames[team];
      for (int i = 0; i < 6; i++) {
        pieces[team][i] = new TriangularMirror(teamName);
      }

      for (int i = 0; i < 4; i++){
        pieces[team][i + 6] = new Block(teamName);
      }

      pieces[team][10] = new HorizontalMirror(teamName);
      pieces[team][11] = new HorizontalMirror(teamName);
      pieces[team][12] = new BeamSplitter(teamName);
      pieces[team][13] = new DiagonalMirror(teamName);
      pieces[team][14] = new DiagonalMirror(teamName);
      pieces[team][15] = new Hypercube(teamName);
      pieces[team][16] = new Laser(teamName);
      pieces[team][17] = new King(teamName);

      for(int i = 0; i < 18; i++){
        int[] pos = positions[team][i];
        pieces[team][i].setFacing(facingsLookup[pos[2]]);
        b.setPiece(pos[0], pos[1], pieces[team][i]);
      }

      b.setPiece(4,4, new Hypercube("__environment__"));
    }


    return b;
  }
}
