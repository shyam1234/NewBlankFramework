package com.stpl.edurp.network;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.stpl.edurp.R;
import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 26-11-2016.
 */

public class WSRequest {
    public final static int POST = Request.Method.POST;
    public final static int GET = Request.Method.GET;
    public final static int PUT = Request.Method.PUT;
    private static WSRequest mInstance;
    private int count;

    private WSRequest() {

    }

    public static WSRequest getInstance() {
        if (mInstance == null)
            mInstance = new WSRequest();
        return mInstance;
    }

    public synchronized void request(int method, String url, String TAG, final IWSRequest pWSRequest) {
        StringRequest request = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pWSRequest.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pWSRequest.onErrorResponse(error);
            }
        });
        if (TAG == null) {
            MyApplication.getInstance().addToRequestQueue(request, "");
        } else {
            MyApplication.getInstance().addToRequestQueue(request, TAG);
        }

        //code for timeout
        //do
    }

    public synchronized void requestWithParam(int method, String url, final Map<String, String> pHeader, final Map<String, String> pParam, final String TAG, final IWSRequest pWSRequest) {
        AppLog.networkLog(TAG, "---------------------------------------" + TAG + "-----------------------------------------");
        AppLog.networkLog(TAG, "URL: " + url);
        AppLog.networkLog(TAG, "Header: " + pHeader);
        AppLog.networkLog(TAG, "Request: " + pParam);
        switch (method) {
            case GET:
                AppLog.networkLog(TAG, "method: GET " + method);
                break;
            case POST:
                AppLog.networkLog(TAG, "method: POST " + method);
                break;
            default:
                AppLog.networkLog(TAG, "method: " + method);
                break;
        }

        final StringRequest request = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AppLog.networkLog(TAG, "Response: " + response.toString());
                if (response != null && response.trim().length() > 0) {
                    AppLog.networkLog(TAG, "------------------- --------------END--" + TAG + "-----------------------------------------");
                    pWSRequest.onResponse(response);
                } else {
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), " WS Request: Response is coming blank: " + response, Toast.LENGTH_SHORT).show();
                    AppLog.networkLog("WSRequest class : ", "Response is coming blank for : " + TAG + response.toString());
                    AppLog.networkLog(TAG, "------------------- --------------END--" + TAG + "-----------------------------------------");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pWSRequest.onErrorResponse(error);
                AppLog.networkLog(TAG, "Error: " + error.getMessage());
                AppLog.networkLog(TAG, "------------------- --------------END--" + TAG + "-----------------------------------------");
                if (error.networkResponse != null) {
                    AppLog.networkLog(TAG, "Error: statusCode: " + error.networkResponse.statusCode);
                    switch (TAG) {
                       /* case WSContant.TAG_GETMOBILEHOME:
                            if (error.networkResponse.statusCode == WSContant.TAG_UNAUTHORIZED_CODE) {
                                Toast.makeText(MyApplication.getInstance().getApplicationContext(), MyApplication.getInstance().getApplicationContext().getResources().getString(R.string.msg_session_exp), Toast.LENGTH_SHORT).show();
                                UserInfo.logout();
                            }
                            break;*/
                        default:
                            if (error.networkResponse.statusCode == WSContant.TAG_UNAUTHORIZED_CODE) {
                                Toast.makeText(MyApplication.getInstance().getApplicationContext(), MyApplication.getInstance().getApplicationContext().getResources().getString(R.string.msg_session_exp), Toast.LENGTH_SHORT).show();
                                UserInfo.logout();
                            }
                    }
                }
                //for offline handling----------------------------------


                //-------------------------------------------------------
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), MyApplication.getInstance().getApplicationContext().getString(R.string.msg_network_prob) + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = (pParam != null ? pParam : new HashMap<String, String>());
                // AppLog.networkLog(TAG, "getParams: " + params.toString());
                return params;

            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = (pHeader != null ? pHeader : new HashMap<String, String>());
                //AppLog.networkLog(TAG, "getHeaders: " + params.toString());
                return params;
            }


            @Override
            public boolean isCanceled() {
                return super.isCanceled();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse resp) {
                if (resp.statusCode == WSContant.TAG_SUCCESS_CODE) {
                    Map<String, String> mapHeader = resp.headers;
                    if ((mapHeader.get(WSContant.TAG_TOKEN)).trim().length() > 0) {
                        UserInfo.authToken = mapHeader.get(WSContant.TAG_TOKEN);
                    }
                    UserInfo.tokenExp = mapHeader.get(WSContant.TAG_TOKEN_EXP);
                    UserInfo.tokenIssue = mapHeader.get(WSContant.TAG_TOKEN_ISSUE);
                    //parseData(resp.data);

                    AppLog.networkLog(TAG, "--network -- response- - " + TAG + " --");
                    AppLog.networkLog("network statusCode", "" + resp.statusCode);
                    AppLog.networkLog("network networkTimeMs", "" + resp.networkTimeMs);
                    AppLog.networkLog("network headers", "" + mapHeader);
                    AppLog.networkLog("network notModified", "" + resp.notModified);
                    AppLog.networkLog(TAG, "------");
                }

                return super.parseNetworkResponse(resp);
            }
        };
        if (TAG == null) {
            MyApplication.getInstance().addToRequestQueue(request, "");
        } else {
            MyApplication.getInstance().addToRequestQueue(request, TAG);
        }

    }

//    private String parseData(byte[] response) {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        try {
//            if (response!=null) {
//
//                //String content =request.responseHeaders.get("Content-Disposition").toString();
//                StringTokenizer st = new StringTokenizer("");
//                //String[] arrTag = st.toArray();
//
//                //String filename = arrTag[1];
//                //filename = filename.replace(":", ".");
//               // Log.d("DEBUG::FILE NAME", filename);
//
//                try{
//                    long lenghtOfFile = response.length;
//
//                    InputStream input = new ByteArrayInputStream(response);
//
//                    File path = Environment.getExternalStorageDirectory();
//                    File file = new File(path, "ddd");
//                    map.put("resume_path", file.toString());
//                    BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
//                    byte data[] = new byte[1024];
//
//                    long total = 0;
//                    count = 0;
//                    while ((count = input.read(data)) != -1) {
//                        total += count;
//                        output.write(data, 0, count);
//                    }
//
//                    output.flush();
//
//                    output.close();
//                    input.close();
//                }catch(IOException e){
//                    e.printStackTrace();
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
