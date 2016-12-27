package entities;

import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

/**
 * Created by Ozzy on 27/12/2016.
 */
public class Player extends Entity{

    private static final float RUN_SPEED = 50;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 20;

    private static final float TERRAIN_HEIGHT = 0;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private boolean isInAir = false;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale ){
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(){
        checkInputs();
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRoty())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRoty())));
        super.increasePosition(dx, 0, dz);

        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        if(super.getPosition().y < TERRAIN_HEIGHT){
            upwardsSpeed = 0;
            isInAir = false;
            super.getPosition().y = TERRAIN_HEIGHT;
        }
    }

    private void jump(){
        if (!isInAir) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }
    }

    private void checkInputs(){
        if (Keyboard.isKeyDown(Keyboard.KEY_W)){
            this.currentSpeed = +RUN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)){
            this.currentSpeed = RUN_SPEED;
        } else {
            this.currentSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)){
            this.currentTurnSpeed = -TURN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A)){
           this.currentTurnSpeed = TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            jump();
        }

    }
}
