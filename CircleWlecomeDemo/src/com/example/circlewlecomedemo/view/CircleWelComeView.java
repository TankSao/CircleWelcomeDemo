package com.example.circlewlecomedemo.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.circlewlecomedemo.R;
import com.example.circlewlecomedemo.listener.CallEndListener;

public class CircleWelComeView  extends View {

    /** ��ɢԲȦ��ɫ */
    private int outColor = getResources().getColor(R.color.outColor);
    /** ԲȦ������ɫ */
    private int inColor = getResources().getColor(R.color.inColor);
    /** ����Բ�뾶 */
    private float radius = 150;
    /** ��ɢԲ��� */
    private int width = 3;
    /** ����� */
    private Integer maxWidth = 255;
    /** ��ɢ�ٶ� */
    private int speed = 5;
    /** �Ƿ�������ɢ�� */
    private boolean mIsDiffuse = false;
    // ͸���ȼ���
    private List<Integer> mAlphas = new ArrayList<Integer>();
    // ��ɢԲ�뾶����
    private List<Integer> mWidths = new ArrayList<Integer>();
    private Paint mPaint;
    
    //������������
    private CallEndListener listener;

    public CircleWelComeView(Context context) {
        this(context, null);
    }

    public CircleWelComeView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CircleWelComeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView, defStyleAttr, 0);
        outColor = a.getColor(R.styleable.CircleView_circle_out_color, outColor);
        inColor = a.getColor(R.styleable.CircleView_circle_in_Color, inColor);
        radius = a.getFloat(R.styleable.CircleView_circle_radius, radius);
        width = a.getInt(R.styleable.CircleView_circle_width, width);
        maxWidth = a.getInt(R.styleable.CircleView_max_width, maxWidth);
        speed = a.getInt(R.styleable.CircleView_speed, speed);
        a.recycle();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mAlphas.add(255);
        mWidths.add(0);
    }

    @Override
    public void invalidate() {
        if(hasWindowFocus()){
            super.invalidate();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus){
            invalidate();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        // ������ɢԲ
        mPaint.setColor(outColor);
        for (int i = 0; i < mAlphas.size(); i ++) {
            // ����͸����
            Integer alpha = mAlphas.get(i);
            mPaint.setAlpha(alpha);
            // ������ɢԲ
            Integer width = mWidths.get(i);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius + width, mPaint);

            if(alpha > 0 && width < maxWidth){
                mAlphas.set(i, alpha - speed > 0 ? alpha - speed : 1);
                mWidths.set(i, width + speed);
            }
        }
        // �жϵ���ɢԲ��ɢ��ָ�����ʱ�������ɢԲ
        if (mWidths.get(mWidths.size() - 1) >= maxWidth / width) {
            mAlphas.add(255);
            mWidths.add(0);
        }
        // ����10����ɢԲ��ɾ�������
        if(mWidths.size() >= 10){
            mWidths.remove(0);
            mAlphas.remove(0);
        }

        // ��������Բ
        mPaint.setAlpha(255);
        mPaint.setColor(inColor);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);

        if(mIsDiffuse){
            invalidate();
        }
    }

    /**
     * ��ʼ��ɢ
     */
    public void start() {
        mIsDiffuse = true;
        invalidate();
    }

    /**
     * ֹͣ��ɢ
     */
    public void stop() {
        mIsDiffuse = false;
        mWidths.clear();
        mAlphas.clear();
        mAlphas.add(255);
        mWidths.add(0);
        invalidate();
        if(listener!=null){
        	listener.doEnd();
        }
    }

    /**
     * �Ƿ���ɢ��
     */
    public boolean isDiffuse(){
        return mIsDiffuse;
    }

    /**
     * ������ɢԲ��ɫ
     */
    public void setOutColor(int colorId){
        this.outColor = colorId;
    }

    /**
     * ��������Բ��ɫ
     */
    public void setInColor(int colorId){
        this.inColor = colorId;
    }

    /**
     * ��������Բ�뾶
     */
    public void setRadius(int radius){
        this.radius = radius;
    }

    /**
     * ������ɢԲ���(ֵԽС���Խ��)
     */
    public void setWidth(int width){
        this.width = width;
    }

    /**
     * ���������
     */
    public void setMaxWidth(int maxWidth){
        this.maxWidth = maxWidth;
    }

    /**
     * ������ɢ�ٶȣ�ֵԽ���ٶ�Խ��
     */
    public void setSpeed(int speed){
        this.speed = speed;
    }
    
    /**
     * ���ö�����������
     */
    public void setCallEndListener(CallEndListener listener){
    	this.listener = listener;
    }
}
