package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
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
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        //****************TERRAIN TEXTURE PACK*********************

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grass-short"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("cobblestone"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture,
                bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        //*********************************************************

        StaticShader shader = new StaticShader();

        //OpenGL expects vertices to be defined counter-clockwise by default

        //****************Player Object****************************
        RawModel bunnyModel = OBJLoader.loadObjModel("stanfordBunny", loader);
        TexturedModel bunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("mountainrock")));
        //*********************************************************



        RawModel model = OBJLoader.loadObjModel("tree", loader);
        RawModel model2 = OBJLoader.loadObjModel("fern", loader);
        //ModelTexture texture = new ModelTexture(loader.loadTexture("image"));

        TexturedModel lowPolyTree = new TexturedModel(model, new ModelTexture(loader.loadTexture("lowPolyTree")));
        TexturedModel fern = new TexturedModel(model2, new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setHasTransparency(true);

        ModelTexture texture = lowPolyTree.getTexture();
        ModelTexture texture2 = fern.getTexture();

        texture.setShineDamper(10);
        texture.setReflectivity(0);
        texture2.setShineDamper(10);
        texture2.setReflectivity(0);

        Light light = new Light(new Vector3f(0,2000,2000), new Vector3f(1,1,1));


        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
        Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightmap");

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<50;i++){
            if(i % 10 == 0) {
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                float y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(lowPolyTree, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 20));
            }
            if(i % 5 == 0){
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                float y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(fern, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 2));

            }
        }

        Player player = new Player(bunny, new Vector3f(100, 5, -150), 0, 180, 0, 0.6f);

        Camera camera = new Camera(player);
        MasterRenderer renderer = new MasterRenderer();
        while(!Display.isCloseRequested()){
            camera.move();
            player.move(terrain);
            renderer.processEntity(player);
            //Game logic
            //Rendering
            //entity.increaseRotation(0,1,0);
            camera.move();

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
//            renderer.processEntity(stallEntity);
//            renderer.processEntity(fernEntity);
            for(Entity entity:entities){
                renderer.processEntity(entity);
            }
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
