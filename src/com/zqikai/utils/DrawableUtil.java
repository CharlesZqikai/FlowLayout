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
	 * ����Բ��ͼƬ�ķ���
	 * @param argb  ��ɫֵ
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
	 * ����״̬ѡ����
	 * @param normal   ��ͨ״̬��ͼƬ
	 * @param pressed   ����״̬��ͼƬ
	 * @return
	 */
	public static Drawable getDrawableSelector(Drawable normal,Drawable pressed){
		
		StateListDrawable stateListDrawable = new StateListDrawable();
		stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
		stateListDrawable.addState(new int[]{}, normal);
		
		return stateListDrawable;
	}

}
