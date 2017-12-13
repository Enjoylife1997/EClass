package com.example.eclass.BasicActivity;

/*
    扫描二维码
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Vibrator;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eclass.R;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanActivity extends Activity implements QRCodeView.Delegate {

    private QRCodeView mQR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_layout);

        mQR = (ZXingView) findViewById(R.id.zx_view);

        //设置结果处理
        mQR.setDelegate(this);

        //开始读取二维码R
        mQR.startSpot();

    }


    /**
     * 扫描二维码成功
     * @param result
     */
    @Override
    public void onScanQRCodeSuccess(String result) {

        Toast.makeText(ScanActivity.this,"扫描成功", Toast.LENGTH_SHORT).show();
        //将获取到的扫描信息传回IndexActivity
        Intent intent = new Intent();
        intent.putExtra("data_return",result);
        setResult(RESULT_OK,intent);
        //震动
        vibrate();
        //停止预览
        mQR.stopCamera();
        finish();

    }

    /**
     * 打开相机出错
     */
    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(ScanActivity.this, "打开相机出错！请检查是否开启权限！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 震动
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //启动相机
        mQR.startCamera();
    }

    @Override
    protected void onStop() {
        mQR.stopCamera();
        super.onStop();
    }

    public void onBackPressed() {
        //TODO something
        super.onBackPressed();
    }

}
