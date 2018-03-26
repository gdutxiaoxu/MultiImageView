package com.xj.shapeview;

/**
 * @author meitu.xujun  on 2018/3/12
 * @version 0.1
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 绘制多边形
 */
public class PathMultiView extends View {

    private static final String TAG = "PathMultiView";
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    private Paint paint;
    private Path path;
    private int width,height;
    private Context mContext;
    private static final int  mDefSides = 5;;
    private int mSides;

    public PathMultiView(Context context) {
        this(context,null);
    }
    public PathMultiView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public PathMultiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData(attrs,defStyleAttr);
        initPaint();
    }

    private void initData(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.PathMultiView, defStyleAttr, 0);
        mSides = a.getInt(R.styleable.PathMultiView_pmv_sides, mDefSides);
        Log.i(TAG, "initData: mSides=" +mSides);
        a.recycle();
    }

    private void initPaint() {
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(width, widthMeasureSpec), getDefaultSize(height, heightMeasureSpec));
        int widthSize = getMeasuredWidth();
        int heightSize = getMeasuredHeight();
        // get the min
        widthSize=Math.min(widthSize,heightSize);
        //宽度等同
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawMultShape(canvas,mSides,getMeasuredWidth()/2);
    }
    /**
     * @param canvas 画布
     * @param count 绘制几边形
     * @param radius //外圆的半径
     */
    public void drawMultShape(Canvas canvas,int count,float radius){

        canvas.translate(radius,radius);//
        int angle = 360 / count;
        canvas.rotate(90- angle);
        if(count<5){
            return;
        }
        for (int i=0;i<count;i++){
            if (i==0){
                path.moveTo(radius*cos(angle *i),radius*sin(angle *i));//绘制起点
            }else{
                path.lineTo(radius*cos(angle *i),radius*sin(angle *i));
            }
        }
        paint.setStrokeWidth(3);
        path.close();
        paint.setColor(Color.GREEN);
        canvas.drawPath(path,paint);
        //因为我下面不再绘制内容了 所以画布就不恢复了
    }
    float sin(int num){
        return (float) Math.sin(num*Math.PI/180);
    }
    float cos(int num){
        return (float) Math.cos(num*Math.PI/180);
    }
}