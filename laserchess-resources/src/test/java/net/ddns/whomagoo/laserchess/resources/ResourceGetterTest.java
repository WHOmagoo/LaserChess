package net.ddns.whomagoo.laserchess.resources;

import net.ddns.whomagoo.laserchess.game.piece.PieceNames;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

;

/**
 * Unit test for simple App.
 */
public class ResourceGetterTest
{

    @Test
    public void colorConfigExists(){ assertNotNull(ResourceGetter.getResourceAsStream(ResourceGetter.colorConfig));
    }

    @Test
    public void allPiecesExist(){
        for(Field f : PieceNames.class.getDeclaredFields()){
            try {
                Object pieceName = f.get(null);
                String resourceName = pieceName + ".svg";
                assertNotNull(ResourceGetter.getResourceAsStream(resourceName));
            } catch (IllegalAccessException e) {

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);

                fail("Fail on IllegalAccessException " + sw.toString());
            }
        }
    }
}
