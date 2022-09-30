package com.qhendhwerhjjqshd.dshfretnfqqdfg.hjjqiandhenbfgutil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadHaojjQianShsjHajduUtil {
    private static DownloadHaojjQianShsjHajduUtil downloadHaojjQianShsjHajduUtil;
    private final OkHttpClient client;

    public static DownloadHaojjQianShsjHajduUtil get() {
        if (downloadHaojjQianShsjHajduUtil == null) {
            downloadHaojjQianShsjHajduUtil = new DownloadHaojjQianShsjHajduUtil();
        }
        return downloadHaojjQianShsjHajduUtil;
    }

    private DownloadHaojjQianShsjHajduUtil() {
        client = new OkHttpClient();
    }

    public void downloadApk(final String url, final String destFileDir, final String destFileName, final OnDownloadListener listener) {
        File file = new File(destFileDir, destFileName);
        if (file.exists()) {
            file.delete();
        }
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败监听回调
                listener.onDownloadFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                File dir = new File(destFileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, destFileName);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    listener.onDownloadSuccess(file);
                } catch (Exception e) {
                    listener.onDownloadFailed(e);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    public interface OnDownloadListener {
        void onDownloadSuccess(File file);

        void onDownloading(int progress);

        void onDownloadFailed(Exception e);
    }
}
