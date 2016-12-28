package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer(loader);

		// ********* INITIALISE TERRAINS *******************************

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
				gTexture, bTexture);

		Terrain spawnTerrain = new Terrain(0, -1, loader, texturePack, blendMap);
		List<Terrain> terrains = new ArrayList<Terrain>();
		for (int t = 0; t < 3; t++) {
			for (int j = -1; j < 2; j++) {
				if (t == 0 && j == -1) {
					terrains.add(spawnTerrain);
				}
				else {
					terrains.add(new Terrain(t, j, loader, texturePack, blendMap));
				}
			}
		}
		// ************************************************************

		//********* INITIALISE MODELS *********************************
		RawModel person = OBJLoader.loadObjModel("person", loader);

		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);

		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
				new ModelTexture(loader.loadTexture("grassTexture")));
		TexturedModel flower = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
				new ModelTexture(loader.loadTexture("flower")));
		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTextureAtlas);
        TexturedModel lamp = new TexturedModel(OBJLoader.loadObjModel("lamp", loader),
                new ModelTexture(loader.loadTexture("lamp")));
		TexturedModel pinetree = new TexturedModel(OBJLoader.loadObjModel("pine", loader),
				new ModelTexture(loader.loadTexture("pine")));
		//TexturedModel rock1 = new TexturedModel(OBJLoader.loadObjModel("rock1", loader),
				//new ModelTexture(loader.loadTexture("rock1")));
		//TexturedModel rock2 = new TexturedModel(OBJLoader.loadObjModel("rock2", loader),
				//new ModelTexture(loader.loadTexture("rock2")));
		TexturedModel box = new TexturedModel(OBJLoader.loadObjModel("box", loader),
				new ModelTexture(loader.loadTexture("box")));
		TexturedModel cube = new TexturedModel(OBJLoader.loadObjModel("box", loader),
				new ModelTexture(loader.loadTexture("cube")));
		TexturedModel playerModel = new TexturedModel(person, new ModelTexture(
				loader.loadTexture("playerTexture")));

		pinetree.getTexture().setHasTransparency(true);
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		flower.getTexture().setHasTransparency(true);
		flower.getTexture().setUseFakeLighting(true);
		fern.getTexture().setHasTransparency(true);
		//**************************************************************

		//************** INITIALISE WORLD ******************************
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for (Terrain terrain : terrains) {
			for (int i = 0; i < 400; i++) {
				if (i % 3 == 0) {
					float x = (random.nextFloat() * 800) + (terrain.getX());
					float z = (random.nextFloat() * 800) + (terrain.getZ());
					float y = terrain.getHeightOfTerrain(x, z);
                	entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360,
							0, 0.5f));
				}
				if (i % 1 == 0) {
					float x = (random.nextFloat() * 800) + (terrain.getX());
					float z = (random.nextFloat() * 800) + (terrain.getZ());
					float y = terrain.getHeightOfTerrain(x, z);
					entities.add(new Entity(pinetree, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360,
							0, random.nextFloat() * 0.1f + 0.6f));
				}
				//if (i % 10 == 0) {
				//	float x = (random.nextFloat() * 800) + (terrain.getX());
				//	float z = (random.nextFloat() * 800) + (terrain.getZ());
				//	float y = terrain.getHeightOfTerrain(x, z);
				//	entities.add(new Entity(rock1, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360,
				//			0, random.nextFloat() * 0.1f + 0.6f));
				//}
				//if (i % 10 == 0) {
				//	float x = (random.nextFloat() * 800) + (terrain.getX());
				//	float z = (random.nextFloat() * 800) + (terrain.getZ());
				//	float y = terrain.getHeightOfTerrain(x, z);
				//	entities.add(new Entity(rock2, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360,
				//			0, random.nextFloat() * 0.1f + 0.6f));
				//}
				if (i % 30 == 0) {
					float x = (random.nextFloat() * 800) + (terrain.getX());
					float z = (random.nextFloat() * 800) + (terrain.getZ());
					float y = terrain.getHeightOfTerrain(x, z);
					entities.add(new Entity(box, random.nextInt(4), new Vector3f(x, y + 2.0f, z), 0, random.nextFloat() * 360,
							0, 2.0f));
				}
				if (i % 300 == 0) {
					float x = (random.nextFloat() * 800) + (terrain.getX());
					float z = (random.nextFloat() * 800) + (terrain.getZ());
					float y = terrain.getHeightOfTerrain(x, z);
					entities.add(new Entity(cube, random.nextInt(4), new Vector3f(x, y + 2.0f, z), 0, random.nextFloat() * 360,
							0, 1.5f));
				}
			}
		}
		Player player = new Player(playerModel, new Vector3f(100, 5, -150), 0, 180, 0, 0.4f);
		Camera camera = new Camera(player);
		//*************************************************************

		//**************** INITIALISE LIGHTING ************************
		List<Light> lights = new ArrayList<Light>();
        //lights.add(new Light (new Vector3f(0, 1000, -7000), new Vector3f(0.4f, 0.4f, 0.4f)));
        //lights.add(new Light (new Vector3f(185, 10, -293), new Vector3f(2,0,0), new Vector3f(1, 0.01f, 0.002f)));
        //lights.add(new Light (new Vector3f(370, 17, -300), new Vector3f(0,2,2), new Vector3f(1, 0.01f, 0.002f)));
        //lights.add(new Light (new Vector3f(293, 7, -305), new Vector3f(2,2,0), new Vector3f(1, 0.01f, 0.002f)));

        //entities.add(new Entity(lamp, new Vector3f(185, -4.7f, -293), 0, 0, 0, 1));
        //entities.add(new Entity(lamp, new Vector3f(370, 4.2f, -300), 0, 0, 0, 1));
        //entities.add(new Entity(lamp, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1));
        //*************************************************************

		//************ INITIALISE GUIS ********************************
		List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("health"),new Vector2f(-0.8f,0.9f), new Vector2f(0.2f,0.3f));
		//guiTextures.add(gui);
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		//*************************************************************


		//*********** MAIN GAME LOOP **********************************
		while (!Display.isCloseRequested()) {
			player.move(spawnTerrain);
			camera.move();
			for (Terrain terrain : terrains) {
				renderer.processTerrain(terrain);
			}
			for (Entity entity : entities) {
				renderer.processEntity(entity);
			}
			renderer.processEntity(player);
			renderer.render(lights, camera);
			guiRenderer.render(guiTextures);
			DisplayManager.updateDisplay();
		}
		//*************************************************************

		//*********** CLEAN UP ****************************************
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		//*************************************************************
	}

}
