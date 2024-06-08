package com.onear.syncnotifysender;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;


import java.io.File;


public class ShareFileHelper {



    public boolean checkAppInstalled(Context context, String pkgName) {
        try {
            context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (Exception x) {
            return false;
        }
        return true;
    }


}
