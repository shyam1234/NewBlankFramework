package com.stpl.edurp.models;

import com.stpl.edurp.R;
import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.exceptions.ModelException;
import com.stpl.edurp.interfaces.IModel;

import java.util.HashMap;

/**
 * Created by Admin on 30-12-2016.
 */

public class ModelFactory {
    public static final String MODEL_NEWS_DETAILS_COMMENTS_LIKE = "commentlike";
    public static final String MODEL_NOTICEBOARD = "noticeboard";
    public final static String MODEL_LOGIN = "LoginDataModel";
    public static final String MODEL_RESULTDETAILS = "ResultDetails";
    public static final String MODEL_GETMOBILEMENU = "NewsDataModel";
    public static final String MODEL_GETMOBILEDETAILS = "NewsDataModelDetails";
    public final static String MODEL_LANG = "LanguageArrayDataModel";
    public final static String MODEL_GETMOBILEHOME = "GetMobileHome";
    public final static String MODEL_TIMETABLEDETAILS = "TableTimeTableDetails";
    public static final String MODEL_GETMOBILEATTDANCEDETAIL = "GetMobileAttendanceDetail";
    public static final String MODEL_GETMOBILEATTDANCEABSENT = "GetMobileAttendancAbsent";
    private volatile static ModelFactory mInstance;
    private HashMap<String, IModel> mHashMapMode;

    private ModelFactory() {
        mHashMapMode = new HashMap<>();
    }

    public static ModelFactory getInstance() {
        synchronized (ModelFactory.class) {
            if (mInstance == null) {
                mInstance = new ModelFactory();
            }
        }
        return mInstance;
    }

    public IModel getModel(String key) throws ModelException {
        IModel model = null;
        if ((model = mHashMapMode.get(key)) != null) {
            return model;
        }
        throw new ModelException(MyApplication.getInstance().getApplicationContext().getResources().getString(R.string.msg_error_model));
    }

    public void register(String key, IModel model) {
        mHashMapMode.put(key, model);
    }

    public void unRegister(String key) {
        mHashMapMode.remove(key);
    }

    public void unRegisterAll() {
        mHashMapMode.clear();
    }


}
