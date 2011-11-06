// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbsSpinner.java

package custom.android;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.*;

// Referenced classes of package custom.android:
//            AdapterView

public abstract class AbsSpinner extends AdapterView
{
    class RecycleBin
    {

        public void add(View view)
        {
            mScrapHeap.put(mScrapHeap.size(), view);
        }

        void clear()
        {
            SparseArray sparsearray = mScrapHeap;
            int i = sparsearray.size();
            for(int j = 0; j < i; j++)
            {
                View view = (View)sparsearray.valueAt(j);
                if(view != null)
                    removeDetachedView(view, true);
            }

            sparsearray.clear();
        }

        public View get()
        {
            View view1;
            if(mScrapHeap.size() < 1)
            {
                view1 = null;
            } else
            {
                View view = (View)mScrapHeap.valueAt(0);
                int i = mScrapHeap.keyAt(0);
                if(view != null)
                    mScrapHeap.delete(i);
                view1 = view;
            }
            return view1;
        }

        View get(int i)
        {
            View view = (View)mScrapHeap.get(i);
            if(view != null)
                mScrapHeap.delete(i);
            return view;
        }

        public void put(int i, View view)
        {
            mScrapHeap.put(i, view);
        }

        private final SparseArray mScrapHeap = new SparseArray();
        final AbsSpinner this$0;

        RecycleBin()
        {
            this$0 = AbsSpinner.this;
            super();
        }
    }

    static class SavedState extends android.view.View.BaseSavedState
    {

        public String toString()
        {
            return (new StringBuilder()).append("AbsSpinner.SavedState{").append(Integer.toHexString(System.identityHashCode(this))).append(" selectedId=").append(selectedId).append(" position=").append(position).append("}").toString();
        }

        public void writeToParcel(Parcel parcel, int i)
        {
            super.writeToParcel(parcel, i);
            parcel.writeLong(selectedId);
            parcel.writeInt(position);
        }

        public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

            public SavedState createFromParcel(Parcel parcel)
            {
                return new SavedState(parcel);
            }

            public volatile Object createFromParcel(Parcel parcel)
            {
                return createFromParcel(parcel);
            }

            public SavedState[] newArray(int i)
            {
                return new SavedState[i];
            }

            public volatile Object[] newArray(int i)
            {
                return newArray(i);
            }

        }
;
        int position;
        long selectedId;


        private SavedState(Parcel parcel)
        {
            super(parcel);
            selectedId = parcel.readLong();
            position = parcel.readInt();
        }


