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
		
		//**获取控件的总宽度
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		
		/**
		 * 屏幕去掉padding 的宽度
		 */
		int noPaddingWidth = width - paddingLeft - paddingRight;
		
		
		mLineList = new ArrayList<Line>();
		
		Line line = null;
		for (int i = 0; i < getChildCount(); i++) {
			View childView = getChildAt(i);
			childView.measure(0, 0);//传 0, 0 等非法数据会根据空间真实的宽高测量, 合法的数据是非常大的-->大到几十万上百万;
			
			if (line == null) {
				line = new Line();
			}
			
			if (line.getViewLiew().size()==0) { //如果某一行还没有添加任何View对象,则直接放进去
				line.addView(childView);
			}else if (line.getWidth()+horizontalSpacing+childView.getMeasuredWidth()>noPaddingWidth) {              
				//如果某一行再添加一个View后的宽度大于 noPaddingWidth则,说明该换行了,将保存行数据的对象存储到集合中
				mLineList.add(line);
				
				//创建新的一行
				line = new Line();
				//因为这是换行的第一条数据直接放进新的一行即可
				line.addView(childView);
			}else{//如果不是某行的第一个数据,并且可以放进某行时,直接加进去即可
				
				line.addView(childView);
			}
			
			//如果是最后一个childView, 说明已经到了最后一行,将line加入到集合中
			if(i == getChildCount()-1){
				mLineList.add(line);
			}
		}
		//for循环结束后我们已经将所有的行都已经保存好了,然后我们得到空间的高度
		
		int height = getPaddingTop()+getPaddingBottom();
		
		for (Line line2 : mLineList) {
//			height += line2.height;
			height += line2.getHeight();
			
		}
		height += (mLineList.size()-1)*verticalSpacing;
		
		/**
		 * 向父控件申请 这么大的空间
		 */
		
		setMeasuredDimension(width, height);
//		setMeasuredDimension(noPaddingWidth, height);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		//将每一行的数据摆放到指定位置,   首先计算左边的padding 右边的padding paddingTop paddingBottom
		
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();
		int paddingTop = getPaddingTop();
		
		//遍历lineList取出每一行line中的textView对象将它摆放到对应位置
		
		for (int i = 0; i < mLineList.size(); i++) {
			
			//获取某一行
			Line line = mLineList.get(i);
			
			//paddingTop会随着行数的增加而增加
			if(i>0){
				//除去第一行之后的每行的top，都比上一行多一个行高和verticalSpacing
				paddingTop += line.getHeight()+verticalSpacing;
			}
			
			
			//获取某一行中的View对象列表
			ArrayList<View> viewLiew = line.getViewLiew();
			
			//1获取剩余的空白宽度
			int freeSpacing = getMeasuredWidth() - line.getWidth() - getPaddingLeft() - getPaddingRight();
			//2计算剩余的空白可以每个分多少
			int perSpacing = freeSpacing/viewLiew.size();
			
			
			for (int j = 0; j < viewLiew.size(); j++) {
				
				View childView = viewLiew.get(j);
				//3重新对childView进行测量,加上perSpacing
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
				//获取childView的最大的高
				height = Math.max(height, view.getMeasuredHeight());
				
				viewList.add(view);
			}
		}
		
	}

}

