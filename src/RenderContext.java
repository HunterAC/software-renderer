public class RenderContext extends Bitmap {

    private final int[] scanBuffer;

    public RenderContext(int width, int height) {
        super(width, height);
        scanBuffer = new int[height * 2];
    }

    public void drawScanBuffer(int yCoord, int xMin, int xMax) {
        scanBuffer[yCoord * 2] = xMin;
        scanBuffer[yCoord * 2 + 1] = xMax;
    }

    public void fillShape(int yMin, int yMax) {
        for (int y = yMin; y < yMax; y++) {
            int xMin = scanBuffer[y * 2];
            int xMax = scanBuffer[y * 2 + 1];

            for (int x = xMin; x < xMax; x++) {
                drawPixel(x, y, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF);
            }
        }
    }

    public void fillTriangle(Vertex v1, Vertex v2, Vertex v3) {
        var screenSpaceTransform = new Matrix4f().initScreenSpaceTransform(getWidth()/2, getHeight()/2);
        var min = v1.transform(screenSpaceTransform).perspectiveDivide();
        var mid = v2.transform(screenSpaceTransform).perspectiveDivide();
        var max = v3.transform(screenSpaceTransform).perspectiveDivide();

        if (max.getY() < mid.getY()) {
            var temp = max;
            max = mid;
            mid = temp;
        }

        if (mid.getY() < min.getY()) {
            var temp = mid;
            mid = min;
            min = temp;
        }

        if (max.getY() < mid.getY()) {
            var temp = max;
            max = mid;
            mid = temp;
        }

        float area = min.triangleArea(max, mid);
        int handedness = area >= 0 ? 1 : 0;

        scanConvertTriangle(min, mid, max, handedness);
        fillShape((int)min.getY(), (int)max.getY());
    }

    public void scanConvertTriangle(Vertex minY, Vertex midY, Vertex maxY, int handedness) {
        scanConvertLine(minY, maxY, 0 + handedness);
        scanConvertLine(minY, midY, 1 - handedness);
        scanConvertLine(midY, maxY, 1 - handedness);
    }

    private void scanConvertLine(Vertex minY, Vertex maxY, int side) {
        int yStart = (int)minY.getY();
        int yEnd = (int)maxY.getY();
        int xStart = (int)minY.getX();
        int xEnd = (int)maxY.getX();

        int yDist = yEnd - yStart;
        int xDist = xEnd - xStart;

        if (yDist <= 0) {
            return;
        }

        float xStep = (float)xDist / (float)yDist;
        float curX = (float)xStart;

        for (int j = yStart; j < yEnd; j++) {
            scanBuffer[j * 2 + side] = (int)curX;
            curX += xStep;
        }
    }
}
