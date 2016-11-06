package net.doracoin.demoprojects;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnDouble;
    private TextView txtAClickCounts;
    private Button btnMulti;
    private TextView txtBClickCounts;
    private int totalTimes = 10;
    private boolean isDebug = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
    }

    private void initview() {
        txtAClickCounts = (TextView) findViewById(R.id.txt_btn_a_click_counts);
        btnDouble = (Button) findViewById(R.id.btn_double_click);
        btnDouble.setOnClickListener(new MultiClickListener() {
            @Override
            public void onDoubleClick(View v) {
                toast("按钮 A 被双击！");
            }

            @Override
            public void onMultiClick(View v, int clickCounts) {
                txtAClickCounts.setText("按钮 A 点击次数： " + clickCounts);
            }
        });

        txtBClickCounts = (TextView) findViewById(R.id.txt_btn_b_click_counts);
        btnMulti = (Button) findViewById(R.id.btn_multi_click);
        btnMulti.setOnClickListener(new MultiClickListener() {
            @Override
            public void onDoubleClick(View v) {
            }

            @Override
            public void onMultiClick(View v, int clickCounts) {
                txtBClickCounts.setText("按钮 B 点击次数： " + clickCounts);
                if (!isDebug) {
                    if (clickCounts >= 3) {
                        toastAppend("再点击 " + (totalTimes - clickCounts) + " 次进入开发者模式");
                        if (clickCounts == totalTimes) {
                            isDebug = true;
                        }
                    }
                } else {
                    toastAppend("您已进入开发者模式 [/滑稽] ");
                }
            }
        });
    }

    private void toast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    private Toast mToast = null;

    private void toastAppend(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }

    int back_click_count = 0;
    long back_click_time = 0;

    /**
     * 双击返回键退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (System.currentTimeMillis() - back_click_time < 300) {
                    back_click_count++;
                } else {
                    back_click_count = 1;
                    toast("双击返回键退出");
                }
                back_click_time = System.currentTimeMillis();
                if (back_click_count == 2) {
                    finish();
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
