package com.zqikai.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * 
 * @author zqk
 *
 */
public class DrawableUtil {
	
	/**
	 * 生成圆角图片的方法
	 * @param argb  颜色值
	 * @return
	 */
	public static Drawable getGradientDrawable(int argb){
		
		GradientDrawable gradientDrawable = new GradientDrawable();
		
		gradientDrawable.setCornerRadius(5);
		gradientDrawable.setColor(argb);
		
		gradientDrawable.setShape(GradientDrawable.RECTANGLE);
		
		return gradientDrawable;
	}
	
	/**
	 * 生成状态选择器
	 * @param normal   普通状态的图片
	 * @param pressed   按下状态的图片
	 * @return
	 */
	public static Drawable getDrawableSelector(Drawable normal,Drawable pressed){
		
		StateListDrawable stateListDrawable = new StateListDrawable();
		stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
		stateListDrawable.addState(new int[]{}, normal);
		
		return stateListDrawable;
	}

}
