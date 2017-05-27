package com.stpl.edurp.constant;

/**
 * Created by Admin on 26-11-2016.
 */

public interface WSContant {
    String URL_BASE = "http://stpl-edurpapi.azurewebsites.net/api";
    //------------------------------------------------------------------------------------
    String URL_LOGIN = URL_BASE + "/authenticate";
    String URL_FORGET = URL_BASE + "/Account/ForgetPassword?";
    String URL_CHANGEPASSWORD = URL_BASE + "/Account/ChangePassword?";

    String URL_GETMOBILEHOME = URL_BASE + "/mobile/GetMobileHome";
    String URL_GETMOBILEMENU = URL_BASE + "/mobile/GetMobileMenu";
    String URL_GETMOBILEDETAILS = URL_BASE + "/mobile/GetMobileDetails";

    String URL_SAVELIKENCOMMENT = URL_BASE + "/mobile/SaveLikeNComment";
    String URL_GETMOBILECOMMENTSNLIKES = URL_BASE + "/mobile/GetMobileCommentsnLikes";
    String URL_PRINT_OVERALL_RESULT = URL_BASE + "/StudentPortal/OverallResult/PrintOverallResult?StudentId=";
    String URL_PRINT_RECEIPT = URL_BASE + "/Fee/FeeCollection/PrintReceipt?sfAssociationId=";

    String URL_TIMETABLE = URL_BASE + "/mobile/GetMobileTimeTable";
    String URL_GETMOBILEATTENDANCEDETAIL = URL_BASE + "/mobile/GetMobileAttendanceDetail";
    String URL_GETMOBILEATTENDANCEABSENCEDETAIL = URL_BASE + "/mobile/GetMobileAttendanceAbsenceDetail";
    //String URL_REGISTRATION = URL_BASE + "/Account/Register";
    //-------------------------------------------------------------------------------------
    String DOWNLOAD_FOLDER = "Edurp_results";
    String TAG_LOGIN = "login";
    String TAG_LANG = "lang";
    String TAG_GETMOBILEHOME = "mobilehome";
    String TAG_AUTHORIZATION = "Authorization";
    String TAG_EMIALADDRESS = "EmailAddress";
    String TAG_PASSWORD = "Password";
    String TAG_CHANGE_PASSWORD = "change_password";
    String TAAG_DATA = "Data";
    String TAG_SUCCESS = "Success";
    String TAG_UNIVERSITYID = "UniversityId";
    String TAG_LANGUAGE_VERSION_DATE = "LanguageVersionDate";
    String TAG_TOKEN = "Token";
    String TAG_TOKEN_EXP = "TokenExpiresOn";
    String TAG_TOKEN_ISSUE = "TokenIssuedOn";
    String TAG_CONTENT_TYPE = "Content-Type";
    String TAG_DATELASTRETRIEVED = "DateLastRetrieved";
    String TAG_NEW = "NEW";
    String TAG_SHAREDPREF_NAME = "edurp_sharepref";
    String TAG_SHAREDPREF_GET_LAST_TIME = "get_last_time";
    String TAG_STUDENTID = "StudentId";
    String TAG_ISMOBILE = "isMobile";
    String TAG_LANG_RETRIVE_TIME = "lan_retriev_time";
    String TAG_LAST_LOGIN_TIME = "last_login_time";
    String TAG_AUTHTOKEN = "auth_token";
    String TAG_SAVED_USERID = "saved_userid";
    String TAG_USERTYPE_PARENT = "P";
    String TAG_USERTYPE_STUDENT = "S";
    String TAG_USER_TYPE = "user_type";
    String TAG_DEFAULT_CHILD = "default_child";
    String TAG_MENUCODE = "MenuCode";
    String TAG_USERID = "UserId";
    String TAG_PARENTID = "ParentId";
    String TAG_USERTYPE = "UserType";
    String TAG_LASTRETRIEVED = "LastRetrieved";
    int TAG_UNAUTHORIZED_CODE = 401;
    int TAG_SUCCESS_CODE = 200;
    String TAG_COMMENT = "Comment";
    String TAG_ISLIKE = "IsLike";
    String TAG_ISDELETE = "IsDelete";
    String TAG_REFERENCEID = "ReferenceId";
    String TAG_MESSAGEBODYHTML = "MessageBodyHTML";
    String TAG_PRINT_OVERALL_RESULT = "print_overall_result";
    String TAG_PRINT_RECEIPT = "print_receipt";
    String TAG_NEWS_GET_LAST_TIME = "get_news_last_time";
    String TAG_NEWS = "news";
    String TAG_OK = "OK";
    String TAG_GETMOBILEDETAILS = "getmobiledetails_ws";
    String TAG_NEWSBODY = "NewsBody";
    String TAG_IMAGE = "I";
    String TAG_VIDEO = "V";

    String TAG_SAVELIKECOMMENT = "SaveLikeNComment";
    String TAG_MESSAGERESULT = "MessageResult";
    String TAG_MOBILENOTICEBOARD = "GetMobileNoticeBoard";
    String TAG_REFERENCEDATE = "ReferenceDate";
    String TAG_VALUE_NOT_PAID = "Not Paid";
    String TAG_ENG = "eng";
    String TAG_BHASHA = "bhasha";

    String TAG_LANG_LOGIN = "lblLogin";
    String TAG_LANG_USERNAME = "lblUserName";
    String TAG_LANG_PASSWORD = "lblPassword";
    String TAG_LANG_FORGOTPASSWORD = "lblForgetPassword";
    String TAG_LANG_SUCCESS = "lblSuccess";
    String TAG_LANG_PROCESSDING = "lblProceseding";
    String TAG_LANG_FORGOTPASSWORDTITLE = "lblForgotPasswordTittle";
    String TAG_LANG_WELCOME = "bnrWelcome";
    String TAG_LANG_EMAIL="lblEmail";
    String TAG_LANG_SUBMIT="lblSubmit";
    String TAG_LANG_NEWS = "NEW";
    String TAG_LANG_DETAILS = "lblDetails";
    String TAG_LANG_COMMENTS = "lblComments";
    String TAG_LANG_LIKE="lblLike"; //pending
    String TAG_LANG_HOME = "lblHome";//pending
    String TAG_LANG_NOTICEBOARD="lblNoticeboard"; //pending
    String TAG_LANG_SEND="lblSend";
    String TAG_LANG_TYPE ="lblType";
    String TAG_LANG_MYPREFERENCE = "lblMyPreference";
    String TAG_LANG_SHARE_THIS_APP = "lblShareThisApp";//pending
    String TAG_LANG_OPTIONS = "lblOptions";//pending
    String TAG_LANG_SETTING="lblSettings";
    String TAG_LANG_LOGOUT="lblLogout";
    String TAG_TIMETABLEDATE="TimeTableDate";
    String TAG_WS_TIMETABLE = "ws_timetable";
    String TAG_NR = "NR";
    String TAG_MOBILEATTENDANCEDETAIL = "ws_GetMobileAttendanceDetail";
    String TAG_MOBILEATTENDANCE_ABSENT= "ws_GetMobileAttendanceAbsent";
    String TAG_COURSE = "Course";
    String TAG_SEMESTER= "Semester";
    String TAG_SUBJECTNAME= "SubjectName";
    String TAG_SUBJECTID= "SubjectId";

}


