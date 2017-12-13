package com.example.eclass.BasicActivity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eclass.Download.DownloadService;
import com.example.eclass.R;

/*
    该活动用于上传和下载测验题文档
 */


public class DownloadActivity extends AppCompatActivity implements View.OnClickListener{

    private  DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_layout);
        Button startDownload = (Button)findViewById(R.id.start_download);
        Button pauseDownload = (Button)findViewById(R.id.pause_download);
        Button cancelDownload = (Button)findViewById(R.id.cancel_download);
        startDownload.setOnClickListener(this);
        pauseDownload.setOnClickListener(this);
        cancelDownload.setOnClickListener(this);
        Intent intent = new Intent(this,DownloadService.class);
        startService(intent);//启动服务
        bindService(intent,connection,BIND_AUTO_CREATE);//绑定服务
        if (ContextCompat.checkSelfPermission(DownloadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DownloadActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    public void onClick(View v){
        if (downloadBinder == null){
            return;
        }
        switch (v.getId()){
            case R.id.start_download:
                //输入需要下载文件所在路径
                String url = "http://d.lanrentuku.com/down/tupian/1706/272107205_lanrentuku.com.zip";
                downloadBinder.startDownload(url);
                break;
            case R.id.pause_download:
                downloadBinder.pauseDownload();
                break;
            case R.id.cancel_download:
                downloadBinder.cancelDownload();
                break;
            default:
                break;
        }
    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        //活动若被销毁需要对服务进行解绑，否则会造成内存泄漏
        unbindService(connection);
    }
}
