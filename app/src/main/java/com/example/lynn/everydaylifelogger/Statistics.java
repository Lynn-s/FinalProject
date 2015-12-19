package com.example.lynn.everydaylifelogger;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Statistics extends Activity{

    String str_result= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        DBhandler handler = new DBhandler(getApplicationContext(), "ScheduleList.db", null, 1);

        str_result += "식사 : " + handler.result_eat() + "%\n운동 : " +
                        handler.result_exercise() + "%\n학습 : " +
                        handler.result_learn() + "%\n문화 : " +
                        handler.result_culture() + "%\n기타 : " +
                        handler.result_etc() + "%";

        TextView list = (TextView) findViewById(R.id.textView);
        list.setText(str_result);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        };
        Button bt_inset = (Button) findViewById(R.id.bt_back);//돌아가기
        bt_inset.setOnClickListener(listener);


    }


}
