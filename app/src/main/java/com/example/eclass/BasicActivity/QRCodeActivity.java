package com.example.eclass.BasicActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.eclass.R;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;


public class QRCodeActivity extends Activity implements View.OnClickListener {



//  private android.widget.EditText etinput;
    //暂且设置为固定密码生成的二维码
    private TextView tvcreate;
    private ImageView ivqr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_layout);
        this.tvcreate = (TextView) findViewById(R.id.tv_create);
        //this.etinput = (EditText) findViewById(R.id.et_input);
        this.ivqr = (ImageView) findViewById(R.id.iv_qr);


        tvcreate.setOnClickListener(this);

    }

    public void onClick(View view){
        createQRCode();
        Toast.makeText(QRCodeActivity.this,"生成二维码且点名成功",Toast.LENGTH_SHORT).show();
    }


    /*
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_create:            //创建二维码

                if(!checkIsEmpty()){
                    createQRCode();
                    Toast.makeText(QRCodeActivity.this,"生成二维码且点名成功",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(QRCodeActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();

                break;

        }
    }
    */

    /**
     * 校验输入框是否有内容
     * 没有内容返回true，有内容返回false
     * @return
     */
    /*
    private boolean checkIsEmpty(){

        return TextUtils.isEmpty(etinput.getText().toString().trim());

    }
    */



    /**
     * 创建二维码
     */
    private void createQRCode() {
        //开启线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode("qrcodetest", 400);//传入字符串设置为固定,可以从Edit中获取
                //把产生的Bitmap赋值到ImageView中,但是要在主线程中运行
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivqr.setImageBitmap(bitmap);
                    }
                });

            }
        }).start();
    }

}
