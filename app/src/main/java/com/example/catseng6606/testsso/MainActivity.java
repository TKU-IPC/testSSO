package com.example.catseng6606.testsso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String shareURL;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new JsCallBack(), "ExtObj");
        webView.setWebViewClient(new WebViewClientImpl());

        webView.loadUrl("https://sso.tku.edu.tw/ilife/Data/AndroidSsoLogin.html");

    }

    private final class WebViewClientImpl extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:window.ExtObj.responseResult(getSsoLoginToken())");
        }
    }

    private class JsCallBack {
        @JavascriptInterface
        public void responseResult(final String result) {
            shareURL = result;
            Toast.makeText(MainActivity.this, shareURL, Toast.LENGTH_SHORT).show();
            // 取得登入資料後，儲存登入資訊。
            // 跳出。
        }
    }
}
