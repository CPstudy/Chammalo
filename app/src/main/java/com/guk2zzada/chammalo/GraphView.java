package com.guk2zzada.chammalo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class GraphView extends View {
	// 그래프 그리는 

	private float mCenterX; // View의 Center x값
	private float mCenterY; // View의 Center y값
	int left;
	int top;
	int right;
	int bottom;
	int degree;
	private float stroke;
	int mPlus = 0;
	private Paint paint;
	private Paint bg_paint;
	private RectF mBigOval;


	public GraphView(Context context) {
		super(context);
		initPaint();
	}

	public GraphView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	public GraphView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint();
	}

	private void initPaint(){
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setDither(true);
		paint.setColor(0xFFFFFFFF);
		paint.setStrokeWidth(8); // 선 굵기
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.BUTT);


		bg_paint = new Paint(paint.ANTI_ALIAS_FLAG);
		bg_paint.setDither(true);
		bg_paint.setColor(0x20ffffff);
		bg_paint.setStrokeWidth(8); // 선 굵기
		bg_paint.setStyle(Paint.Style.STROKE);
		bg_paint.setStrokeJoin(Paint.Join.ROUND);
		bg_paint.setStrokeCap(Paint.Cap.BUTT);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mBigOval = new RectF(left, top, mCenterX, mCenterY);
		canvas.drawArc(mBigOval, 0, 360, false, bg_paint);
		canvas.drawArc(mBigOval, -90, mPlus, false, paint);
		if(mPlus < degree) {
			mPlus++;
			invalidate();
		} else {
			mPlus = 0;
		}
	}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		left = this.getPaddingLeft();
		top = this.getPaddingTop();
		right = this.getPaddingRight();
		bottom = this.getPaddingBottom();

		mCenterX = (float)w - right;
		mCenterY = (float)h - bottom;
	}

	public int setGraph(float a, float b) {
		degree = (int)((a / b) * 360);
		return (int)((a / b) * 100);
	}
}
