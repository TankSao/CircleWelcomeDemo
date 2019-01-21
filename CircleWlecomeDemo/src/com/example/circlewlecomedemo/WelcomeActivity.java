package com.example.circlewlecomedemo;

import com.example.circlewlecomedemo.listener.CallEndListener;
import com.example.circlewlecomedemo.view.CircleWelComeView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.TextView;

public class WelcomeActivity extends Activity{
	private CircleWelComeView circleView;
	private TextView start;
	private CallEndListener listener = new CallEndListener() {
		
		@Override
		public void doEnd() {
			// TODO �Զ����ɵķ������
			startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
			finish();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		circleView = (CircleWelComeView) findViewById(R.id.circleView);
		circleView.setCallEndListener(listener);
		start = (TextView) findViewById(R.id.start);
		start.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO �Զ����ɵķ������
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					circleView.start();
					break;
				case MotionEvent.ACTION_UP:
					circleView.stop();
					break;
				default:
					break;
				}
				return true;
			}
		});
	}
}
