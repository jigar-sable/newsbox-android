package com.lifecodes.newsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lifecodes.newsapp.databinding.ActivityWebviewBinding;

public class WebviewActivity extends AppCompatActivity {

    ActivityWebviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String appname = "";

        int nightModeFlags = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                appname = "<font color=#ffffff>News</font><font color=#5800FC>Box</font>";
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                appname = "<font color=#424242>News</font><font color=#5800FC>Box</font>";
                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;
        }
        binding.appNameTxt.setText(Html.fromHtml(appname));

        String url = getIntent().getStringExtra("url");
        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.loadUrl(url);

        binding.progressBar.setMax(100);

        binding.webView.setWebViewClient(new Client());
        WebSettings ws = binding.webView.getSettings();
        ws.setJavaScriptEnabled(true);
        binding.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        binding.webView.clearCache(true);
        binding.webView.clearHistory();

        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                binding.progressBar.setProgress(newProgress);
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private class Client extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            binding.progressBar.setVisibility(View.GONE);
        }

        public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
            try {
                webView.stopLoading();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (webView.canGoBack()) {
                webView.goBack();
            }
            webView.loadUrl("about:blank");
            AlertDialog alertDialog = new AlertDialog.Builder(WebviewActivity.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Check your internet connection and try again!");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    startActivity(getIntent());
                }
            });
            alertDialog.show();
            super.onReceivedError(webView, errorCode, description, failingUrl);
        }
    }
}