package com.paparr.twod;

import android.app.Activity;
import android.os.Bundle;
import android.os.Build;
import android.view.WindowInsets;
import android.view.ViewGroup;
import android.graphics.Insets;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.view.View;
import android.graphics.Color;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.rgb(16, 21, 34));
        getWindow().setNavigationBarColor(Color.rgb(16, 21, 34));

        webView = new WebView(this);
        webView.setBackgroundColor(Color.rgb(16, 21, 34));

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        // NOTE: WebView ignores padding on itself when rendering web content,
        // so the insets padding must go on a container around the WebView —
        // otherwise page content slides under the status/navigation bars.
        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(Color.rgb(16, 21, 34));
        root.addView(webView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        root.setOnApplyWindowInsetsListener((v, insets) -> {
            int top = 0;
            int bottom = 0;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Insets bars = insets.getInsets(WindowInsets.Type.systemBars());
                top = bars.top;
                bottom = bars.bottom;
            } else {
                top = insets.getSystemWindowInsetTop();
                bottom = insets.getSystemWindowInsetBottom();
            }

            v.setPadding(0, top, 0, bottom);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                return WindowInsets.CONSUMED;
            }
            return insets;
        });

        setContentView(root);
        root.requestApplyInsets();

        if (savedInstanceState == null) {
            webView.loadUrl("https://myanmargg205-png.github.io/paparr-2d/");
        } else {
            webView.restoreState(savedInstanceState);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (webView != null) {
            webView.saveState(outState);
        }
        super.onSaveInstanceState(outState);
    }
}
