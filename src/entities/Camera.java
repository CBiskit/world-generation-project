package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by c1547497 on 26/12/2016.
 */
public class Camera {

    private float distanceFromPlayer = 70;
    private float angleAroundPlayer = 0;

    private final float CAMERA_SPEED = 5f;

    private Vector3f position = new Vector3f(0,0,0);
    private float pitch = 20;
    private float yaw;
    private float roll;

    private Player player;

    public Camera(Player player){
        this.player = player;
        this.position = new Vector3f(0, 0, 0);
    }

    public void move(){
        calculateZoom();
        calculatePitch();
        calculateAngle();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 -(player.getRotY() + angleAroundPlayer);
        if (pitch < 5 ){ pitch = 5; };
        if (pitch > 90){ pitch = 90; };
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

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance){
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + verticalDistance;
        if (position.y < 0){ position.y = 0; }
    }

    private float calculateHorizontalDistance(){
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance(){
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom(){
        float zoomLevel = Mouse.getDWheel() * 0.1f;
        distanceFromPlayer -= zoomLevel;
    }

    private void calculatePitch(){
        if (Mouse.isButtonDown(1)){
            float pitchChange = Mouse.getDY() * 0.1f;
            pitch -= pitchChange;
        }
    }

    private void calculateAngle(){
        if (Mouse.isButtonDown(0)) {
            float angleChange = Mouse.getDX() * 0.1f;
            angleAroundPlayer -= angleChange;
            yaw += angleChange;
        }
    }






}
