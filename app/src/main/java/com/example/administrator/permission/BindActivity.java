package com.example.administrator.permission;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.animal.BindView;
import com.example.apilibrary.BufferViewBinder;

/**
 * @author : Alex
 * @version : V 2.0.0
 * @date : 2018/09/17
 */
public class BindActivity extends AppCompatActivity {
    @BindView(R.id.button5)
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        BufferViewBinder.bind(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BindActivity.this,"Button",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        BufferViewBinder.unBind(this);
    }
}