        SavedState(Parcelable parcelable)
        {
            super(parcelable);
        }
    }


    public AbsSpinner(Context context)
    {
        super(context);
        mSelectionLeftPadding = 0;
        mSelectionTopPadding = 0;
        mSelectionRightPadding = 0;
        mSelectionBottomPadding = 0;
        mSpinnerPadding = new Rect();
        mRecycler = new RecycleBin();
        initAbsSpinner();
    }

    public AbsSpinner(Context context, AttributeSet attributeset)
    {
        this(context, attributeset, 0);
    }

    public AbsSpinner(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        mSelectionLeftPadding = 0;
        mSelectionTopPadding = 0;
        mSelectionRightPadding = 0;
        mSelectionBottomPadding = 0;
        mSpinnerPadding = new Rect();
        mRecycler = new RecycleBin();
        initAbsSpinner();
        TypedArray typedarray = context.obtainStyledAttributes(attributeset, R.styleable.AbsSpinner, i, 0);
        CharSequence acharsequence[] = typedarray.getTextArray(0);
        if(acharsequence != null)
        {
            ArrayAdapter arrayadapter = new ArrayAdapter(context, 0x7f030077, acharsequence);
            arrayadapter.setDropDownViewResource(0x7f030076);
            setAdapter(arrayadapter);
        }
        typedarray.recycle();
    }

    private void initAbsSpinner()
    {
        setFocusable(true);
        setWillNotDraw(false);
    }

    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams()
    {
        return new android.view.ViewGroup.LayoutParams(-1, -2);
    }

    public volatile Adapter getAdapter()
    {
        return getAdapter();
    }

    public SpinnerAdapter getAdapter()
    {
        return mAdapter;
    }

    int getChildHeight(View view)
    {
        return view.getMeasuredHeight();
    }

    int getChildWidth(View view)
    {
        return view.getMeasuredWidth();
    }

    public int getCount()
    {
        return mItemCount;
    }

    public View getSelectedView()
    {
        View view;
        if(mItemCount > 0 && mSelectedPosition >= 0)
            view = getChildAt(mSelectedPosition - mFirstPosition);
        else
            view = null;
        return view;
    }

    abstract void layout(int i, boolean flag);

    protected void onMeasure(int i, int j)
    {
        int k = android.view.View.MeasureSpec.getMode(i);
        int l = getPaddingLeft();
        int i1 = getPaddingRight();
        int j1 = getPaddingBottom();
        int k1 = getPaddingTop();
        Rect rect = mSpinnerPadding;
        int l1;
        Rect rect1;
        int i2;
        Rect rect2;
        int j2;
        Rect rect3;
        int k2;
        int l2;
        int i3;
        boolean flag;
        int j3;
        int k3;
        int l3;
        int i4;
        int j4;
        int k4;
        if(l > mSelectionLeftPadding)
            l1 = l;
        else
            l1 = mSelectionLeftPadding;
        rect.left = l1;
        rect1 = mSpinnerPadding;
        if(k1 > mSelectionTopPadding)
            i2 = k1;
        else
            i2 = mSelectionTopPadding;
        rect1.top = i2;
        rect2 = mSpinnerPadding;
        if(i1 > mSelectionRightPadding)
            j2 = i1;
        else
            j2 = mSelectionRightPadding;
        rect2.right = j2;
        rect3 = mSpinnerPadding;
        if(j1 > mSelectionBottomPadding)
            k2 = j1;
        else
            k2 = mSelectionBottomPadding;
        rect3.bottom = k2;
        if(mDataChanged)
            handleDataChanged();
        l2 = 0;
        i3 = 0;
        flag = true;
        j3 = getSelectedItemPosition();
        if(j3 >= 0 && mAdapter != null)
        {
            View view = mRecycler.get();
            if(view == null)
                view = mAdapter.getView(j3, null, this);
            if(view != null)
                mRecycler.add(view);
            if(view != null)
            {
                if(view.getLayoutParams() == null)
                {
                    mBlockLayoutRequests = true;
                    android.view.ViewGroup.LayoutParams layoutparams = generateDefaultLayoutParams();
                    view.setLayoutParams(layoutparams);
                    mBlockLayoutRequests = false;
                }
                measureChild(view, i, j);
                l2 = getChildHeight(view) + mSpinnerPadding.top + mSpinnerPadding.bottom;
                i3 = getChildWidth(view) + mSpinnerPadding.left + mSpinnerPadding.right;
                flag = false;
            }
        }
        if(flag)
        {
            l2 = mSpinnerPadding.top + mSpinnerPadding.bottom;
            if(k == 0)
                i3 = mSpinnerPadding.left + mSpinnerPadding.right;
        }
        k3 = getSuggestedMinimumHeight();
        l3 = Math.max(l2, k3);
        i4 = getSuggestedMinimumWidth();
        j4 = Math.max(i3, i4);
        k4 = resolveSize(l3, j);
        setMeasuredDimension(resolveSize(j4, i), k4);
        mHeightMeasureSpec = j;
        mWidthMeasureSpec = i;
    }

    public void onRestoreInstanceState(Parcelable parcelable)
    {
        SavedState savedstate = (SavedState)parcelable;
        super.onRestoreInstanceState(savedstate.getSuperState());
        if(savedstate.selectedId >= 0L)
        {
            mDataChanged = true;
            mNeedSync = true;
            mSyncRowId = savedstate.selectedId;
            mSyncPosition = savedstate.position;
            mSyncMode = 0;
            requestLayout();
        }
    }

    public Parcelable onSaveInstanceState()
    {
        SavedState savedstate = new SavedState(super.onSaveInstanceState());
        savedstate.selectedId = getSelectedItemId();
        if(savedstate.selectedId >= 0L)
            savedstate.position = getSelectedItemPosition();
        else
            savedstate.position = -1;
        return savedstate;
    }

    public int pointToPosition(int i, int j)
    {
        Rect rect;
        int k;
        rect = mTouchFrame;
        if(rect == null)
        {
            mTouchFrame = new Rect();
            rect = mTouchFrame;
        }
        k = getChildCount() - 1;
_L3:
        if(k < 0) goto _L2; else goto _L1
_L1:
        int l;
        View view = getChildAt(k);
        if(view.getVisibility() != 0)
            continue; /* Loop/switch isn't completed */
        view.getHitRect(rect);
        if(!rect.contains(i, j))
            continue; /* Loop/switch isn't completed */
        l = k + mFirstPosition;
_L4:
        return l;
        k--;
          goto _L3
_L2:
        l = -1;
          goto _L4
    }

    void recycleAllViews()
    {
        int i = getChildCount();
        RecycleBin recyclebin = mRecycler;
        int j = mFirstPosition;
        for(int k = 0; k < i; k++)
        {
            View view = getChildAt(k);
            recyclebin.put(j + k, view);
        }

    }

    public void requestLayout()
    {
        if(!mBlockLayoutRequests)
            super.requestLayout();
    }

    void resetList()
    {
        mDataChanged = false;
        mNeedSync = false;
        removeAllViewsInLayout();
        mOldSelectedPosition = -1;
        mOldSelectedRowId = 0x0L;
        setSelectedPositionInt(-1);
        setNextSelectedPositionInt(-1);
        invalidate();
    }

    public volatile void setAdapter(Adapter adapter)
    {
        setAdapter((SpinnerAdapter)adapter);
    }

    public void setAdapter(SpinnerAdapter spinneradapter)
    {
        if(mAdapter != null)
        {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
            resetList();
        }
        mAdapter = spinneradapter;
        mOldSelectedPosition = -1;
        mOldSelectedRowId = 0x0L;
        if(mAdapter != null)
        {
            mOldItemCount = mItemCount;
            mItemCount = mAdapter.getCount();
            checkFocus();
            mDataSetObserver = new AdapterView.AdapterDataSetObserver(this);
            mAdapter.registerDataSetObserver(mDataSetObserver);
            int i;
            if(mItemCount > 0)
                i = 0;
            else
                i = -1;
            setSelectedPositionInt(i);
            setNextSelectedPositionInt(i);
            if(mItemCount == 0)
                checkSelectionChanged();
        } else
        {
            checkFocus();
            resetList();
            checkSelectionChanged();
        }
        requestLayout();
    }

    public void setSelection(int i)
    {
        setNextSelectedPositionInt(i);
        requestLayout();
        invalidate();
    }

    public void setSelection(int i, boolean flag)
    {
        boolean flag1;
        if(flag && mFirstPosition <= i && i <= (mFirstPosition + getChildCount()) - 1)
            flag1 = true;
        else
            flag1 = false;
        setSelectionInt(i, flag1);
    }

    void setSelectionInt(int i, boolean flag)
    {
        if(i != mOldSelectedPosition)
        {
            mBlockLayoutRequests = true;
            int j = i - mSelectedPosition;
            setNextSelectedPositionInt(i);
            layout(j, flag);
            mBlockLayoutRequests = false;
        }
    }

    SpinnerAdapter mAdapter;
    boolean mBlockLayoutRequests;
    private DataSetObserver mDataSetObserver;
    int mHeightMeasureSpec;
    final RecycleBin mRecycler;
    int mSelectionBottomPadding;
    int mSelectionLeftPadding;
    int mSelectionRightPadding;
    int mSelectionTopPadding;
    final Rect mSpinnerPadding;
    private Rect mTouchFrame;
    int mWidthMeasureSpec;

}
