package demo.com.keyboarddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {



    private int bottomHeight;
    private KeyBoardHelperUtil boardHelper;
    private View layoutBottom;
    private View layoutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //为 Activity 指定 windowSoftInputMode
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        layoutContent = findViewById(R.id.layout_content);
        layoutBottom = findViewById(R.id.layout_bottom);
        boardHelper = new KeyBoardHelperUtil(this);
        boardHelper.onCreate();
        boardHelper.setOnKeyBoardStatusChangeListener(onKeyBoardStatusChangeListener);
        layoutBottom.post(new Runnable() {
            @Override
            public void run() {
                bottomHeight = layoutBottom.getHeight();
            }
        });
    }



    private KeyBoardHelperUtil.OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener = new KeyBoardHelperUtil.OnKeyBoardStatusChangeListener() {

        @Override
        public void OnKeyBoardPop(int keyBoardheight) {

            final int height = keyBoardheight;
            if (bottomHeight > height) {//底部空白区域高度大于软键盘高度，没遮住
                layoutBottom.setVisibility(View.GONE);
            } else {
                int offset = bottomHeight - height;
                final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) layoutContent
                        .getLayoutParams();
                lp.topMargin = offset;
                layoutContent.setLayoutParams(lp);
            }
        }

        @Override
        public void OnKeyBoardClose(int oldKeyBoardheight) {
            if (View.VISIBLE != layoutBottom.getVisibility()) {
                layoutBottom.setVisibility(View.VISIBLE);
            }
            final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) layoutContent
                    .getLayoutParams();
            if (lp.topMargin != 0) {
                lp.topMargin = 0;
                layoutContent.setLayoutParams(lp);
            }

        }

    };

}
