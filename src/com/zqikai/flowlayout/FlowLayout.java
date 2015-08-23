package com.zqikai.flowlayout;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {
	

	private int horizontalSpacing = 15;
	private int verticalSpacing = 15;
	private ArrayList<Line> mLineList;

	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FlowLayout(Context context) {
		super(context);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		//**��ȡ�ؼ����ܿ��
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		
		/**
		 * ��Ļȥ��padding �Ŀ��
		 */
		int noPaddingWidth = width - paddingLeft - paddingRight;
		
		
		mLineList = new ArrayList<Line>();
		
		Line line = null;
		for (int i = 0; i < getChildCount(); i++) {
			View childView = getChildAt(i);
			childView.measure(0, 0);//�� 0, 0 �ȷǷ����ݻ���ݿռ���ʵ�Ŀ�߲���, �Ϸ��������Ƿǳ����-->�󵽼�ʮ���ϰ���;
			
			if (line == null) {
				line = new Line();
			}
			
			if (line.getViewLiew().size()==0) { //���ĳһ�л�û������κ�View����,��ֱ�ӷŽ�ȥ
				line.addView(childView);
			}else if (line.getWidth()+horizontalSpacing+childView.getMeasuredWidth()>noPaddingWidth) {              
				//���ĳһ�������һ��View��Ŀ�ȴ��� noPaddingWidth��,˵���û�����,�����������ݵĶ���洢��������
				mLineList.add(line);
				
				//�����µ�һ��
				line = new Line();
				//��Ϊ���ǻ��еĵ�һ������ֱ�ӷŽ��µ�һ�м���
				line.addView(childView);
			}else{//�������ĳ�еĵ�һ������,���ҿ��ԷŽ�ĳ��ʱ,ֱ�Ӽӽ�ȥ����
				
				line.addView(childView);
			}
			
			//��������һ��childView, ˵���Ѿ��������һ��,��line���뵽������
			if(i == getChildCount()-1){
				mLineList.add(line);
			}
		}
		//forѭ�������������Ѿ������е��ж��Ѿ��������,Ȼ�����ǵõ��ռ�ĸ߶�
		
		int height = getPaddingTop()+getPaddingBottom();
		
		for (Line line2 : mLineList) {
//			height += line2.height;
			height += line2.getHeight();
			
		}
		height += (mLineList.size()-1)*verticalSpacing;
		
		/**
		 * �򸸿ؼ����� ��ô��Ŀռ�
		 */
		
		setMeasuredDimension(width, height);
//		setMeasuredDimension(noPaddingWidth, height);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		//��ÿһ�е����ݰڷŵ�ָ��λ��,   ���ȼ�����ߵ�padding �ұߵ�padding paddingTop paddingBottom
		
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();
		int paddingTop = getPaddingTop();
		
		//����lineListȡ��ÿһ��line�е�textView�������ڷŵ���Ӧλ��
		
		for (int i = 0; i < mLineList.size(); i++) {
			
			//��ȡĳһ��
			Line line = mLineList.get(i);
			
			//paddingTop���������������Ӷ�����
			if(i>0){
				//��ȥ��һ��֮���ÿ�е�top��������һ�ж�һ���иߺ�verticalSpacing
				paddingTop += line.getHeight()+verticalSpacing;
			}
			
			
			//��ȡĳһ���е�View�����б�
			ArrayList<View> viewLiew = line.getViewLiew();
			
			//1��ȡʣ��Ŀհ׿��
			int freeSpacing = getMeasuredWidth() - line.getWidth() - getPaddingLeft() - getPaddingRight();
			//2����ʣ��Ŀհ׿���ÿ���ֶ���
			int perSpacing = freeSpacing/viewLiew.size();
			
			
			for (int j = 0; j < viewLiew.size(); j++) {
				
				View childView = viewLiew.get(j);
				//3���¶�childView���в���,����perSpacing
				int width = perSpacing + childView.getMeasuredWidth();
				int makeMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
				childView.measure(makeMeasureSpec, 0);
				
				if (j==0) {
					childView.layout(paddingLeft, paddingTop, 
							paddingRight+childView.getMeasuredWidth(), 
							paddingTop+childView.getMeasuredHeight());
				}else{
					View preChildView = viewLiew.get(j-1);
					
					childView.layout(preChildView.getRight()+horizontalSpacing, preChildView.getTop(), 
							preChildView.getRight()+childView.getMeasuredWidth()+horizontalSpacing,
							preChildView.getBottom());
				}
				
			}
		}
		
	}
	
	
	 class Line{
		
		private int width;
		private int height;
		
		private ArrayList<View> viewList;
		
		public Line(){
			viewList = new ArrayList<View>();
		}
		
		public ArrayList<View> getViewLiew(){
			return viewList;
		}
		
		public int getWidth(){
			return width;
		}
		
		public int getHeight(){
			return height;
		}
		
		public void addView(View view){
			
			if (!viewList.contains(view)) {
				if (viewList.size()==0) {
					width += view.getMeasuredWidth();
				}else{
					width += view.getMeasuredWidth()+horizontalSpacing;
				}
				//��ȡchildView�����ĸ�
				height = Math.max(height, view.getMeasuredHeight());
				
				viewList.add(view);
			}
		}
		
	}

}

