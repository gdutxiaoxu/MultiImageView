package com.xj.shapeview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

public  class ClipHelper  {



    /**
     * 创建目标图像
     *
     * @param width  图像宽
     * @param height 图像高
     * @return bitmap
     */
    public static Bitmap createMask(Path path,Paint paint,int width, int height) {
        final Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(mask);
        canvas.drawPath(path, paint);
        return mask;
    }





    public static void setCirclePath(Path path, int width, int height) {
        //xy为圆的圆心 radius为圆的半径 Diection.CW 顺时针方向
        path.addCircle(width / 2f, height / 2f, Math.min(width / 2f, height / 2f), Path.Direction.CW);
    }

    private static final String TAG = "ClipHelper";

    public static void setRectangle(Path path, RectF rectF,float radius){
        path.addRoundRect(rectF,radius,radius, Path.Direction.CW);
    }

    public static void setPolygon(Canvas canvas,Path path,float radius,int sides,int rotateAngles){




    }

    static float sin(int num){
        return (float) Math.sin(num*Math.PI/180);
    }
    static float cos(int num){
        return (float) Math.cos(num*Math.PI/180);
    }

    public static void setPolygon(Path path, RectF rectF, int sides,float rotateAngle) {
        Log.i(TAG, "setPolygon: rotateAngle=" +rotateAngle);
        float r = (rectF.right - rectF.left) / 2;
        float mX = (rectF.right + rectF.left) / 2;
        float my = (rectF.top + rectF.bottom) / 2;

        for (int i = 0; i <sides; i++) {
            // - 0.5 : Turn 90 ° counterclockwise
            float alpha = Double.valueOf(((2f / sides) * i - 0.5) * Math.PI).floatValue();
            float nextX = mX + Double.valueOf(r * Math.cos(alpha)).floatValue();
            float nextY = my + Double.valueOf(r * Math.sin(alpha)).floatValue();
            if (i == 0) {
                path.moveTo(nextX, nextY);
            } else {
                path.lineTo(nextX, nextY);
            }
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(rotateAngle,mX,my);
        path.transform(matrix);
    }
}
