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
        Log.d(TAG,"接受Json消息：" + jsonContent);
        UpdateInformation updateInformation = JSON.parseObject(jsonContent,UpdateInformation.class);
        if (updateInformation != null){
            if (updateInformation.getInternalVersion() > BuildConfig.VERSION_CODE){
                Intent intent = new Intent(Intent.ACTION_VIEW);  //调用系统内置动作
                //用Intent的setData（）方法将Uri对象传递进去
                intent.setData(updateInformation.getTargetURI());
                intent.putExtra("com.sn.UpdateLog",updateInformation.getUpdateLog());
                return intent;
            }else{
                return null;
            }
        }else{
            return null;
        }

    }


}
