package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by c1547497 on 26/12/2016.
 */
public class Camera {

    private final float CAMERA_SPEED = 5f;

    private Vector3f position = new Vector3f(0,0,0);
    private float pitch;
    private float yaw;
    private float roll;

    public void move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.z-=CAMERA_SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x+=CAMERA_SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x-=CAMERA_SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.z+=CAMERA_SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            position.y+=CAMERA_SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            position.y-=CAMERA_SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            yaw -= CAMERA_SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            yaw +=CAMERA_SPEED;
        }


    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
