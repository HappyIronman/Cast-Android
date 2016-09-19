package tool.xpy.opengl_test.work.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

/**
 * Created by root on 15-11-11.
 */
public class TextCanvas {

    public static Bitmap getTextBitmap(String text, int width, int height){
        Bitmap tBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(tBitmap);
        canvas.drawColor(Color.TRANSPARENT);
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(20.0F);
        textPaint.setColor(Color.RED);
        StaticLayout sl= new StaticLayout(text, textPaint, tBitmap.getWidth()-8,
                Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        //canvas.translate(6, 40);
        sl.draw(canvas);
        return convert(tBitmap);
    }

    private static Bitmap convert(Bitmap a) {
        int w = a.getWidth();
        int h = a.getHeight();

        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newb);
        Matrix m = new Matrix();

        m.postScale(1, -1);

        Bitmap new2 = Bitmap.createBitmap(a, 0, 0, w, h, m, true);

        cv.drawBitmap(new2, new Rect(0, 0, new2.getWidth(), new2.getHeight()), new Rect(0, 0, w, h), null);

        return newb;
    }

}
