package com.onear.syncnotifysender;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.FileProvider;
import com.google.android.material.color.DynamicColors;
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
    private EditText messageEditor;
    private int displayMode;
    private String currentMessageFileLocation;
    private StringBuilder sb = new StringBuilder();
    /**
     *             DEFAULT, //0
     *             INVISIBLE, //1
     *             FORCED_NOW, //2
     *             AFTERCLASS, //3
     *             FIGURED, //4
     **/
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
//    String nowTime = simpleDateFormat.format(new Date().getTime());
    //获取日历的一个实例，里面包含了当前的年月日
    Calendar calendar = Calendar.getInstance();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //fab的事件实例
        actionButton = findViewById(R.id.send_fab);
        //RadioGroup的实例
        radioGroup = findViewById(R.id.popup_time_radiobutton_group);

        //文本框的实例
        messageEditor = findViewById(R.id.message_textbox);


        // apply dynamic color
        DynamicColors.applyToActivitiesIfAvailable(getApplication());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //通过checkedId获取选中的 RadioButton
                RadioButton radioButton = findViewById(i);
                //获取选中的RadioButton的tag
                String selectedTag = radioButton.getText().toString();
                switch (selectedTag){
                    case "下课后":
                        displayMode = 3;
                        break;
                    case "取决于客户端":
                        displayMode = 0;
                        break;
                    case "强制立即弹出":
                        displayMode = 2;
                        break;
                    case "选择时间":
                        displayMode = 4;
                        openDateSelectDialog();
                        break;
                    case "不弹出":
                        displayMode = 1;
                        break;
                }
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createJson(simpleDateFormat.format(new Date().getTime()),messageEditor.getText().toString(),displayMode);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (currentMessageFileLocation!=null){
                    File jsonFile = new File(currentMessageFileLocation);
                    shareJsonFile(jsonFile);
                }
            }
        });
    }

    private void openDateSelectDialog() {

        //构建一个日期对话框，该对话框已经集成了日期选择器
        //DatePickerDialog的第二个构造参数指定了日期监听器
        DatePickerDialog dialog = new DatePickerDialog((Context) this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                sb.append(i).append("-").append(i1+1).append("-").append(i2);
                calendar.set(java.util.Calendar.YEAR,i);
                calendar.set(java.util.Calendar.MONTH,i1);
                calendar.set(java.util.Calendar.DAY_OF_MONTH,i1);
                openTimeSetDialog();
            }
        },

                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        //把日期对话框显示在界面上
        dialog.show();
    }

    private void openTimeSetDialog() {
        TimePickerDialog dialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
//                sb.append("-").append(i).append("-").append(i1).append("-00");
                calendar.set(calendar.HOUR,i);
                calendar.set(calendar.MINUTE,i1);
                calendar.set(calendar.SECOND,0);
            }
        },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);//true表示使用二十四小时制
        //把时间对话框显示在界面上
        dialog.show();

    }


    private void shareJsonFile(File jsonFile) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        // 比如发送二进制文件数据流内容（比如图片、视频、音频文件等等）
    // 指定发送的内容 (EXTRA_STREAM 对于文件 Uri )
        Uri uri = FileProvider.getUriForFile(this,this.getPackageName()+".fileprovider", jsonFile);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
    // 指定发送内容的类型 (MIME type)
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent,"选择目标同步服务器软件"));
    }

    private void createJson(String fileName,String messageContent,int displayMode) throws JSONException, IOException {
        File file = new File(getApplicationContext().getFilesDir(), fileName+".json");
        currentMessageFileLocation = getApplicationContext().getFilesDir().toString() + "/" +  fileName + ".json";
        //实例化一个JSONObject对象
        JSONObject jsonMessage = new JSONObject();
        //property的子属性
        JSONObject property = new JSONObject();
        property.put("FileContent", messageContent);
        property.put("FileType", "json");
        property.put("FileSender", "SyncNotify User");
        property.put("FileLocation", "null");
        property.put("FileName", fileName);
        property.put("FileCreatingDate", simpleDateFormat.format(new Date().getTime()));
        //display的子属性
        JSONObject display = new JSONObject();
        if (displayMode == 4){
            display.put("fileDisplayTime", simpleDateFormat.format(calendar.getTime()).toString());
        }else{
            display.put("fileDisplayTime", "");
        }
        display.put("fileDisplayMode",displayMode);
//        //实例化一个JSON数组
//        JSONArray message = new JSONArray();
//        //将course1添加到JSONArray，下标为0
//        message.put(0, property);
//        message.put(1, display);
        jsonMessage.put("property",property);
        jsonMessage.put("display",display);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(jsonMessage.toString().getBytes());
        fos.close();
        file = null;
    }


}