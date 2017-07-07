package com.stpl.edurp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.stpl.edurp.R;
import com.stpl.edurp.adapters.CustomPagerAdapter;
import com.stpl.edurp.adapters.NewsDetailsCommentAdapter;
import com.stpl.edurp.adapters.NewsDetailsLikeAdapter;
import com.stpl.edurp.constant.Constant;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.database.TableDocumentMaster;
import com.stpl.edurp.database.TableNewsMaster;
import com.stpl.edurp.models.GetMobileDetailsDataModel;
import com.stpl.edurp.models.LoginDataModel;
import com.stpl.edurp.models.ModelFactory;
import com.stpl.edurp.models.NewsDetailsCommentLikeDataModel;
import com.stpl.edurp.models.TableDocumentMasterDataModel;
import com.stpl.edurp.models.TableNewsMasterDataModel;
import com.stpl.edurp.network.IWSRequest;
import com.stpl.edurp.network.WSRequest;
import com.stpl.edurp.parser.ParseResponse;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.GetUILImage;
import com.stpl.edurp.utils.InternetManager;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;
import com.stpl.edurp.utils.ZoomOutPageTransformer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by 23508 on 2/7/2017.
 */

public class NewsDetails extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final java.lang.String TAG = "NewsDetails";
    private TableNewsMasterDataModel mNewsMasterDataModel;
    private TextView mTextViewRefTitle;
    private TextView mTextViewPublishedBy;
    private TextView mTextViewTime;
    private WebView mWebViewNewsBody;
    //private GetMobileDetailsDataModel mMobileDetailsHolder;
    private ViewPager mViewPagerNewsImages;
    private CustomPagerAdapter mCustomPagerAdapter;
    private int dotsCount;
    private ImageView[] dots;
    private LinearLayout pager_indicator;
    private BottomSheetBehavior<View> behavior;
    //private  String[] mImagesList = new String[1];
    private View bottomSheet;
    private BottomSheetDialog mBottomSheetDialog;
    private TextView mTextViewCommentTab;
    private TextView mTextViewLikeTab;
    private RecyclerView mRecycleViewCommentLike;
    private EditText mEditTextComment;
    private TextView mTextViewSend;
    private NewsDetailsLikeAdapter mNewsDetailsLikeAdapter;
    private NewsDetailsCommentLikeDataModel mNewsDetailsCommentLikeDataModel;
    private NewsDetailsCommentAdapter mNewsDetailsCommentAdapter;
    private RelativeLayout mRelComment;
    private TextView mTextViewTitle;
    //private ArrayList<GetMobileDetailsDataModel.MessageBodyDataModel> mMesgBodyDataList;
    private ArrayList<TableDocumentMasterDataModel> mTableDocumentMesgList;
    private int mReferenceId;
    private ImageView mImageview_bottom_like;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        init();
        initView();

        if (InternetManager.isInternetConnected(this)) {
            fetchDataFromServer();
        } else {
            TableDocumentMaster table = new TableDocumentMaster();
            table.openDB(this);
            mTableDocumentMesgList = table.getDocument(mNewsMasterDataModel.getDocumentMasterId()
                   /* , mNewsMasterDataModel.getReferenceId()*/);
            table.closeDB();
            bindDataWithUI();
        }

    }

    private void init() {
        mNewsDetailsCommentLikeDataModel = new NewsDetailsCommentLikeDataModel();
        mTableDocumentMesgList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mReferenceId = (int) bundle.getSerializable(Constant.TAG_HOLDER);
            AppLog.log(TAG,"getReferenceId: "+mReferenceId);
        }
        mNewsMasterDataModel = getNewsMasterDataModel();
    }

    private void initView() {
        //------------------------------------
        mTextViewTitle = (TextView) findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.title_news_details);
        ImageView mImgProfile = (ImageView) findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetUILImage.getInstance().setCircleImage(this, UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        //------------------------------------
        mTextViewRefTitle = (TextView) findViewById(R.id.textview_news_details_reference_title);
        mTextViewPublishedBy = (TextView) findViewById(R.id.textview_news_details_published_by);
        mTextViewTime = (TextView) findViewById(R.id.textview_news_details_published_time);
        //--------------------------------
        mViewPagerNewsImages = (ViewPager) findViewById(R.id.viewpager_news_details);
        mWebViewNewsBody = (WebView) findViewById(R.id.webview_news_row_body);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        mViewPagerNewsImages.addOnPageChangeListener(this);
        mViewPagerNewsImages.setPageTransformer(true, new ZoomOutPageTransformer());

        mImageview_bottom_like = (ImageView)findViewById(R.id.imageview_bottom_like);
        TextView textView = (((TextView) findViewById(R.id.textview_inc_bottom_like)));
        if (mNewsMasterDataModel.getLikedByMe() == 1) {
            textView.setText(getResources().getString(R.string.unlike));
            mImageview_bottom_like.setImageResource(R.drawable.comments_like_icon_active);
        } else {
            textView.setText(getResources().getString(R.string.like));
            mImageview_bottom_like.setImageResource(R.drawable.comments_like_icon);
        }

        RelativeLayout rel_like = (RelativeLayout) findViewById(R.id.rel_inc_like_comment_like_holder);
        rel_like.setOnClickListener(this);
        RelativeLayout rel_comment = (RelativeLayout) findViewById(R.id.rel_inc_like_comment_comment_holder);
        rel_comment.setOnClickListener(this);
        initBottomSheetLayout();
        setLangSelection();
    }


    private void fetchDataFromServer() {
        //call to WS and validate given credential----
        Map<String, String> header = new HashMap<>();
        header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
        header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
        // header.put(WSContant.TAG_DATELASTRETRIEVED, Utils.getLastRetrivedTimeForNews());
        //header.put(WSContant.TAG_NEW, Utils.getCurrTime());
        //-Utils-for body
        Map<String, String> body = new HashMap<>();
        body.put(WSContant.TAG_MENUCODE, "" + UserInfo.menuCode);
        body.put(WSContant.TAG_REFERENCEID, "" + (mNewsMasterDataModel != null ? mNewsMasterDataModel.getReferenceId() : ""));
        body.put(WSContant.TAG_REFERENCEDATE, "" + UserInfo.timeTableRefDate);
        Utils.showProgressBar(this);
        WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEDETAILS, header, body, WSContant.TAG_GETMOBILEDETAILS, new IWSRequest() {
            @Override
            public void onResponse(String response) {
                ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_GETMOBILEDETAILS);
                GetMobileDetailsDataModel pMobileDetailsHolder = ((GetMobileDetailsDataModel) obj.getModel());
                mNewsMasterDataModel.setNewsBody(pMobileDetailsHolder.getMessageBody().get(0).getMessageBodyHTML());
                mTableDocumentMesgList = pMobileDetailsHolder.getDocuments();
                saveDataIntoTable("" + (mNewsMasterDataModel != null ? mNewsMasterDataModel.getReferenceId() : ""), mNewsMasterDataModel.getNewsBody(), mTableDocumentMesgList);
                bindDataWithUI();
                fetchCommentDataFromServer(0);
            }

            @Override
            public void onErrorResponse(VolleyError response) {
                Utils.dismissProgressBar();
            }
        });
    }

    private void bindDataWithUI() {
        if (mNewsMasterDataModel != null) {
            mTextViewRefTitle.setText(mNewsMasterDataModel.getReferenceTitle());
            mTextViewPublishedBy.setText(mNewsMasterDataModel.getPublishedBy());
            mTextViewTime.setText(mNewsMasterDataModel.getPublishedOn());
            mWebViewNewsBody.loadData(mNewsMasterDataModel.getNewsBody(), "text/html; charset=utf-8", "utf-8");
            //--------------------------------------------
            if (mTableDocumentMesgList != null) {
                mCustomPagerAdapter = new CustomPagerAdapter(this, mTableDocumentMesgList, this);
                mViewPagerNewsImages.setAdapter(mCustomPagerAdapter);
                mViewPagerNewsImages.setCurrentItem(0);
            }
            setUiPageViewController();
        }
    }


    private void saveDataIntoTable(String pRefId, String pMessageBodyList, ArrayList<TableDocumentMasterDataModel> pDocumentList) {
        TableNewsMaster newsTable = new TableNewsMaster();
        newsTable.openDB(this);
        newsTable.insertMessageBody(pRefId, pMessageBodyList);
        newsTable.closeDB();

        TableDocumentMaster table = new TableDocumentMaster();
        table.openDB(this);
        table.insert(pDocumentList);
        table.closeDB();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
        } else if (behavior != null && behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            finish();
            Utils.animLeftToRight(NewsDetails.this);
        }
    }


    private void setUiPageViewController() {
        dotsCount = mCustomPagerAdapter.getCount();
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0);
            pager_indicator.addView(dots[i], params);
        }

        if (dots.length > 0)
            dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (dots != null) {
            for (int i = 0; i < dotsCount; i++) {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            }
            if (dots.length > 0)
                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

            if (position + 1 == dotsCount) {
                // btnNext.setVisibility(View.GONE);
                // btnFinish.setVisibility(View.VISIBLE);
            } else {
                // btnNext.setVisibility(View.VISIBLE);
                // btnFinish.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void navigateToNextPage(int posi) {
        Intent i = new Intent(this, NewsScreenSliderPagerActivity.class);
        //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle = new Bundle();
        if (mTableDocumentMesgList != null) {
            bundle.putSerializable(Constant.TAG_HOLDER, mTableDocumentMesgList);
        }
        bundle.putInt(Constant.TAG_POSITION, posi);
        i.putExtras(bundle);
        startActivity(i);
        Utils.animRightToLeft(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        JCVideoPlayer.releaseAllVideos();
    }


    private void viewComment() {
        onShareClick();
    }


    private void initBottomSheetLayout() {
        // The View with the BottomSheetBehavior
        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheet.setVisibility(View.VISIBLE);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    findViewById(R.id.lin_main_content).setEnabled(false);
                    findViewById(R.id.lin_main_content).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.lin_main_content).setEnabled(true);
                    findViewById(R.id.lin_main_content).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        //decide the initial height
        behavior.setPeekHeight(0);
        //-----------------------------------------------
        mTextViewCommentTab = (TextView) findViewById(R.id.textview_comment_like_page_comment);
        mTextViewLikeTab = (TextView) findViewById(R.id.textview_comment_like_page_like);
        mEditTextComment = (EditText) findViewById(R.id.edittext_send_comment_comment);
        mTextViewSend = (TextView) findViewById(R.id.textview_send_comment_send);
        mRelComment = (RelativeLayout) findViewById(R.id.rel_comment);
        //------------------------------------------------
        mTextViewSend.setOnClickListener(this);
        mTextViewCommentTab.setOnClickListener(this);
        mTextViewLikeTab.setOnClickListener(this);

        initRecyclerView();

    }

    private void initRecyclerView() {
        mRecycleViewCommentLike = (RecyclerView) findViewById(R.id.recyclerview_comment_like_page);
        mRecycleViewCommentLike.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewCommentLike.setLayoutManager(manager);

    }


    public void onShareClick() {
        if (behavior.getState() == BottomSheetBehavior.STATE_HIDDEN || behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            getComments();
        } else {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_back:
                onBackPressed();
                break;
            case R.id.imageView:
                int posi = (Integer) v.getTag();
                navigateToNextPage(posi);
                break;
            case R.id.rel_inc_like_comment_like_holder:
                if (InternetManager.isInternetConnected(this)) {
                    doLike(((TextView) v.findViewById(R.id.textview_inc_bottom_like)));
                } else {
                    //Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rel_inc_like_comment_comment_holder:
                if (InternetManager.isInternetConnected(this)) {
                    viewComment();
                }
                break;
            case R.id.textview_comment_like_page_comment:
                if (InternetManager.isInternetConnected(this)) {
                    getComments();
                }
                break;
            case R.id.textview_comment_like_page_like:
                if (InternetManager.isInternetConnected(this)) {
                    getLikes();
                }
                break;
            case R.id.textview_send_comment_send:
                if (InternetManager.isInternetConnected(this)) {
                    doComment(((EditText) findViewById(R.id.edittext_send_comment_comment)));
                } else {
                    // Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private void getLikes() {
        mRelComment.setVisibility(View.GONE);
        mTextViewLikeTab.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        mTextViewCommentTab.setBackgroundColor(Color.TRANSPARENT);
        mTextViewLikeTab.setTextColor(getResources().getColor(R.color.colorGreen));
        mTextViewCommentTab.setTextColor(getResources().getColor(R.color.colorWhite));
        mNewsDetailsLikeAdapter = new NewsDetailsLikeAdapter(NewsDetails.this, mNewsDetailsCommentLikeDataModel.getLikeMaster());
        mRecycleViewCommentLike.setAdapter(mNewsDetailsLikeAdapter);
        mNewsDetailsLikeAdapter.notifyDataSetChanged();

    }

    private void getComments() {
        mRelComment.setVisibility(View.VISIBLE);
        mTextViewCommentTab.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        mTextViewLikeTab.setBackgroundColor(Color.TRANSPARENT);
        mTextViewCommentTab.setTextColor(getResources().getColor(R.color.colorGreen));
        mTextViewLikeTab.setTextColor(getResources().getColor(R.color.colorWhite));
        mNewsDetailsCommentAdapter = new NewsDetailsCommentAdapter(NewsDetails.this, mNewsDetailsCommentLikeDataModel.getCommentMaster());
        mRecycleViewCommentLike.setAdapter(mNewsDetailsCommentAdapter);
        mNewsDetailsCommentAdapter.notifyDataSetChanged();
    }

    private void fetchCommentDataFromServer(final int type) {
        if (InternetManager.isInternetConnected(this)) {
            //call to WS and validate given credential----
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            header.put(WSContant.TAG_DATELASTRETRIEVED, Utils.getLastRetrivedTimeForNews());
            header.put(WSContant.TAG_NEW, Utils.getCurrTime());
            header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
            //-Utils-for body
            Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_MENUCODE, "" + UserInfo.menuCode);
            body.put(WSContant.TAG_REFERENCEID, "" + (mNewsMasterDataModel != null ? mNewsMasterDataModel.getReferenceId() : ""));
            // Utils.showProgressBar(this);
            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILECOMMENTSNLIKES, header, body, WSContant.TAG_COMMENT, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_NEWS_DETAILS_COMMENTS_LIKE);
                    mNewsDetailsCommentLikeDataModel = ((NewsDetailsCommentLikeDataModel) obj.getModel());

                    if ((type == 2) && mNewsDetailsCommentAdapter != null) {
                        getComments();
                        mRecycleViewCommentLike.setAdapter(mNewsDetailsCommentAdapter);
                      //  mRecycleViewCommentLike.smoothScrollToPosition(mNewsDetailsCommentLikeDataModel.getCommentMaster().size() - 1);
                        mRecycleViewCommentLike.smoothScrollToPosition(0);
                    } else if ((type == 1) && mNewsDetailsLikeAdapter != null) {
                        getLikes();
                        //mRecycleViewCommentLike.smoothScrollToPosition(mNewsDetailsCommentLikeDataModel.getLikeMaster().size() - 1);
                        mRecycleViewCommentLike.smoothScrollToPosition(0);
                    }
                    Utils.dismissProgressBar();
                }

                @Override
                public void onErrorResponse(VolleyError response) {
                    Utils.dismissProgressBar();
                }
            });
        } else {
            //Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }


    private void doLike(final TextView textView) {
        final String pStr = textView.getText().toString().trim();
        Map<String, String> header = new HashMap<>();
        header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
        header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
        header.put(WSContant.TAG_DATELASTRETRIEVED, Utils.getLastRetrivedTimeForNews());
        header.put(WSContant.TAG_NEW, Utils.getCurrTime());

        //-Utils-for body
        final Map<String, String> body = new HashMap<>();
        body.put(WSContant.TAG_MENUCODE, "" + UserInfo.menuCode);
        body.put(WSContant.TAG_REFERENCEID, "" + (mNewsMasterDataModel != null ? mNewsMasterDataModel.getReferenceId() : ""));
        body.put(WSContant.TAG_USERID, "" + UserInfo.userId);
        body.put(WSContant.TAG_COMMENT, "");
        if (mNewsMasterDataModel.getLikedByMe() == 1) {
            body.put(WSContant.TAG_ISLIKE, "0");
            body.put(WSContant.TAG_ISDELETE, "1");
        } else {
            body.put(WSContant.TAG_ISLIKE, "1");
            body.put(WSContant.TAG_ISDELETE, "0");
        }

        Utils.showProgressBar(this);
        WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_SAVELIKENCOMMENT, header, body, WSContant.TAG_SAVELIKECOMMENT, new IWSRequest() {
            @Override
            public void onResponse(String response) {
                Utils.dismissProgressBar();
                if (response != null && response.length() > 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String resp = jsonObject.getString(WSContant.TAG_MESSAGERESULT);
                        if (resp.equalsIgnoreCase(WSContant.TAG_OK)) {
                            if (mNewsMasterDataModel.getLikedByMe() == 1) {
                                saveLikeIntoDB(0);
                                textView.setText(getResources().getString(R.string.like));
                                mImageview_bottom_like.setImageResource(R.drawable.comments_like_icon);
                            } else {
                                textView.setText(getResources().getString(R.string.unlike));
                                mImageview_bottom_like.setImageResource(R.drawable.comments_like_icon_active);
                                saveLikeIntoDB(1);
                            }
                            fetchCommentDataFromServer(1);
                        } else {
                            Toast.makeText(NewsDetails.this, R.string.msg_network_prob, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        AppLog.errLog(TAG, e.getMessage());
                    }
                }

            }

            @Override
            public void onErrorResponse(VolleyError response) {
                Utils.dismissProgressBar();
            }
        });
    }




    private void doComment(final EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
            header.put(WSContant.TAG_DATELASTRETRIEVED, Utils.getLastRetrivedTimeForNews());
            header.put(WSContant.TAG_NEW, Utils.getCurrTime());
            //-Utils-for body
            final Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_MENUCODE, "" + UserInfo.menuCode);
            body.put(WSContant.TAG_REFERENCEID, "" + (mNewsMasterDataModel != null ? mNewsMasterDataModel.getReferenceId() : ""));
            body.put(WSContant.TAG_USERID, "" + UserInfo.userId);
            body.put(WSContant.TAG_COMMENT, editText.getText().toString().trim());
            body.put(WSContant.TAG_ISLIKE, "0");
            body.put(WSContant.TAG_ISDELETE, "0");
            Utils.showProgressBar(this);
            Utils.hideNativeKeyboard(this);
            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_SAVELIKENCOMMENT, header, body, WSContant.TAG_SAVELIKECOMMENT, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    Utils.dismissProgressBar();
                    if (response != null && response.length() > 0) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String resp = jsonObject.getString(WSContant.TAG_MESSAGERESULT);
                            if (resp.equalsIgnoreCase(WSContant.TAG_OK)) {
                                editText.setText("");
                                Toast.makeText(NewsDetails.this, R.string.msg_success_msg_post, Toast.LENGTH_SHORT).show();
                                fetchCommentDataFromServer(2);
                            } else {
                                Toast.makeText(NewsDetails.this, R.string.msg_network_prob, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            AppLog.errLog(TAG, e.getMessage());
                        }
                    }
                }

                @Override
                public void onErrorResponse(VolleyError response) {
                    Utils.dismissProgressBar();
                }
            });
        } else {
            Toast.makeText(NewsDetails.this, R.string.msg_validate_comment, Toast.LENGTH_SHORT).show();
        }
    }

    public void setLangSelection() {
        Utils.langConversion(NewsDetails.this, mTextViewTitle, new String[]{WSContant.TAG_LANG_DETAILS}, getString(R.string.tab_news), UserInfo.lang_pref);
       //Utils.langConversion(NewsDetails.this, mTextViewTitle, new String[]{WSContant.TAG_LANG_NEWS, WSContant.TAG_LANG_DETAILS}, getString(R.string.tab_news), UserInfo.lang_pref);
        //Utils.langConversion(NewsDetails.this, ((TextView) findViewById(R.id.textview_inc_bottom_like)), new String[]{WSContant.TAG_LANG_LIKE}, getString(R.string.like), UserInfo.lang_pref);
        Utils.langConversion(NewsDetails.this, findViewById(R.id.textview_inc_bottom_comment), new String[]{WSContant.TAG_LANG_COMMENTS}, getString(R.string.comment), UserInfo.lang_pref);

        Utils.langConversion(NewsDetails.this, mTextViewCommentTab, new String[]{WSContant.TAG_LANG_COMMENTS}, getString(R.string.comment), UserInfo.lang_pref);
        Utils.langConversion(NewsDetails.this, mTextViewLikeTab, new String[]{WSContant.TAG_LANG_LIKE}, getString(R.string.like), UserInfo.lang_pref);
        Utils.langConversion(NewsDetails.this, mEditTextComment, new String[]{WSContant.TAG_LANG_TYPE, WSContant.TAG_LANG_COMMENTS}, getString(R.string.type_comment), UserInfo.lang_pref);
        Utils.langConversion(NewsDetails.this, mTextViewSend, new String[]{WSContant.TAG_LANG_SEND}, getString(R.string.send), UserInfo.lang_pref);

    }

    public TableNewsMasterDataModel getNewsMasterDataModel() {
        TableNewsMasterDataModel pNewsList = null;//new TableNewsMasterDataModel();
        try {
            TableNewsMaster table = new TableNewsMaster();
            table.openDB(this);
            pNewsList = table.getNews(UserInfo.studentId, mReferenceId);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog(TAG, e.getMessage());
        } finally {
            return pNewsList;
        }
    }

    private void saveLikeIntoDB(int value) {
        try {
            TableNewsMaster table = new TableNewsMaster();
            table.openDB(this);
            table.updateLikedByMe(value, UserInfo.studentId, mReferenceId);
            table.closeDB();
            mNewsMasterDataModel.setLikedByMe(value);
        } catch (Exception e) {
            AppLog.errLog(TAG, e.getMessage());
        }
    }
}
