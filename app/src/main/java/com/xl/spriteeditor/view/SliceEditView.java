package com.xl.spriteeditor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.xl.game.tool.Coll;
import com.xl.game.tool.DisplayUtil;
import com.xl.spriteeditor.tool.SpriteEdit;

import java.util.ArrayList;

/**
 * 切片编辑 包含如下功能
 * 双指缩放
 * 图片分割（自动切图）
 * 跳转到像素编辑器
 * 切片复制
 * 橡皮擦
 * 导入图片
 * 修改底图大小
 * 切片自动排版
 *
 */

public class SliceEditView extends View {
    private static final String TAG = "SliceEditView";
    SpriteEdit spriteEdit;
    int bitmap_x, bitmap_y;
    Paint paint_bitmap;
    Paint paint_slice;
    int press_timeOut;
    Runnable mLongPressRunnable;
    boolean isLongClick;
    float zoom; //控件放大倍数
    Rect rect_light;

    public SliceEditView(Context context) {
        super(context, null);
        initView();
    }

    public SliceEditView(Context context, AttributeSet attr) {
        super(context, attr);
        initView();
    }

    private void initView() {
        this.zoom = 1.0f;
        this.press_timeOut = 1000;
        this.mLongPressRunnable = new Runnable() {
            @Override
            public void run() {
                //开启长按模式
                if (!isMove) {
                    isLongClick = true;
                    rect_light.set(getScrollX() + touch_x_up, getScrollY() + touch_y_up, getScrollX() + touch_x_up + 1, getScrollY() + touch_y_up + 1);
                    invalidate();
                }

            }
        };
        this.paint_bitmap = new Paint();
        this.paint_slice = new Paint();
        this.paint_slice.setAntiAlias(true);
        this.paint_slice.setStrokeWidth(1);
        this.paint_slice.setColor(0x80ff0000);
//        this.paint_slice.setAlpha(128);
        this.paint_slice.setStyle(Paint.Style.STROKE);
        int padding = DisplayUtil.dip2px(getContext(), 120);
        this.rect_light = new Rect();
        setPadding(padding, padding, padding, padding);

    }

    //设置精灵编辑控件
    public void setSpriteEdit(SpriteEdit spriteEdit) {
        this.spriteEdit = spriteEdit;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //显示底层位图
        Bitmap bitmap = spriteEdit.getBitmap();
        Rect src = new Rect(bitmap_x, bitmap_y, bitmap_x + bitmap.getWidth(), bitmap_y + bitmap.getHeight());
        Rect dst = new Rect(bitmap_x, bitmap_y, bitmap_x + (int) (bitmap.getWidth() * zoom), bitmap_y + (int) (bitmap.getHeight() * zoom));

        canvas.drawBitmap(spriteEdit.getBitmap(), src, dst, paint_bitmap);

//        canvas.drawBitmap(spriteEdit.getBitmap(),100,0,null);
        //显示切片
        ArrayList<SpriteEdit.Slice> array_slice = spriteEdit.getSliceList();

        for (int i = 0; i < array_slice.size(); i++) {
            SpriteEdit.Slice slice = array_slice.get(i);
            canvas.drawRect((bitmap_x + slice.getX())*zoom, (bitmap_y + slice.getY())*zoom, (bitmap_x + slice.getX() + slice.getWidth())*zoom, (bitmap_y + slice.getY() + slice.getHeight())*zoom, paint_slice);

        }

        if (isLongClick) {
            canvas.drawRect(rect_light, paint_slice);
            //绘制十字光标
            canvas.drawLine(rect_light.left, getScrollY(), rect_light.left, getScrollY() + getHeight(), paint_slice);
            canvas.drawLine(rect_light.right, getScrollY(), rect_light.right, getScrollY() + getHeight(), paint_slice);

            canvas.drawLine(getScrollX(), rect_light.top, getScrollX() + getWidth(), rect_light.top, paint_slice);
            canvas.drawLine(getScrollX(), rect_light.bottom, getScrollX() + getWidth(), rect_light.bottom, paint_slice);
        }

//        Log.i(TAG, "onDraw: ");
    }

    //缩放视图 参数：缩放中心坐标 缩放倍数
    public void zoomView(int x,int y, float zoom){
        Log.i(TAG, "zoomView: "+zoom);
        int px = (int) ((x+getScrollX())/this.zoom);
        int py = (int) ((y+getScrollY())/this.zoom);
        int tx = (int) (px*zoom);
        int ty = (int) (py*zoom);
        scrollTo(tx-x,ty-y);
        this.zoom = zoom;
        invalidate();
    }

    //检测scroll是否越界
    private void checkScroll() {
        if (getScrollX() < -getPaddingLeft()) {
            setScrollX(-getPaddingLeft());
        }
        if (getScrollY() < -getPaddingTop()) {
            setScrollY(-getPaddingTop());
        }
        if (getScrollX() > spriteEdit.getBitmap().getWidth() * zoom + getPaddingLeft()) {
            setScrollX((int) (spriteEdit.getBitmap().getWidth() * zoom) + getPaddingLeft());
        }
        if (getScrollY() > spriteEdit.getBitmap().getHeight() * zoom + getPaddingRight()) {
            setScrollY((int) (spriteEdit.getBitmap().getHeight() * zoom) + getPaddingRight());
        }
    }

