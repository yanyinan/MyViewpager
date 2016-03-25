package com.example.myviewpager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * �Զ���ViewPager
 * @author Administrator
 *
 */
public class MyViewPager extends ViewGroup {
	
	private Context context;
	private MyScroller scroller;
//	private Scroller scroller;
	private boolean isFling;
	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	private void initView() {
		scroller = new MyScroller(context);
	//	scroller = new Scroller(context);
		gestureDetector = new GestureDetector(context, new OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				/**
				 * ���ݽ��������Ļ���������û�������
				 * distanceXΪ��ʱ�������ƶ���Ϊ��ʱ�������ƶ�
				 */
				scrollBy((int) distanceX, 0);
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				//���ٻ���������£�currId������Χ��һ�������仯
				isFling	= true;
				if(velocityX>0 && currId>0){
					currId--;
				}else if (velocityX<0 && currId<getChildCount()-1) {
					currId++;
				}
				moveToDest(currId);
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

	/**
	 * changedΪtrue��˵�����ַ����˱仯
	 * l/t/r/b ��ǰviewgroup�ڸ�View��λ��
	 * 
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			view.layout(getWidth()*i, 0, getWidth()*(i+1), getHeight());
			
		//	view.layout(200*i, i*100, 200*(i+1), 400);   //ͼƬ��ɲ���ָ���Ĵ�С
		}
		
	}
	
	/**
	 * ����ʶ�������������onTouchEvent�����ƵĽ���
	 * 
	 */
	private GestureDetector gestureDetector;
	/**
	 * ��ǰ��ʾ��ͼƬid
	 */
	private int currId;
	/**
	 * down��x���� 
	 */
	private float firstX;
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) { 
		super.onTouchEvent(event);
		
		//event����gestureDetectorִ�С��������Ǵ������¼�
		gestureDetector.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			firstX = event.getX();
	System.out.println("down");
			break;
		case MotionEvent.ACTION_MOVE:
	System.out.println("move");

			break;	
		case MotionEvent.ACTION_UP:
	System.out.println("up");
		   //û�п��ٻ���ʱ���Ű�λ���ж�currId
		   if(!isFling){  
			   if(event.getX()-firstX > getWidth()/2){
					currId = currId>0?--currId:0;
				//��
				}else if (firstX - event.getX() > getWidth()/2) {
					currId = currId<getChildCount()-1?++currId:(getChildCount()-1);
				}
				//������ָ��λ�ã�ֻ��up����²�ִ��
				moveToDest(currId);  
		   }
			//�һ�
			isFling = false;
			
			break;
		default:
			break;
		}
		return true;
	}

	public void moveToDest(int currId) { 
		if(pageChangeListener != null){
			pageChangeListener.moveToDest(currId);
		}
		int distanceX = currId*getWidth() - getScrollX();
		scroller.startScroll(getScrollX(), 0, distanceX, 0);
	//	scroller.startScroll(getScrollX(), 0, distanceX, 0,Math.abs(distanceX));  //�����ʱ����ͬ
		//����onDraw()ִ��
		invalidate();
	}
	
	/**
	 * ������View���в���.(������д�÷����������View��onMeasure()����ֻ�ǲ�����ǰViewGroup)
	 * 
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			view.measure(widthMeasureSpec, heightMeasureSpec);
		}
	}
	
	
	/**
	 * ��onDraw()�лᱻ���ã���ȡ��ǰӦ�û�������λ��Ȼ�󻬶�����λ��
	 */
	@Override
	public void computeScroll() {
		if(scroller.computeScrollOffset()){
			int newX = scroller.getCurrX();
			int newY = scroller.getCurrY();
			scrollTo(newX, newY);
			invalidate();
		}
	}
	
	private PageChangeListener pageChangeListener;
	
	
	
	public PageChangeListener getPageChangeListener() {
		return pageChangeListener;
	}

	public void setPageChangeListener(PageChangeListener pageChangeListener) {
		this.pageChangeListener = pageChangeListener;
	}


	/**
	 * ҳ�滬���ļ����������ⲿ���ʹ��
	 * @author Administrator
	 *
	 */
	public interface PageChangeListener{
		void moveToDest(int currId);
	};
	
	
	
}
