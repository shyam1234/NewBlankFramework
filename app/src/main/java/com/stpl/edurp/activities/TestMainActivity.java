package com.stpl.edurp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.stpl.edurp.R;
import com.stpl.edurp.utils.CustomDialogbox;

/**
 * Created by Admin on 26-11-2016.
 */

public class TestMainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Button btn = (Button) findViewById(R.id.btn_show);
        Button btnParseURL = (Button) findViewById(R.id.btn_parse_url);
        btn.setOnClickListener(this);
        btnParseURL.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                startActivity(new Intent(TestMainActivity.this, TestImageLoad.class));
                break;
            case R.id.btn_parse_url:
                startActivity(new Intent(TestMainActivity.this, TestParseResponse.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
        final CustomDialogbox dialogbox = new CustomDialogbox(TestMainActivity.this, CustomDialogbox.TYPE_YES_NO);
        dialogbox.setCancelable(false);
      // dialogbox.setTitle("Do you want to exit?");
        dialogbox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogbox.show();
        dialogbox.getBtnYes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogbox.dismiss();
                finish();
            }
        });

        dialogbox.getBtnNo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogbox.dismiss();
            }
        });
    }
}
