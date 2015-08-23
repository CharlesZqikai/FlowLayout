package com.zqikai.utils;

import java.util.Random;

import android.graphics.Color;

/**
 * 用于生成随机好看颜色的工具类
 * @author zqk
 *
 */
public class ColorUtil {
	
	public static int getRandomColor(){
		
		Random random = new Random();
		
		int red = random.nextInt(200)+30;
		int gre = random.nextInt(200)+30;
		int blu = random.nextInt(200)+30;
		
		return Color.rgb(red, gre, blu);
	}

}
