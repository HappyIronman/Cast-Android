package tool.xpy.opengl_test.work.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by root on 15-10-10.
 */
public class BufferUtil {

    public static FloatBuffer toFloatBuffer(int cap){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(cap * 4);
        byteBuffer.order(ByteOrder.nativeOrder());

        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.position(0);

        return floatBuffer;
    }

    public static FloatBuffer getRepeatBuffer(float h_times, float v_times, boolean r){
        FloatBuffer floatBuffer = toFloatBuffer(12);
        floatBuffer.position(0);
        if(r){
            floatBuffer.put(new float[]{
                    0, 0,
                    0, v_times,
                    h_times, 0,
                    h_times, v_times,
            });
        } else {
            floatBuffer.put(new float[]{
                    0, 0,
                    h_times, 0,
                    0, v_times,
                    h_times, v_times,
            });
        } //两种不同的纹理拼接方法
        floatBuffer.position(0);
        return floatBuffer;
    }

}
