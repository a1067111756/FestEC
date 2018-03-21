package com.ali.latte.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.ali.latte.app.Latte;
import com.ali.latte.net.callback.IRequset;
import com.ali.latte.net.callback.ISuccess;
import com.ali.latte.utils.file.FileUtil;
import com.blankj.utilcode.util.FileUtils;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by 澄鱼 on 2018/3/22.
 */

public class SaveFileTask extends AsyncTask<Object, Void, File>{

    private final IRequset REQUSET;
    private final ISuccess SUCESS;

    public SaveFileTask(IRequset REQUSET, ISuccess SUCESS) {
        this.REQUSET = REQUSET;
        this.SUCESS = SUCESS;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir =  (String)params[0];
        String extension =  (String)params[1];
        final String name = (String)params[4];
        final ResponseBody body = (ResponseBody) params[2];
        final InputStream is = body.byteStream();
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "ec_down_loads";
        }
        if (extension == null || extension.equals("")) {
            extension = "";
        }
        if (name == null) {
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            return FileUtil.writeToDisk(is, downloadDir, name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);

        if (SUCESS != null) {
            SUCESS.onSuccess(file.getPath());
        }
        if (REQUSET != null) {
            REQUSET.onRequestEnd();
        }
    }

    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(install);
        }
    }
}
