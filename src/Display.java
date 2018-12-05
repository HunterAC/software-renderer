import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.image.DataBufferByte;

public class Display extends Canvas {
    private final JFrame frame;
    private final RenderContext frameBuffer;
    private final BufferedImage displayImage;
    private final byte[] displayComponents;
    private final BufferStrategy bufferStrategy;
    private final Graphics graphics;

    public Display(int width, int height, String title) {
        var size = new Dimension(width, height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        frameBuffer = new RenderContext(width, height);
        displayImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        displayComponents = ((DataBufferByte)displayImage.getRaster().getDataBuffer()).getData();

        frame = new JFrame();
        frame.add(this);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(title);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        createBufferStrategy(1);
        bufferStrategy = getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();
    }

    public void swapBuffers() {
        frameBuffer.copyToByteArray(displayComponents);
        graphics.drawImage(displayImage, 0, 0, frameBuffer.getWidth(), frameBuffer.getHeight(), null);
        bufferStrategy.show();
    }

    public RenderContext getFrameBuffer() {
        return this.frameBuffer;
    }
}
