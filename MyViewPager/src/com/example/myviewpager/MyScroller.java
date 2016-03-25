package com.example.myviewpager;

import android.content.Context;
import android.os.SystemClock;

/**
 * ���Ի����Ĺ�����
 * @author Administrator
 *
 */
public class MyScroller {
	private Context context;
	private int startX;
	private int startY;
	private int distanceX;
	private int distanceY;
	private long startTime;
	private boolean isFinish;
	private long duration;   //Ĭ�ϻ���ʱ��
	private int currX;   //��ǰx����
	private int currY;   //��ǰy����
	
	
	
	
	public MyScroller(Context context) {
		
		this.context = context;
	}

	/**
	 * ��ʼ����������
	 * @param startX
	 * @param startY
	 * @param distanceX
	 * @param distanceY
	 */
	public void startScroll(int startX,int startY,int distanceX,int distanceY) {
		this.startX = startX;
		this.startY = startY;
		this.distanceX = distanceX;
		this.distanceY = distanceY;
		this.startTime = SystemClock.uptimeMillis();
		this.isFinish = false;
		this.duration = 500;
	}
	
	/**
	 * ��ʼ��������������Ϊ�ⲿ�ӿڹ��ͻ��˿�ʼ���们��
	 * @param startX
	 * @param startY
	 * @param distanceX
	 * @param distanceY
	 * @param duration
	 */
	public void startScroll(int startX,int startY,int distanceX,int distanceY,long duration) {
		this.startX = startX;
		this.startY = startY;
		this.distanceX = distanceX;
		this.distanceX = distanceY;
		this.startTime = SystemClock.uptimeMillis();
		this.isFinish = false;
		this.duration = duration;
	}
	
	/**
	 * ���㵱ǰ������״̬����õ�ǰӦ�����е���λ������
	 * @return true:��������  false:���н���
	 */
	public boolean computeScrollOffset() {
		if(isFinish){
			return false;
		}
		//�Ѿ�����ʱ��
		long passTime = SystemClock.uptimeMillis() - startTime;
		if(passTime < duration){
			currX = (int) (startX + passTime*distanceX/duration);
			currY = (int) (startY + passTime*distanceY/duration);
		}else {
			currX = (int) (startX + distanceX);
			currY = (int) (startY + distanceY);
			isFinish = true;
		}
		
		return true;
	}

	public int getCurrX() {
		return currX;
	}

	

	public int getCurrY() {
		return currY;
	}

	
	
	
	
}
