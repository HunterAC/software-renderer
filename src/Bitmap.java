import java.util.Arrays;

public class Bitmap {
    private final int width;
    private final int height;
    private final byte components[];

    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;
        this.components = new byte[width * height * 4];
    }

    public void clear(byte shade) {
        Arrays.fill(components, shade);
    }

    public void drawPixel(int x, int y, byte alpha, byte blue, byte green, byte red) {
        int place = ((width * y) + x) * 4;

        components[place] = alpha;
        components[place + 1] = blue;
        components[place + 2] = green;
        components[place + 3] = red;
    }

    public void copyToByteArray(byte[] dest) {
        for (int i = 0; i < width * height; i++) {
            //Copying from ABGR to BGR
            dest[i * 3] = components[(i * 4) + 1];
            dest[i * 3 + 1] = components[(i * 4) + 2];
            dest[i * 3 + 2] = components[(i * 4) + 3];
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

//This method was needed when implementing the bitmap with char then converting to a single int
//    public void copyToIntArray(int[] dest) {
//        for (int i = 0; i < width * height; i++) {
//            //Bit shift the components to store all of them in a single integer value
//            int a = ((int)components[i * 4]) << 24;
//            int r = ((int)components[i * 4 + 1]) << 16;
//            int g = ((int)components[i * 4 + 2]) << 8;
//            int b = ((int)components[i * 4 + 3]);
//
//            dest[i] = a | r | g | b;
//        }
//    }
}
