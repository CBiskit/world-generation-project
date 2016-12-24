package engineTester;

import models.TexturedModel;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

/**
 * Created by c1547497 on 24/12/2016.
 */
public class MainGameLoop {

    /**
     * @param args
     */

    public static void main(String[] args){

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        //OpenGL expects vertices to be defined counter-clockwise by default
        float[] vertices = {
                -0.5f, 0.5f, 0f,    //V0
                -0.5f, -0.5f, 0f,   //V1
                0.5f, -0.5f, 0f,    //V2
                0.5f, 0.5f, 0f     //V3
        };

        int[] indices = {
                0,1,3,  //Top left triangle (V0, V1, V3)
                3,1,2   //Bottom right triangle (V3, V1, V2)
        };

        float[] textureCoords ={
            0,0,    //V0
            0,1,    //V1
            1,1,    //V2
            1,0     //v3
        };

        RawModel model = loader.loadToVAO(vertices,textureCoords ,indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
        TexturedModel texturesModel = new TexturedModel(model,texture);

        while(!Display.isCloseRequested()){
            renderer.prepare();
            //Game logic
            //Rendering
            shader.start();
            renderer.render(texturesModel);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
