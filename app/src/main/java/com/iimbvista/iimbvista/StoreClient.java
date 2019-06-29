package com.iimbvista.iimbvista;

import android.app.Activity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class StoreClient extends WebViewClient {
    private Activity activity = null;

    public StoreClient(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url){

        if(url.contains("townscript.com/e/")){
            return false;
        }
        return true;
    }
}
