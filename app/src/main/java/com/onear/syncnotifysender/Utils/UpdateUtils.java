package com.onear.syncnotifysender.Utils;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.onear.syncnotifysender.BuildConfig;
import com.onear.syncnotifysender.UpdateInformation;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class UpdateUtils  {
    public Intent getUpdateIntent(String jsonURI){
        HttpUtils httpUtils = new HttpUtils();
        String jsonContent = httpUtils.getJsonContent(jsonURI);
        Log.d(TAG,"json消息是" + jsonContent);
        UpdateInformation updateInformation = JSON.parseObject(jsonContent,UpdateInformation.class);
        if (updateInformation != null){
            if (updateInformation.getInternalVersion() > BuildConfig.VERSION_CODE){
                Intent intent = new Intent(Intent.ACTION_VIEW);  //调用系统内置动作
                //用Uri.parse（）方法将网址字符串解析成uri对象，再用Intent的setData（）方法将Uri对象传递进去
                intent.setData(Uri.parse(updateInformation.getTargetURI()));
                return intent;
            }else{
                return null;
            }
        }else{
            return null;
        }

    }


}
