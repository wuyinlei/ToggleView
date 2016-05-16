package yinlei.customize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import yinlei.customize.widget.ToggleView;

public class MainActivity extends AppCompatActivity {

    ToggleView mToggleView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToggleView = (ToggleView) findViewById(R.id.toggle_view);

        //设置背景颜色
        mToggleView.setSwitchBackground(R.mipmap.switch_background);

        //
        mToggleView.setSlideButtonResource(R.mipmap.slide_button);

        //
        mToggleView.setSwitchState(false);

    }
}
