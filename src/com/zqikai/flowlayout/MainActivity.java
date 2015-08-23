package com.zqikai.flowlayout;

import java.util.Random;

import com.zqikai.utils.ColorUtil;
import com.zqikai.utils.DrawableUtil;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private FlowLayout mFlowLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("大家好");
		
		ScrollView scrollView = new ScrollView(this);
		
		mFlowLayout = new FlowLayout(this);
		mFlowLayout.setPadding(8, 8, 8, 8);
		
		final Random random = new Random();
		

		for (int i = 0; i <100; i++) {
			final TextView textView = new TextView(this);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			int nextInt = random.nextInt(1000) + 10;
			
			textView.setText(nextInt+"");
			

			Drawable normal = DrawableUtil.getGradientDrawable(ColorUtil.getRandomColor());
			Drawable pressed = DrawableUtil.getGradientDrawable(Color.GRAY);
			
			Drawable selector = DrawableUtil.getDrawableSelector(normal, pressed);
			
			textView.setBackgroundDrawable(selector);
			
			textView.setGravity(Gravity.CENTER_HORIZONTAL);
			textView.setPadding(5, 2, 5, 2);
			
			textView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(MainActivity.this, textView.getText().toString(), 0).show();;
				}
			});
						
			mFlowLayout.addView(textView);
		}

		scrollView.addView(mFlowLayout);
		
		setContentView(scrollView);
		
	}


}
