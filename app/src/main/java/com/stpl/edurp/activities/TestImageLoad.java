package com.stpl.edurp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.stpl.edurp.R;
import com.stpl.edurp.utils.GetPicassoImage;
import com.stpl.edurp.utils.RenderImageByUIL;

public class TestImageLoad extends AppCompatActivity implements View.OnClickListener{

    private Button btnShow1;
    private Button btnShow2;
    private Button btnShow3;
    private Button btnShow4;
    private ImageView imageHolder;
    private String url = "https://digitalstrategyworld.files.wordpress.com/2014/10/digital-marketing.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_image);
        initView();
    }

    private void initView() {
        btnShow1 = (Button)findViewById(R.id.btn_show1);
        btnShow2 = (Button)findViewById(R.id.btn_show2);
        btnShow3 = (Button)findViewById(R.id.btn_show3);
        btnShow4 = (Button)findViewById(R.id.btn_show4);
        imageHolder = (ImageView)findViewById(R.id.imageview);
        btnShow1.setOnClickListener(this);
        btnShow2.setOnClickListener(this);
        btnShow3.setOnClickListener(this);
        btnShow4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
         switch (view.getId()){
             case R.id.btn_show1:
                 GetPicassoImage.getImage(TestImageLoad.this,url, imageHolder);
                 break;
             case R.id.btn_show2:
                 GetPicassoImage.setCircleImageByPicasso(TestImageLoad.this,url, imageHolder);
                 break;
             case R.id.btn_show3:
                 GetPicassoImage.setSquareImageByPicasso(TestImageLoad.this,url, imageHolder);
                 break;
             case R.id.btn_show4:
                 RenderImageByUIL.getInstance(TestImageLoad.this).setImageByURL(url,imageHolder,R.mipmap.ic_launcher,R.drawable.loader);
                 break;

         }
    }

}
