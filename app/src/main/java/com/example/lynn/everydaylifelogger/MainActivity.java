package com.example.lynn.everydaylifelogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.bt_insert:
                       Intent intent = new Intent(MainActivity.this, WriteNote.class);
                        startActivity(intent);
                        break;
                    case R.id.bt_see:
                        Intent intent2 = new Intent(MainActivity.this, ViewList.class);
                        startActivity(intent2);
                        break;
                    case R.id.bt_result:
                        Intent intent3 = new Intent(MainActivity.this, Statistics.class);
                        startActivity(intent3);
                        break;
                }
            }
        };

        Button bt_inset = (Button) findViewById(R.id.bt_insert);//입력
        bt_inset.setOnClickListener(listener);
        Button bt_see = (Button) findViewById(R.id.bt_see);//보기
        bt_see.setOnClickListener(listener);
        Button bt_result = (Button) findViewById(R.id.bt_result);//통계
        bt_result.setOnClickListener(listener);


    }


}
