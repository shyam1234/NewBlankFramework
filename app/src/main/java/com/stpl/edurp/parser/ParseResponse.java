package com.stpl.edurp.parser;

import com.google.gson.Gson;
import com.stpl.edurp.interfaces.IModel;
import com.stpl.edurp.models.GetMobileAttendanceDetailDataModel;
import com.stpl.edurp.models.GetMobileDetailsDataModel;
import com.stpl.edurp.models.GetMobileHomeDataModel;
import com.stpl.edurp.models.GetMobileMenuDataModel;
import com.stpl.edurp.models.LanguageArrayDataModel;
import com.stpl.edurp.models.LoginDataModel;
import com.stpl.edurp.models.ModelFactory;
import com.stpl.edurp.models.NewsDetailsCommentLikeDataModel;
import com.stpl.edurp.models.PayslipDataModel;
import com.stpl.edurp.models.TableAbsenseDetailsDataModel;
import com.stpl.edurp.models.TableResultDetailsDataModel;
import com.stpl.edurp.models.TableTimeTableDetailsDataModel;
import com.stpl.edurp.models.TaxReportsDataModel;

/**
 * Created by Admin on 30-12-2016.
 */

public class ParseResponse {

    private final String TAG = "ParseResponse";
    private final String mKey;
    private String mRespose;
    private Gson mGson;
    private IModel mModel;
   // private Class<? extends IModel> mModelClass;

    public ParseResponse(String response, Class<? extends IModel> pModelClass, String key) {
        mRespose = response;
        mGson = new Gson();
        mKey = key;
       // mModelClass = pModelClass;
        doParse();
    }


    /**
     * add model class here to parse response through gson
     */
    private void doParse() {
        switch (mKey) {
            case ModelFactory.MODEL_LOGIN:
                mModel = mGson.fromJson(mRespose, LoginDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_LANG:
                mModel = mGson.fromJson(mRespose, LanguageArrayDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_GETMOBILEHOME:
                mModel = mGson.fromJson(mRespose, GetMobileHomeDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_GETMOBILEMENU:
                mModel = mGson.fromJson(mRespose, GetMobileMenuDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_GETMOBILEDETAILS:
                mModel = mGson.fromJson(mRespose, GetMobileDetailsDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_NEWS_DETAILS_COMMENTS_LIKE:
                mModel = mGson.fromJson(mRespose, NewsDetailsCommentLikeDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_TIMETABLEDETAILS:
                mModel = mGson.fromJson(mRespose, TableTimeTableDetailsDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_RESULTDETAILS:
                mModel = mGson.fromJson(mRespose, TableResultDetailsDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_GETMOBILEATTDANCEDETAIL:
                mModel = mGson.fromJson(mRespose, GetMobileAttendanceDetailDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_GETMOBILEATTDANCEABSENT:
                mModel = mGson.fromJson(mRespose, TableAbsenseDetailsDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_PAYSLIPFACULTYDM:
                mModel = mGson.fromJson(mRespose, PayslipDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_GETTAXFINANCIALYEAR:
                mModel = mGson.fromJson(mRespose, TaxReportsDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            default:
                break;

        }
    }

    public IModel getModel() {
        return mModel;
    }
}
