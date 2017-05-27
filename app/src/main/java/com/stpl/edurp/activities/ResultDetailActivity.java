package com.stpl.edurp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.stpl.edurp.R;
import com.stpl.edurp.adapters.ResultDetailsAdapter;
import com.stpl.edurp.constant.Constant;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.database.TableResultDetails;
import com.stpl.edurp.interfaces.ICallBack;
import com.stpl.edurp.models.LoginDataModel;
import com.stpl.edurp.models.ModelFactory;
import com.stpl.edurp.models.TableResultDetailsDataModel;
import com.stpl.edurp.models.TableResultMasterDataModel;
import com.stpl.edurp.network.IWSRequest;
import com.stpl.edurp.network.WSRequest;
import com.stpl.edurp.parser.ParseResponse;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.DownloadFileAsync;
import com.stpl.edurp.utils.GetPicassoImage;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 23508 on 2/7/2017.
 */
public class ResultDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final java.lang.String TAG = "ResultDetailActivity";
    private RecyclerView mRecycleViewResultList;
    private ResultDetailsAdapter mResultDetailsAdapter;
    private TableResultMasterDataModel mResultDataModel;
   // private TableResultDetailsDataModel mMobileDetailsHolder;
    private Button mBtnDownload;
    private Button mBtnDelete;
    private Button mBtnView;
    private ArrayList<TableResultDetailsDataModel.InnerResultDetails> mMobileDetailsHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);
        init();
        initView();
        fetchDataFromServer();

    }


    private void init() {
        mMobileDetailsHolder = new TableResultDetailsDataModel().getMessageBody();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mResultDataModel = (TableResultMasterDataModel) bundle.getSerializable(Constant.TAG_HOLDER);
            AppLog.log(TAG, "ref id: " + mResultDataModel.getReferenceId());
        }
    }


    private void initView() {
        TextView mTextViewTitle = (TextView) findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_result_details);
        ImageView mImgProfile = (ImageView) findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetPicassoImage.setCircleImageByPicasso(this, UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        //------------------------------------
        mBtnDownload = (Button) findViewById(R.id.btn_results_download);
        mBtnDownload.setOnClickListener(this);
        //-------------------------------------
        mBtnDelete = (Button) findViewById(R.id.btn_results_delete);
        mBtnView = (Button) findViewById(R.id.btn_results_view);
        mBtnView.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        //-------------------------------------
        if (Utils.isFileDownloaded(this, WSContant.DOWNLOAD_FOLDER, "" + UserInfo.studentId + ".pdf")) {
            mBtnDownload.setVisibility(View.GONE);
            mBtnView.setVisibility(View.VISIBLE);
            mBtnDelete.setVisibility(View.VISIBLE);
        } else {
            mBtnDownload.setVisibility(View.VISIBLE);
            mBtnView.setVisibility(View.GONE);
            mBtnDelete.setVisibility(View.GONE);
        }
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecycleViewResultList = (RecyclerView) findViewById(R.id.recyclerview_results_list);
        mRecycleViewResultList.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewResultList.setLayoutManager(manager);
        mResultDetailsAdapter = new ResultDetailsAdapter(ResultDetailActivity.this, mMobileDetailsHolder, this);
        mRecycleViewResultList.setAdapter(mResultDetailsAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Utils.animLeftToRight(ResultDetailActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_back:
                onBackPressed();
                break;
            case R.id.imageview_resultlist_row_selection:
                int position = (Integer) v.getTag();
                ((ImageView) (v.findViewById(R.id.imageview_resultlist_row_selection))).setImageResource(R.drawable.selected);
                //ResultFragment.selected_sem = mResultDetailList.get(position).getSemester();
                finish();
                break;
            case R.id.btn_results_download:
                new DownloadFileAsync(ResultDetailActivity.this, WSContant.DOWNLOAD_FOLDER, new ICallBack() {
                    @Override
                    public void callBack() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mBtnDownload != null) {
                                    mBtnDownload.setVisibility(View.GONE);
                                    mBtnView.setVisibility(View.VISIBLE);
                                    mBtnDelete.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                }).execute(WSContant.URL_PRINT_OVERALL_RESULT, "" + UserInfo.studentId);
                break;
            case R.id.btn_results_delete:
                Utils.deleteDownloadFile(this, WSContant.DOWNLOAD_FOLDER, "" + UserInfo.studentId + ".pdf", new ICallBack() {
                    @Override
                    public void callBack() {
                        if (mBtnDelete != null) {
                            mBtnDelete.setVisibility(View.GONE);
                            mBtnView.setVisibility(View.GONE);
                            mBtnDownload.setVisibility(View.VISIBLE);
                        }
                    }
                });
                break;
            case R.id.btn_results_view:
                Utils.showDownloadFile(this, WSContant.DOWNLOAD_FOLDER, "" + UserInfo.studentId + ".pdf");
                break;
        }
    }


    private void fetchDataFromServer() {
        TableResultDetails table = new TableResultDetails();
        table.openDB(this);
        table.openDB(ResultDetailActivity.this);
        mMobileDetailsHolder = table.getValue((mResultDataModel != null ? mResultDataModel.getReferenceId() : 0));
        table.closeDB();
        if (Utils.isInternetConnected(this)) {


            //call to WS and validate given credential----
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
            //-Utils-for body
            Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_MENUCODE, "" + UserInfo.menuCode);
            body.put(WSContant.TAG_REFERENCEID, "" + (mResultDataModel != null ? mResultDataModel.getReferenceId() : ""));
            body.put(WSContant.TAG_REFERENCEDATE, "" + UserInfo.timeTableRefDate);
            Utils.showProgressBar(this);
            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEDETAILS, header, body, WSContant.TAG_GETMOBILEDETAILS, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_RESULTDETAILS);
                    mMobileDetailsHolder =  ((TableResultDetailsDataModel)obj.getModel()).getMessageBody();
                    saveIntoDatabase();
                    Utils.dismissProgressBar();
                    initRecyclerView();
                }

                @Override
                public void onErrorResponse(VolleyError response) {
                    Utils.dismissProgressBar();
                }
            });
        } else {
            initRecyclerView();
        }
    }

    private void saveIntoDatabase() {
        TableResultDetails table = new TableResultDetails();
        table.openDB(ResultDetailActivity.this);
        table.insert(mMobileDetailsHolder);
        table.closeDB();
    }


}