    int touch_x_up, touch_y_up;
    int touch_start_x, touch_start_y;
    boolean isMove;
    boolean isMorePoints;
    int start_line;
    float start_zoom;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_x_up = (int) event.getX();
                touch_y_up = (int) event.getY();
                touch_start_x = (int) event.getX();
                touch_start_y = (int) event.getY();
                postDelayed(mLongPressRunnable, press_timeOut);
                isMove = false;
                isMorePoints = false;
                isLongClick = false;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG, "action pointer dowm");
                if(event.getPointerCount()==2){
                    int index1 = event.findPointerIndex(0);
                    int x1 = (int) event.getX(index1);
                    int y1 = (int) event.getY(index1);
                    int index2 = event.findPointerIndex(1);
                    int x2 = (int) event.getX(index2);
                    int y2 = (int) event.getY(index2);
                    start_line = (int) Coll.getLineSize(x1,y1,x2,y2);
start_zoom = this.zoom;
                }
                break;
            case MotionEvent.ACTION_POINTER_INDEX_MASK:
                Log.i(TAG, "pointer index mask");
                break;
            case MotionEvent.ACTION_POINTER_2_DOWN:
                if(event.getPointerCount()==2){
                    int index1 = event.findPointerIndex(0);
                    int x1 = (int) event.getX(index1);
                    int y1 = (int) event.getY(index1);
                    int index2 = event.findPointerIndex(1);
                    int x2 = (int) event.getX(index2);
                    int y2 = (int) event.getY(index2);
                    start_line = (int) Coll.getLineSize(x1,y1,x2,y2);
                    start_zoom = this.zoom;
                    Log.i(TAG, "start_line = "+start_line);
                }


                Log.i(TAG, "action pointer 2 dowm");
                break;
            case MotionEvent.ACTION_MOVE:
                //如果只有一个手指按下并且这个手指id是0
                if (event.getPointerCount() == 1) {

                    int touch_id = event.getPointerId(0);
                    if (touch_id == 0) {
                        int index = event.findPointerIndex(touch_id);
                        int x = (int) event.getX(index);
                        int y = (int) event.getY(index);
                        int x_move = x - touch_x_up;
                        int y_move = y - touch_y_up;
                        if (Math.abs(x - touch_start_x) > 5 && Math.abs(y - touch_start_y) > 5) {
                            isMove = true;
                        }
                        Log.i(TAG, "onTouchEvent: move " + x_move + " " + y_move);
                        if (isMove && (!isLongClick)) {
                            scrollBy(-x_move, -y_move);
                        } else if (isLongClick) {
                            rect_light.right += x_move;
                            rect_light.bottom += y_move;
                        }

                        touch_x_up = x;
                        touch_y_up = y;
                    }


                }
                else if(event.getPointerCount() == 2){
                    isMorePoints = true;
                    int touch_id = event.getPointerId(0);
                    int touch_id2 = event.getPointerId(1);
                    int index = event.findPointerIndex(touch_id);
                    int x1 = (int) event.getX(index);
                    int y1 = (int) event.getY(index);
                    index = event.findPointerIndex(touch_id2);
                    int x2 = (int) event.getX(index);
                    int y2 = (int) event.getY(index);
                    //获取两点之间的中心点
                    int center_x = (x2+x1)/2;
                    int center_y = (y1+y2)/2;
int line = (int) Coll.getLineSize(x1,y1,x2,y2);
                    Log.i(TAG, "onTouchEvent: line="+line+" start_zoom="+start_zoom+" zoom="+(((float)line/2)/(start_line/2)));
                    float zoom_new = (start_zoom-1.0f)+(((float)line)/(start_line));

                    zoom_new = (start_zoom)*(((float)line)/(start_line));


                    zoomView(center_x,center_y, zoom_new);
                }

                //否则不坐处理
                checkScroll();
                break;
            case MotionEvent.ACTION_UP:

                removeCallbacks(mLongPressRunnable);
                if (isLongClick && !isMorePoints) {
                    Log.i(TAG, "onTouchEvent: 添加切片");
                    //添加一个切片
                    if (Math.abs(rect_light.width()) > 1 && Math.abs(rect_light.height()) > 1) {
                        spriteEdit.addSlice((int) (rect_light.left / zoom), (int) (rect_light.top / zoom), (int) (rect_light.width() / zoom), (int) (rect_light.height() / zoom));
                        Log.i(TAG, "onTouchEvent: addSlice");
                    }
                }
                isMove = false;
                isLongClick = false;
                isMorePoints = false;
                break;
            case MotionEvent.ACTION_POINTER_1_UP:
                break;
            case MotionEvent.ACTION_POINTER_2_UP:

        }

        invalidate();
        return true;

    }


}
