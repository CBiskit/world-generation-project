package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import terrains.Terrain;
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
        StaticShader shader = new StaticShader();

        //OpenGL expects vertices to be defined counter-clockwise by default


        RawModel model = OBJLoader.loadObjModel("stall", loader);
//        ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));

        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        Entity entity = new Entity(staticModel, new Vector3f(0,0,-25),0,0,0,1);
        Light light = new Light(new Vector3f(0,0,-1), new Vector3f(1,1,1));

        Terrain terrain = new Terrain(-1,-1,loader,new ModelTexture(loader.loadTexture("grasslong ")));
        Terrain terrain2 = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grasslong")));

        Camera camera = new Camera();
        MasterRenderer renderer = new MasterRenderer();
        while(!Display.isCloseRequested()){
            //Game logic
            //Rendering
            entity.increaseRotation(0,1,0);
            camera.move();

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            renderer.processEntity(entity);

            renderer.render(light, camera);
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);
            shader.stop();
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
