package com.example.eclass.Download;

/**
 * Created by Enjoy life on 2017/10/6.
 */

public interface DownloadListener {

    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCanceled();
}
