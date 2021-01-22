package net.ddns.whomagoo.laserchess.game.piece;

import net.ddns.whomagoo.laserchess.game.Directions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangularMirrorTest {
  @Test
  public void testOnHitNorthNorth(){
    TriangularMirror b = new TriangularMirror("TeamA");
    b.setFacing(Directions.NORTH);

    List<String> resultingDirections;
    resultingDirections =  b.hit(Directions.NORTH);

    assertEquals(1, resultingDirections.size());
    assertEquals(Directions.EAST, resultingDirections.get(0));
  }

  @Test
  public void testOnHitNorthSouth(){
    TriangularMirror b = new TriangularMirror("TeamA");
    b.setFacing(Directions.NORTH);

    List<String> resultingDirections;
    resultingDirections =  b.hit(Directions.SOUTH);

    assertEquals(1, resultingDirections.size());
    assertTrue(resultingDirections.contains(Directions.DESTROYED));
  }

  @Test
  public void testOnHitNorthEast(){
    TriangularMirror b = new TriangularMirror("TeamA");
    b.setFacing(Directions.NORTH);

    List<String> resultingDirections;
    resultingDirections =  b.hit(Directions.EAST);

    assertEquals(1, resultingDirections.size());
    assertEquals(Directions.NORTH, resultingDirections.get(0));
  }

  @Test
  public void testOnHitNorthWest(){
    TriangularMirror b = new TriangularMirror("TeamA");
    b.setFacing(Directions.NORTH);

    List<String> resultingDirections;
    resultingDirections =  b.hit(Directions.WEST);

    assertEquals(1, resultingDirections.size());
    assertEquals(Directions.DESTROYED, resultingDirections.get(0));
  }
}