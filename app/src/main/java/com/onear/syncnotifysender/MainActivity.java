package com.onear.syncnotifysender;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton actionButton;
    private RadioGroup radioGroup;
    private RadioButton checkedRadioButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //fab的事件实例
        actionButton = findViewById(R.id.send_fab);
        //RadioGroup的实例
        radioGroup = findViewById(R.id.popup_time_radiobutton_group);
        //选中的button的id
        checkedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        switch (checkedRadioButton.getTag().toString()){
            case "popup_time_after_class_radiobutton":
                break;
            case ""
        }
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
                String nowTime = simpleDateFormat.format(new Date().getTime());


                try {
                    createJson(nowTime,"asas");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void createJson(String fileName,String messageContent) throws JSONException, IOException {
        File file = new File(getFilesDir(), fileName+".json");
        //实例化一个JSONObject对象
        JSONObject jsonMessage = new JSONObject();
        JSONObject property = new JSONObject();
        property.put("FileContent", "语文");
        property.put("FileType", 98);
        property.put("FileSender", 98);
        property.put("FileLocation", 98);
        property.put("FileName", 98);
        property.put("FileCreatingDate", 98);
        JSONObject display = new JSONObject();
        display.put("fileDisplayTime", "数学");
        display.put("fileDisplayMode", 100);
        //实例化一个JSON数组
        JSONArray message = new JSONArray();
        //将course1添加到JSONArray，下标为0
        message.put(0, property);
        message.put(1, display);
        jsonMessage.put("message",message);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(message.toString().getBytes());
        fos.close();
    }
}