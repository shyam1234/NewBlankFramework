package com.stpl.edurp.exceptions;

import com.stpl.edurp.utils.AppLog;

/**
 * Created by Admin on 30-12-2016.
 */

public class ModelException extends Exception {

    private final String mExceptionMesg;

    public ModelException(String exceptionMesg) {
        mExceptionMesg = exceptionMesg;
    }

    @Override
    public String getMessage() {
        AppLog.errLog("ModelException", mExceptionMesg);
        return mExceptionMesg;
    }
}
