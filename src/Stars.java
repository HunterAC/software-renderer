public class Stars {
    private final float spread;
    private final float speed;
    private final float[] starX;
    private final float[] starY;
    private final float[] starZ;

    public Stars(int numStars, float spread, float speed) {
        this.spread = spread;
        this.speed = speed;
        this.starX = new float[numStars];
        this.starY = new float[numStars];
        this.starZ = new float[numStars];

        for (int i = 0; i < starX.length; i++) {
            initStar(i);
        }
    }

    private void initStar(int i) {
        starX[i] = (2 * (float)(Math.random() - 0.5f)) * spread;
        starY[i] = (2 * (float)(Math.random() - 0.5f)) * spread;
        starZ[i] = (2 * (float)(Math.random() + 0.00001f) * spread);
    }

    public void updateAndRender(RenderContext target, float delta) {
        target.clear((byte)0x00);

        float halfWidth = target.getWidth() / 2.0f;
        float halfHeight = target.getHeight() / 2.0f;
        for (int i = 0; i < starX.length; i++) {
            starZ[i] -= delta * speed;

            if (starZ[i] <= 0) {
                initStar(i);
            }

            int x = (int)((starX[i]/starZ[i]) * halfWidth + halfWidth);
            int y = (int)((starY[i]/starZ[i]) * halfHeight + halfHeight);

            if (x <= 0 || x >= target.getWidth() || y <= 0 || y >= target.getHeight()) {
                initStar(i);
            } else {
                target.drawPixel(x, y, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF);
            }
        }
    }
}
