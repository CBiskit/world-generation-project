package engineTester;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;

/**
 * Created by c1547497 on 24/12/2016.
 */
public class MainGameLoop {

    /**
     * @param args
     */

    public static void main(String[] args){

        DisplayManager.createDisplay();

        while(!Display.isCloseRequested()){

            //Game logic
            //Rendering
            DisplayManager.updateDisplay();
        }

        DisplayManager.closeDisplay();
    }
}
