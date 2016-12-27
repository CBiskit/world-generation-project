package textures;

import java.nio.ByteBuffer;

/**
 * Created by c1547497 on 27/12/2016.
 */
public class TextureData {

    private int width;
    private int height;
    private ByteBuffer buffer;

    public TextureData(ByteBuffer buffer, int width, int height) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }
}
