package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.Sys;
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

	public static int NUMBER_OF_TERRAINS_SQUARED = 2;
	private static Player player;
	private static MasterRenderer renderer;
	private static Loader loader;
	private static Terrain[][] terrains;
	private static TerrainTexture blendMap;
	private static TerrainTexturePack texturePack;

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		loader = new Loader();
		renderer = new MasterRenderer(loader);

		// ********* INITIALISE TERRAINS *******************************

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
	    texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
				gTexture, bTexture);

	    terrains = new Terrain[100][100];
		for (int t = 0; t < NUMBER_OF_TERRAINS_SQUARED; t++) {
			for (int j = 0; j < NUMBER_OF_TERRAINS_SQUARED; j++) {
					terrains[t][j] = new Terrain(t, j - 1, loader, texturePack, blendMap);
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
		for (int t = 0; t < NUMBER_OF_TERRAINS_SQUARED; t++) {
			for (int j = 0; j < NUMBER_OF_TERRAINS_SQUARED; j++) {
				for (int i = 0; i < 800; i++) {
					if (i % 3 == 0) {
						float x = (random.nextFloat() * 800) + (terrains[t][j].getX());
						float z = (random.nextFloat() * 800) + (terrains[t][j].getZ());
						float y = terrains[t][j].getHeightOfTerrain(x, z);
						entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360,
								0, 0.5f));
					}
					if (i % 1 == 0) {
						float x = (random.nextFloat() * 800) + (terrains[t][j].getX());
						float z = (random.nextFloat() * 800) + (terrains[t][j].getZ());
						float y = terrains[t][j].getHeightOfTerrain(x, z);
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
						float x = (random.nextFloat() * 800) + (terrains[t][j].getX());
						float z = (random.nextFloat() * 800) + (terrains[t][j].getZ());
						float y = terrains[t][j].getHeightOfTerrain(x, z);
						entities.add(new Entity(box, random.nextInt(4), new Vector3f(x, y + 2.0f, z), 0, random.nextFloat() * 360,
								0, 2.0f));
					}
					if (i % 300 == 0) {
						float x = (random.nextFloat() * 800) + (terrains[t][j].getX());
						float z = (random.nextFloat() * 800) + (terrains[t][j].getZ());
						float y = terrains[t][j].getHeightOfTerrain(x, z);
						entities.add(new Entity(cube, random.nextInt(4), new Vector3f(x, y + 2.0f, z), 0, random.nextFloat() * 360,
								0, 1.5f));
					}
				}
			}
		}
		//*************************************************************


		//**************** INITIALISE PLAYER **************************
		float x = (float)(random.nextInt(800));
		float z = (float)(random.nextInt(800));
		player = new Player(playerModel, new Vector3f(x, terrains[0][0].getHeightOfTerrain(x, z), z), 0, 180, 0, 0.4f);
		Camera camera = new Camera(player);
		//*************************************************************


		//**************** INITIALISE LIGHTING ************************
		List<Light> lights = new ArrayList<Light>();
        //TODO RE-DO LAMP OBJECTS IN MAP TO MATCH TERRAIN HEIGHT
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
			for (int t = 0; t < NUMBER_OF_TERRAINS_SQUARED; t++) {
				for (int j = 0; j < NUMBER_OF_TERRAINS_SQUARED; j++) {
					//generateNewTerrains(t, j);
					renderNearbyTerrains(t, j);
					moveplayer(t, j, camera);
				}
			}
			//TODO BUILD GUI INTO MAIN GAME LOOP
			for (Entity entity : entities) {
				renderer.processEntity(entity);
			}
			System.out.println(player.getCurrentTerrainCoords()[0] + ", " + player.getCurrentTerrainCoords()[1]);
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

	private static void generateNewTerrains(int t, int j){
				for (int i = 0; i < 2; i++)
				{
					//if (terrains[player.getCurrentTerrainCoords()[0] + i][player.getCurrentTerrainCoords()[1] + i] == null){
					//	terrains[i][j] = new Terrain(t, j - 1, loader, texturePack, blendMap);
					//}
					//if (terrains[player.getCurrentTerrainCoords()[0] - i][player.getCurrentTerrainCoords()[1] - i] == null){
					//	terrains[i][j] = new Terrain(t, j - 1, loader, texturePack, blendMap);
					//}
				}
	}

	private static void renderNearbyTerrains(int t, int j){
		if (t < player.getCurrentTerrainCoords()[0] + 2 && t > player.getCurrentTerrainCoords()[0] - 2 &&
				j < player.getCurrentTerrainCoords()[0] + 2 && j > player.getCurrentTerrainCoords()[1] - 2){
			renderer.processTerrain(terrains[t][j]);
		}
	}

	private static void moveplayer(int t, int j, Camera camera){
		if ((t == player.getCurrentTerrainCoords()[0]) && (j == player.getCurrentTerrainCoords()[1] + 1)){
			player.move(terrains[t][j]);
			camera.move();
		}
	}
}
