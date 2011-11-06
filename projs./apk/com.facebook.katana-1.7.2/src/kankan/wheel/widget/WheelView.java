// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WheelView.java

package kankan.wheel.widget;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.*;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import java.util.*;
import kankan.wheel.widget.adapters.WheelViewAdapter;

// Referenced classes of package kankan.wheel.widget:
//            WheelRecycle, ItemsRange, WheelScroller, OnWheelChangedListener, 
//            OnWheelClickedListener, OnWheelScrollListener

public class WheelView extends View
{

    public WheelView(Context context)
    {
        super(context);
        currentItem = 0;
        visibleItems = 5;
        itemHeight = 0;
        isCyclic = false;
        recycle = new WheelRecycle(this);
        changingListeners = new LinkedList();
        scrollingListeners = new LinkedList();
        clickingListeners = new LinkedList();
        initData(context);
    }

    public WheelView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        currentItem = 0;
        visibleItems = 5;
        itemHeight = 0;
        isCyclic = false;
        recycle = new WheelRecycle(this);
        changingListeners = new LinkedList();
        scrollingListeners = new LinkedList();
        clickingListeners = new LinkedList();
        initData(context);
    }

    public WheelView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        currentItem = 0;
        visibleItems = 5;
        itemHeight = 0;
        isCyclic = false;
        recycle = new WheelRecycle(this);
        changingListeners = new LinkedList();
        scrollingListeners = new LinkedList();
        clickingListeners = new LinkedList();
        initData(context);
    }

    private boolean addViewItem(int i, boolean flag)
    {
        View view = getItemView(i);
        boolean flag1;
        if(view != null)
        {
            if(flag)
                itemsLayout.addView(view, 0);
            else
                itemsLayout.addView(view);
            flag1 = true;
        } else
        {
            flag1 = false;
        }
        return flag1;
    }

    private void buildViewForMeasuring()
    {
        int i;
        if(itemsLayout != null)
            recycle.recycleItems(itemsLayout, firstItem, new ItemsRange());
        else
            createItemsLayout();
        i = visibleItems / 2;
        for(int j = i + currentItem; j >= currentItem - i; j--)
            if(addViewItem(j, true))
                firstItem = j;

    }

    private int calculateLayoutWidth(int i, int j)
    {
        int k;
        initResourcesIfNecessary();
        itemsLayout.setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -2));
        itemsLayout.measure(android.view.View.MeasureSpec.makeMeasureSpec(i, 0), android.view.View.MeasureSpec.makeMeasureSpec(0, 0));
        k = itemsLayout.getMeasuredWidth();
        if(j != 0x40000000) goto _L2; else goto _L1
_L1:
        int l = i;
_L4:
        itemsLayout.measure(android.view.View.MeasureSpec.makeMeasureSpec(l - 20, 0x40000000), android.view.View.MeasureSpec.makeMeasureSpec(0, 0));
        return l;
