package com.stpl.edurp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.stpl.edurp.R;
import com.stpl.edurp.network.IWSRequest;
import com.stpl.edurp.network.WSRequest;

/**
 * Created by Admin on 26-11-2016.
 */

public class TestParseResponse extends Activity {
    final String TAG = "DemoParseResponse";
    private TextView mTextView;
    private String url ="http://query.yahooapis.com/v1/public/yql?q=select%20%2a%20from%20yahoo.finance.quotes%20WHERE%20symbol%3D%27WRC%27&format=json&diagnostics=true&env=store://datatables.org/alltableswithkeys&callback";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_parse_data);
        initView();
        loadData();
    }

    private void loadData() {
        WSRequest.getInstance().request(WSRequest.GET, url, TAG , new IWSRequest() {
            @Override
            public void onResponse(String response) {
                mTextView.setText(response);
            }

            @Override
            public void onErrorResponse(VolleyError response) {

            }
        });
    }

    private void initView() {
         mTextView = (TextView) findViewById(R.id.textview_parse_data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
