package net.ddns.whomagoo.laserchess.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

;
;

public class LocationTest {

  @Test
  public void testConstructor(){
    Location l = new Location(0,0);
    assertNotNull(l);
  }

  @Test
  public void testLocationX(){
    Location l = new Location(5,0);

    assertEquals(5, l.getX());
  }

  @Test
  public void testLocationY(){
    Location l = new Location(0,5);

    assertEquals(5, l.getY());
  }

  @Test
  public void testSetterX(){
    Location l = new Location(0,1);
    l.setX(2);

    assertEquals(2, l.getX());
  }

  @Test
  public void testSetterY(){
    Location l = new Location(0,1);
    l.setY(2);

    assertEquals(2, l.getY());
  }


}
