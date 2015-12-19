package com.example.lynn.everydaylifelogger;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ViewList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final DBhandler handler = new DBhandler(getApplicationContext(), "ScheduleList.db", null, 1);

        final TextView list = (TextView) findViewById(R.id.list);
        list.setText(handler.PrintData());//출력

        //삭제
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.editText2);
                String temp = et.getText().toString();
                int index = Integer.parseInt(temp);
                handler.delete("delete from ScheduleList where _id = " + index + ";");
                Toast.makeText(ViewList.this,"삭제 완료",Toast.LENGTH_SHORT).show();
                list.setText(handler.PrintData());
            }
        };
        Button bt_inset = (Button) findViewById(R.id.bt_delete);
        bt_inset.setOnClickListener(listener);
    }
}
