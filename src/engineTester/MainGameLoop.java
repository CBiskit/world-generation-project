package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
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

        ModelData data = OBJFileLoader.loadOBJ("tree");
        RawModel treeModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
                data.getIndices());

        RawModel model = OBJLoader.loadObjModel("stall", loader);
        RawModel model2 = OBJLoader.loadObjModel("fern", loader);
        //ModelTexture texture = new ModelTexture(loader.loadTexture("image"));

        TexturedModel stall = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
        TexturedModel fern = new TexturedModel(model2, new ModelTexture(loader.loadTexture("fern")));

        fern.getTexture().setHasTransparency(true);

        ModelTexture texture = stall.getTexture();
        ModelTexture texture2 = fern.getTexture();

        texture.setShineDamper(10);
        texture.setReflectivity(0);
        texture2.setShineDamper(10);
        texture2.setReflectivity(0);

        Entity stallEntity = new Entity(stall, new Vector3f(400,0,-400),0,0,0,1);
        Entity fernEntity = new Entity(fern, new Vector3f(400,0,-450),0,0,0,1) ;
        Light light = new Light(new Vector3f(0,0,0), new Vector3f(1,1,1));

        Terrain terrain = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(1,-1,loader,new ModelTexture(loader.loadTexture("grass")));

        Camera camera = new Camera();
        MasterRenderer renderer = new MasterRenderer();
        while(!Display.isCloseRequested()){
            //Game logic
            //Rendering
            //entity.increaseRotation(0,1,0);
            camera.move();

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            renderer.processEntity(stallEntity);
            renderer.processEntity(fernEntity);

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
