public class Main {

    public static void main(String[] args) {
        var display = new Display(800, 600, "Software Renderer");
        var target = display.getFrameBuffer();
        var stars = new Stars(4096, 56.0f, 20.0f);

        var minY = new Vertex(-1, -1, 0);
        var midY = new Vertex(0, 1, 0);
        var maxY = new Vertex(1, -1, 0);

        var projection = new Matrix4f()
                .initPerspective((float)Math.toRadians(70.0f),
                        (float)target.getWidth()/(float)target.getHeight(),
                        0.1f,
                        1000.0f);

        float rotations = 0.0f;
        long prevTime = System.nanoTime();
        while (true) {
            long currTime = System.nanoTime();
            float delta = (float)((currTime - prevTime) / 1000000000.0);
            prevTime = currTime;

            //stars.updateAndRender(target, delta);

            rotations += delta;
            var translation = new Matrix4f().initTranslation(0.0f, 0.0f, 3.0f);
            var rotation = new Matrix4f().initRotation(0.0f, rotations, 0.0f);
            var transform = projection.mult(translation.mult(rotation));;

            target.clear((byte)0x00);

            target.fillTriangle(maxY.transform(transform), minY.transform(transform), midY.transform(transform));

            display.swapBuffers();
        }
    }
}
