package com.jason.usedcar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SideBar extends View {

	// �Ƿ���
	private boolean showBkg = false;
	OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	public static String[] b = {"#", "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };
	// ѡ���ֵ
	int choose = -1;
	// ����
	Paint paint = new Paint();

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideBar(Context context) {
		super(context);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// �����崦�ڵ��״̬�ͽ����ı���ɫ����Ϊ��ɫ
		if (showBkg) {
			canvas.drawColor(Color.parseColor("#40000000"));
		}
		// ��ã֣����ĸ�
		int height = getHeight();
		// ��ã֣����Ŀ�
		int width = getWidth();
		// ����ó�ÿһ�������ŵĸ߶�
		int singleHeight = height / b.length;
		for (int i = 0; i < b.length; i++) {
			// ���þ��
			paint.setAntiAlias(true);
			// ���������С
			paint.setTextSize(18);
			// ����������26����ĸ�е�����һ����Ⱦ�
			if (i == choose) {
				// ���Ƶ�����������ɫΪ��ɫ
				paint.setColor(Color.BLUE);
				paint.setFakeBoldText(true);
			}
			// �õ������X���
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			// �õ������Y���
			float yPos = singleHeight * i + singleHeight + 3;
			// ��������Ƶ������
			canvas.drawText(b[i], xPos, yPos, paint);
			// ��ԭ����
			paint.reset();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// �õ������״̬
		final int action = event.getAction();
		// �����Y���
		final float y = event.getY();

		final int oldChoose = choose;
		// ����
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		// �õ���ǰ��ֵ
		final int c = (int) (y / getHeight() * b.length);
		switch (action) {
		// ����
		case MotionEvent.ACTION_DOWN:
			// ����������Ϊtrue
			showBkg = true;
			if (oldChoose != c && listener != null) {
				if (c > 0 && c < b.length) {
					listener.onTouchingLetterChanged(b[c]);
					choose = c;
					// ˢ�½���
					invalidate();
				}
			}
			break;
		// �ƶ�
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != c && listener != null) {
				if (c > 0 && c < b.length) {
					listener.onTouchingLetterChanged(b[c]);
					choose = c;
					invalidate();
				}
			}
			break;
		// �ɿ� ��ԭ��� ��ˢ�½���
		case MotionEvent.ACTION_UP:
			showBkg = false;
			choose = -1;
			invalidate();
			break;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

}
