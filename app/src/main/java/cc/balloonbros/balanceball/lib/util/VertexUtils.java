package cc.balloonbros.balanceball.lib.util;

public class VertexUtils {
    /**
     * Generate vertices as a float array for a rectangle polygon.
     * The vertex is placed counterclockwise from the top of the left
     * to the top of the right.
     *
     * (1)    w     (4)
     *  .------------.
     *  |            |
     *  |h           |
     *  |            |
     *  .------------.
     * (2)          (3)
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @param z z coordinate.
     * @param w Width of the rectangle.
     * @param h Height of the rectangle.
     * @return float array.
     */
    public static float[] generateRectangle(float x, float y, float z, float w, float h) {
        return new float[] {
         // x-axis y-axis z-axis
            x,     y + h, z,     // The top of the left
            x,     y,     z,     // The bottom of the left
            x + w, y,     z,     // The bottom of the right
            x + w, y + h, z      // The top of the right
        };
    }
}
