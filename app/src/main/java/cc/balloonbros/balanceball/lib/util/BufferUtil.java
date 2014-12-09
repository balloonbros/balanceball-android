package cc.balloonbros.balanceball.lib.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Buffer conversion utility class.
 */
public class BufferUtil {
    /**
     * Convert the data from a float array to a FloatBuffer.
     * @param data Target data as a float array.
     * @return Converted data as a FloatBuffer.
     */
    public static FloatBuffer convert(float[] data) {
        ByteBuffer bb = ByteBuffer.allocateDirect(data.length * 4);
        bb.order(ByteOrder.nativeOrder());

        FloatBuffer floatBuffer = bb.asFloatBuffer();
        floatBuffer.put(data);
        floatBuffer.position(0);

        return floatBuffer;
    }

    /**
     * Convert the data from a short array to a ShortBuffer.
     * @param data Target data as a short array.
     * @return Converted data as a ShortBuffer.
     */
    public static ShortBuffer convert(short[] data) {
        ByteBuffer bb = ByteBuffer.allocateDirect(data.length * 2);
        bb.order(ByteOrder.nativeOrder());

        ShortBuffer shortBuffer = bb.asShortBuffer();
        shortBuffer.put(data);
        shortBuffer.position(0);

        return shortBuffer;
    }

    public static void set(FloatBuffer buffer, float[] data) {
        buffer.position(0);
        buffer.put(data);
        buffer.position(0);
    }
}
