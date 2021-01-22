package net.ddns.whomagoo.laserchess.game.piece;

import net.ddns.whomagoo.laserchess.game.Directions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {
  @Test
  public void testOnHitNorthNorth(){
    King b = new King("TeamA");
    b.setFacing(Directions.NORTH);

    List<String> resultingDirections;
    resultingDirections =  b.hit(Directions.NORTH);

    assertEquals(1, resultingDirections.size());
    assertEquals(Directions.DESTROYED, resultingDirections.get(0));
  }

  @Test
  public void testOnHitNorthSouth(){
    King b = new King("TeamA");
    b.setFacing(Directions.NORTH);

    List<String> resultingDirections;
    resultingDirections =  b.hit(Directions.SOUTH);

    assertEquals(1, resultingDirections.size());
    assertTrue(resultingDirections.contains(Directions.DESTROYED));
  }

  @Test
  public void testOnHitNorthEast(){
    King b = new King("TeamA");
    b.setFacing(Directions.NORTH);

    List<String> resultingDirections;
    resultingDirections =  b.hit(Directions.EAST);

    assertEquals(1, resultingDirections.size());
    assertEquals(Directions.DESTROYED, resultingDirections.get(0));
  }

  @Test
  public void testOnHitNorthWest(){
    King b = new King("TeamA");
    b.setFacing(Directions.NORTH);

    List<String> resultingDirections;
    resultingDirections =  b.hit(Directions.WEST);

    assertEquals(1, resultingDirections.size());
    assertEquals(Directions.DESTROYED, resultingDirections.get(0));
  }

}