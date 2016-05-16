package yinlei.customize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import yinlei.customize.utils.SingleToast;
import yinlei.customize.widget.ToggleView;

public class MainActivity extends AppCompatActivity {

    ToggleView mToggleView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToggleView = (ToggleView) findViewById(R.id.toggle_view);

        //设置背景颜色
       // mToggleView.setSwitchBackground(R.mipmap.switch_background);

        //设置背景
        //mToggleView.setSlideButtonResource(R.mipmap.slide_button);

        //设置开关状态
        //mToggleView.setSwitchState(false);

        //设置开关更新监听
        mToggleView.setOnSwitchStateUpdateListener(new ToggleView.OnSwitchStateUpdateClickListener() {
            @Override
            public void onStateUpdate(boolean state) {
                if (state) {
                    //Toast.makeText(MainActivity.this, "开关点开了" , 0).show();
                    SingleToast.showToast(MainActivity.this,"开关点开了");
                } else {
                    //Toast.makeText(MainActivity.this, "开关关闭了", 0).show();
                    SingleToast.showToast(MainActivity.this,"开关关闭了");
                }
            }
        });

    }
}
