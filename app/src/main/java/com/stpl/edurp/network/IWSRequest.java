package com.stpl.edurp.network;

import com.android.volley.VolleyError;

/**
 * Created by Admin on 26-11-2016.
 */

public interface IWSRequest {
    void onResponse(String response);
    void onErrorResponse(VolleyError response);
}
