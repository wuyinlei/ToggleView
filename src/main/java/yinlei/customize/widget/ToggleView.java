package yinlei.customize.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wuyin on 2016/5/16.
 * <p/>
 * Android 界面的绘制流程
 * <p/>
 * measure（测量）   -->   layout（摆放）   -->   draw（绘制）
 * <p/>
 * onMeasure       -->    onLayout       -->    onDraw    需要重写的方法
 * <p/>
 * View中
 * <p/>
 * onMeasure(在这个方法中指定自己的宽高)onSizeChanged（这个方法也是可以的）
 * onLayout（摆放自己的位置）
 * onDraw(绘制)
 */
public class ToggleView extends View {

    //选择图片
    private Bitmap slideButton;

    //背景
    private Bitmap switchBackground;

    //画笔
    private Paint mPaint;

    //开关状态  默认关闭
    private boolean mSwitchState = false;

    /**
     * 用于代码创建控件
     *
     * @param context
     */
    public ToggleView(Context context) {
        this(context, null);
    }


    /**
     * 用于在xml中使用，可以指定自定义属性
     *
     * @param context
     * @param attrs
     */
    public ToggleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 用于在xml中,如果指定了样式，就走此构造函数
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ToggleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint = new Paint();
    }


    /**
     * 设置背景图片
     *
     * @param switch_background
     */
    public void setSwitchBackground(int switch_background) {
        switchBackground = BitmapFactory.decodeResource(getResources(), switch_background);
    }

    /**
     * 设置滑块的图片资源
     *
     * @param slide_button
     */
    public void setSlideButtonResource(int slide_button) {
        slideButton = BitmapFactory.decodeResource(getResources(), slide_button);
    }

    /**
     * 设置开关状态
     *
     * @param state
     */
    public void setSwitchState(boolean state) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(switchBackground.getWidth(), switchBackground.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //1、绘制背景
        canvas.drawBitmap(switchBackground, 0, 0, mPaint);


        //根据开关状态直接设置图片的位置
        if (mSwitchState) {
            int newLeft = switchBackground.getWidth() - slideButton.getWidth();
            canvas.drawBitmap(slideButton, newLeft, 0, mPaint);
        } else {
            //2、绘制滑块
            canvas.drawBitmap(slideButton, 0, 0, mPaint);
        }

    }
}