_L2:
        l = Math.max(k + 20, getSuggestedMinimumWidth());
        if(j == 0x80000000 && i < l)
            l = i;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void createItemsLayout()
    {
        if(itemsLayout == null)
        {
            itemsLayout = new LinearLayout(getContext());
            itemsLayout.setOrientation(1);
        }
    }

    private void doScroll(int i)
    {
        int j;
        int k;
        int l;
        int i1;
        int j1;
        scrollingOffset = i + scrollingOffset;
        j = getItemHeight();
        k = scrollingOffset / j;
        l = currentItem - k;
        i1 = viewAdapter.getItemsCount();
        j1 = scrollingOffset % j;
        if(Math.abs(j1) <= j / 2)
            j1 = 0;
        if(!isCyclic || i1 <= 0)
            break MISSING_BLOCK_LABEL_184;
        if(j1 <= 0) goto _L2; else goto _L1
_L1:
        l--;
        k++;
_L4:
        for(; l < 0; l += i1);
        break; /* Loop/switch isn't completed */
_L2:
        if(j1 < 0)
        {
            l++;
            k--;
        }
        if(true) goto _L4; else goto _L3
_L3:
        l %= i1;
_L5:
        int k1 = scrollingOffset;
        if(l != currentItem)
            setCurrentItem(l, false);
        else
            invalidate();
        scrollingOffset = k1 - k * j;
        if(scrollingOffset > getHeight())
            scrollingOffset = scrollingOffset % getHeight() + getHeight();
        return;
        if(l < 0)
        {
            k = currentItem;
            l = 0;
        } else
        if(l >= i1)
        {
            k = 1 + (currentItem - i1);
            l = i1 - 1;
        } else
        if(l > 0 && j1 > 0)
        {
            l--;
            k++;
        } else
        if(l < i1 - 1 && j1 < 0)
        {
            l++;
            k--;
        }
          goto _L5
    }

    private void drawCenterRect(Canvas canvas)
    {
        int i = getHeight() / 2;
        int j = (int)(1.2D * (double)(getItemHeight() / 2));
        centerDrawable.setBounds(0, i - j, getWidth(), i + j);
        centerDrawable.draw(canvas);
    }

    private void drawItems(Canvas canvas)
    {
        canvas.save();
        canvas.translate(10F, -((currentItem - firstItem) * getItemHeight() + (getItemHeight() - getHeight()) / 2) + scrollingOffset);
        itemsLayout.draw(canvas);
        canvas.restore();
    }

    private void drawShadows(Canvas canvas)
    {
        int i = (int)(1.5D * (double)getItemHeight());
        topShadow.setBounds(0, 0, getWidth(), i);
        topShadow.draw(canvas);
        bottomShadow.setBounds(0, getHeight() - i, getWidth(), getHeight());
        bottomShadow.draw(canvas);
    }

    private int getDesiredHeight(LinearLayout linearlayout)
    {
        if(linearlayout != null && linearlayout.getChildAt(0) != null)
            itemHeight = linearlayout.getChildAt(0).getMeasuredHeight();
        return Math.max(itemHeight * visibleItems - (10 * itemHeight) / 50, getSuggestedMinimumHeight());
    }

    private int getItemHeight()
    {
        int i;
        if(itemHeight != 0)
            i = itemHeight;
        else
        if(itemsLayout != null && itemsLayout.getChildAt(0) != null)
        {
            itemHeight = itemsLayout.getChildAt(0).getHeight();
            i = itemHeight;
        } else
        {
            i = getHeight() / visibleItems;
        }
        return i;
    }

    private View getItemView(int i)
    {
        View view;
        if(viewAdapter == null || viewAdapter.getItemsCount() == 0)
        {
            view = null;
        } else
        {
            int j = viewAdapter.getItemsCount();
            if(!isValidItemIndex(i))
            {
                view = viewAdapter.getEmptyItem(recycle.getEmptyItem(), itemsLayout);
            } else
            {
                for(; i < 0; i += j);
                int k = i % j;
                view = viewAdapter.getItem(k, recycle.getItem(), itemsLayout);
            }
        }
        return view;
    }

    private ItemsRange getItemsRange()
    {
        ItemsRange itemsrange;
        if(getItemHeight() == 0)
        {
            itemsrange = null;
        } else
        {
            int i = currentItem;
            int j;
            for(j = 1; j * getItemHeight() < getHeight(); j += 2)
                i--;

            if(scrollingOffset != 0)
            {
                if(scrollingOffset > 0)
                    i--;
                int k = j + 1;
                int l = scrollingOffset / getItemHeight();
                i -= l;
                j = (int)((double)k + Math.asin(l));
            }
            itemsrange = new ItemsRange(i, j);
        }
        return itemsrange;
    }

    private void initData(Context context)
    {
        scroller = new WheelScroller(getContext(), scrollingListener);
    }

    private void initResourcesIfNecessary()
    {
        if(centerDrawable == null)
            centerDrawable = getContext().getResources().getDrawable(0x7f020132);
        if(topShadow == null)
            topShadow = new GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, SHADOWS_COLORS);
        if(bottomShadow == null)
            bottomShadow = new GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.BOTTOM_TOP, SHADOWS_COLORS);
        setBackgroundResource(0x7f020131);
    }

    private boolean isValidItemIndex(int i)
    {
        boolean flag;
        if(viewAdapter != null && viewAdapter.getItemsCount() > 0 && (isCyclic || i >= 0 && i < viewAdapter.getItemsCount()))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void layout(int i, int j)
    {
        int k = i - 20;
        itemsLayout.layout(0, 0, k, j);
    }

    private boolean rebuildItems()
    {
        ItemsRange itemsrange;
        boolean flag;
        int i;
        int k;
        itemsrange = getItemsRange();
        if(itemsLayout != null)
        {
            int l = recycle.recycleItems(itemsLayout, firstItem, itemsrange);
            int j;
            if(firstItem != l)
                flag = true;
            else
                flag = false;
            firstItem = l;
        } else
        {
            createItemsLayout();
            flag = true;
        }
        if(!flag)
            if(firstItem != itemsrange.getFirst() || itemsLayout.getChildCount() != itemsrange.getCount())
                flag = true;
            else
                flag = false;
        if(firstItem <= itemsrange.getFirst() || firstItem > itemsrange.getLast()) goto _L2; else goto _L1
_L1:
        k = firstItem - 1;
_L7:
        if(k >= itemsrange.getFirst() && addViewItem(k, true)) goto _L4; else goto _L3
_L3:
        i = firstItem;
        for(j = itemsLayout.getChildCount(); j < itemsrange.getCount(); j++)
            if(!addViewItem(j + firstItem, false) && itemsLayout.getChildCount() == 0)
                i++;

        break; /* Loop/switch isn't completed */
_L4:
        firstItem = k;
        k--;
        continue; /* Loop/switch isn't completed */
_L2:
        firstItem = itemsrange.getFirst();
        if(true) goto _L3; else goto _L5
_L5:
        firstItem = i;
        return flag;
        if(true) goto _L7; else goto _L6
_L6:
    }

    private void updateView()
    {
        if(rebuildItems())
        {
            calculateLayoutWidth(getWidth(), 0x40000000);
            layout(getWidth(), getHeight());
        }
    }

    public void addChangingListener(OnWheelChangedListener onwheelchangedlistener)
    {
        changingListeners.add(onwheelchangedlistener);
    }

    public void addClickingListener(OnWheelClickedListener onwheelclickedlistener)
    {
        clickingListeners.add(onwheelclickedlistener);
    }

    public void addScrollingListener(OnWheelScrollListener onwheelscrolllistener)
    {
        scrollingListeners.add(onwheelscrolllistener);
    }

    public int getCurrentItem()
    {
        return currentItem;
    }

    public WheelViewAdapter getViewAdapter()
    {
        return viewAdapter;
    }

    public int getVisibleItems()
    {
        return visibleItems;
    }

    public void invalidateWheel(boolean flag)
    {
        if(!flag) goto _L2; else goto _L1
_L1:
        recycle.clearAll();
        if(itemsLayout != null)
            itemsLayout.removeAllViews();
        scrollingOffset = 0;
_L4:
        invalidate();
        return;
_L2:
        if(itemsLayout != null)
            recycle.recycleItems(itemsLayout, firstItem, new ItemsRange());
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean isCyclic()
    {
        return isCyclic;
    }

    protected void notifyChangingListeners(int i, int j)
    {
        for(Iterator iterator = changingListeners.iterator(); iterator.hasNext(); ((OnWheelChangedListener)iterator.next()).onChanged(this, i, j));
    }

    protected void notifyClickListenersAboutClick(int i)
    {
        for(Iterator iterator = clickingListeners.iterator(); iterator.hasNext(); ((OnWheelClickedListener)iterator.next()).onItemClicked(this, i));
    }

    protected void notifyScrollingListenersAboutEnd()
    {
        for(Iterator iterator = scrollingListeners.iterator(); iterator.hasNext(); ((OnWheelScrollListener)iterator.next()).onScrollingFinished(this));
    }

    protected void notifyScrollingListenersAboutStart()
    {
        for(Iterator iterator = scrollingListeners.iterator(); iterator.hasNext(); ((OnWheelScrollListener)iterator.next()).onScrollingStarted(this));
    }

    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(viewAdapter != null && viewAdapter.getItemsCount() > 0)
        {
            updateView();
            drawItems(canvas);
            drawCenterRect(canvas);
        }
        drawShadows(canvas);
    }

    protected void onLayout(boolean flag, int i, int j, int k, int l)
    {
        layout(k - i, l - j);
    }

    protected void onMeasure(int i, int j)
    {
        int l;
        int j1;
        int k1;
        int k = android.view.View.MeasureSpec.getMode(i);
        l = android.view.View.MeasureSpec.getMode(j);
        int i1 = android.view.View.MeasureSpec.getSize(i);
        j1 = android.view.View.MeasureSpec.getSize(j);
        buildViewForMeasuring();
        k1 = calculateLayoutWidth(i1, k);
        if(l != 0x40000000) goto _L2; else goto _L1
_L1:
        int l1 = j1;
_L4:
        setMeasuredDimension(k1, l1);
        return;
_L2:
        l1 = getDesiredHeight(itemsLayout);
        if(l == 0x80000000)
            l1 = Math.min(l1, j1);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        if(isEnabled() && getViewAdapter() != null) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
        switch(motionevent.getAction())
        {
        default:
            break;

        case 2: // '\002'
            break; /* Loop/switch isn't completed */

        case 1: // '\001'
            break;
        }
        break MISSING_BLOCK_LABEL_76;
_L5:
        flag = scroller.onTouchEvent(motionevent);
        if(true) goto _L4; else goto _L3
_L3:
        if(getParent() != null)
            getParent().requestDisallowInterceptTouchEvent(true);
          goto _L5
        if(!isScrollingPerformed)
        {
            int i = (int)motionevent.getY() - getHeight() / 2;
            int j;
            int k;
            if(i > 0)
                j = i + getItemHeight() / 2;
            else
                j = i - getItemHeight() / 2;
            k = j / getItemHeight();
            if(isValidItemIndex(k + currentItem))
                notifyClickListenersAboutClick(k + currentItem);
        }
          goto _L5
    }

    public void removeChangingListener(OnWheelChangedListener onwheelchangedlistener)
    {
        changingListeners.remove(onwheelchangedlistener);
    }

    public void removeClickingListener(OnWheelClickedListener onwheelclickedlistener)
    {
        clickingListeners.remove(onwheelclickedlistener);
    }

    public void removeScrollingListener(OnWheelScrollListener onwheelscrolllistener)
    {
        scrollingListeners.remove(onwheelscrolllistener);
    }

    public void scroll(int i, int j)
    {
        int k = i * getItemHeight() - scrollingOffset;
        scroller.scroll(k, j);
    }

    public void setCurrentItem(int i)
    {
        setCurrentItem(i, false);
    }

    public void setCurrentItem(int i, boolean flag)
    {
        if(viewAdapter != null && viewAdapter.getItemsCount() != 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int j = viewAdapter.getItemsCount();
        if(i < 0 || i >= j)
        {
            if(!isCyclic)
                continue; /* Loop/switch isn't completed */
            for(; i < 0; i += j);
            i %= j;
        }
        if(i != currentItem)
            if(flag)
            {
                int l = i - currentItem;
                if(isCyclic)
                {
                    int i1 = (j + Math.min(i, currentItem)) - Math.max(i, currentItem);
                    if(i1 < Math.abs(l))
                        if(l < 0)
                            l = i1;
                        else
                            l = -i1;
                }
                scroll(l, 0);
            } else
            {
                scrollingOffset = 0;
                int k = currentItem;
                currentItem = i;
                notifyChangingListeners(k, currentItem);
                invalidate();
            }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void setCyclic(boolean flag)
    {
        isCyclic = flag;
        invalidateWheel(false);
    }

    public void setInterpolator(Interpolator interpolator)
    {
        scroller.setInterpolator(interpolator);
    }

    public void setViewAdapter(WheelViewAdapter wheelviewadapter)
    {
        if(viewAdapter != null)
            viewAdapter.unregisterDataSetObserver(dataObserver);
        viewAdapter = wheelviewadapter;
        if(viewAdapter != null)
            viewAdapter.registerDataSetObserver(dataObserver);
        invalidateWheel(true);
    }

    public void setVisibleItems(int i)
    {
        visibleItems = i;
    }

    public void stopScrolling()
    {
        scroller.stopScrolling();
    }

    private static final int DEF_VISIBLE_ITEMS = 5;
    private static final int ITEM_OFFSET_PERCENT = 10;
    private static final int PADDING = 10;
    private static final int SHADOWS_COLORS[];
    private GradientDrawable bottomShadow;
    private Drawable centerDrawable;
    private List changingListeners;
    private List clickingListeners;
    private int currentItem;
    private DataSetObserver dataObserver = new DataSetObserver() {

        public void onChanged()
        {
            invalidateWheel(false);
        }

        public void onInvalidated()
        {
            invalidateWheel(true);
        }

        final WheelView this$0;

            
            {
                this$0 = WheelView.this;
                super();
            }
    }
;
    private int firstItem;
    boolean isCyclic;
    private boolean isScrollingPerformed;
    private int itemHeight;
    private LinearLayout itemsLayout;
    private WheelRecycle recycle;
    private WheelScroller scroller;
    WheelScroller.ScrollingListener scrollingListener = new WheelScroller.ScrollingListener() {

        public void onFinished()
        {
            if(isScrollingPerformed)
            {
                notifyScrollingListenersAboutEnd();
                isScrollingPerformed = false;
            }
            scrollingOffset = 0;
            invalidate();
        }

        public void onJustify()
        {
            if(Math.abs(scrollingOffset) > 1)
                scroller.scroll(scrollingOffset, 0);
        }

        public void onScroll(int i)
        {
            int j;
            doScroll(i);
            j = getHeight();
            if(scrollingOffset <= j) goto _L2; else goto _L1
_L1:
            scrollingOffset = j;
            scroller.stopScrolling();
_L4:
            return;
_L2:
            if(scrollingOffset < -j)
            {
                scrollingOffset = -j;
                scroller.stopScrolling();
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        public void onStarted()
        {
            isScrollingPerformed = true;
            notifyScrollingListenersAboutStart();
        }

        final WheelView this$0;

            
            {
                this$0 = WheelView.this;
                super();
            }
    }
;
    private List scrollingListeners;
    private int scrollingOffset;
    private GradientDrawable topShadow;
    private WheelViewAdapter viewAdapter;
    private int visibleItems;

    static 
    {
        int ai[] = new int[3];
        ai[0] = 0xff111111;
        ai[1] = 0xaaaaaa;
        ai[2] = 0xaaaaaa;
        SHADOWS_COLORS = ai;
    }



/*
    static boolean access$002(WheelView wheelview, boolean flag)
    {
        wheelview.isScrollingPerformed = flag;
        return flag;
    }

*/




/*
    static int access$202(WheelView wheelview, int i)
    {
        wheelview.scrollingOffset = i;
        return i;
    }

*/

}
