package net.doracoin.demoprojects;

import android.view.View;

/**
 * Created by Doracoin on 2016/11/6.
 *
 * @author Doracoin
 * @version 1.0
 *          主要判断逻辑全部都在onClick方法中
 *          想做双击，直接实现 {@code onDoubleClick()} 方法即可
 *          其他次数，实现 {@code onMultiClick()} 根据回调的次数进行操作
 */
public abstract class MultiClickListener implements View.OnClickListener {
    /**
     * 记录点击次数
     */
    private int click_counts = 0;

    /**
     * 记录上次点击事件的时间戳，用于延时判断
     */
    private long last_click_time = 0;

    /**
     * 延迟时间
     */
    private long delayed_time = 300;

    /**
     * 延迟时间默认300ms
     */
    public MultiClickListener() {
    }

    /**
     * 通过此构造函数设定点击的延迟时间
     *
     * @param delayed_time 延迟时间,默认300ms
     */
    public MultiClickListener(long delayed_time) {
        this.delayed_time = delayed_time;
    }

    /**
     * 双击回调，如果想对双击进行操作可直接实现此方法
     *
     * @param v The view that was clicked.
     */
    public abstract void onDoubleClick(View v);

    /**
     * 点击次数的回调方法，如果想根据不同的点击次数进行操作，可实现此方法获取当前点击次数
     *
     * @param v           The view that was clicked.
     * @param clickCounts 实时点击次数
     */
    public abstract void onMultiClick(View v, int clickCounts);

    /**
     * 不建议实现此回调方法, 如果需要实现此方法，请去掉编译器自动填充的
     * {@code super.onClick(v)} 这一行,
     * 否则在 {@code onMultiClick()} 方法被实现的情况下，会导致此方法被重复回调
     */
    @Deprecated
    @Override
    public void onClick(View v) {
        //根据当前点击时间和上次点击时间相差时间, 来判断是否在设定的延迟时间范围内
        if (System.currentTimeMillis() - last_click_time < delayed_time) {
            //点击次数+1
            click_counts++;
        } else {
            //点击次数为1
            click_counts = 1;
        }
        //记录新的点击时间
        last_click_time = System.currentTimeMillis();
        if (click_counts == 2) {
            onDoubleClick(v);
        }
        onMultiClick(v, click_counts);
    }
}
