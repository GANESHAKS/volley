package com.pro.volley.feed;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pro.volley.R;

/**
 * A simple {@link Fragment} subclass.
 * Use factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment {
    FloatingActionButton fab_new_feed;
    WebView webView;
    SwipeRefreshLayout swipeRefreshLayout;
    Context context;

    public FeedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.layout_feed, container, false);
        context = view.getContext();
        fab_new_feed = view.findViewById(R.id.fab_add_feed);
        swipeRefreshLayout = view.findViewById(R.id.sr_feed);
        fab_new_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab_new_feed_clicked();
            }
        });
        webView = view.findViewById(R.id.wv_feed_home);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setDomStorageEnabled(true);
        webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webView.setBackgroundColor(Configuration.UI_MODE_NIGHT_MASK);
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //This is the filter
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                        //Dlog.d(“canGoBack”);
                    } else {
                        //Dlog.d(“canNotGoBack”);
                        getActivity().onBackPressed();

                    }
                    return true;
                }
                return false;
            }
        });
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            //Code to enable force dark and select force dark strategy
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
            }
        }
        webView.loadUrl("http://facebook.com");
        swiped();

        return view;
    }

    private void swiped() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl("http://facebook.com");
            }
        });
    }

    private void fab_new_feed_clicked() {
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            //  pbar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // TODO Auto-generated method stub
            if (swipeRefreshLayout.isRefreshing() == false) {

                swipeRefreshLayout.setRefreshing(true);
            }

            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            // TODO Auto-generated method stub

            super.onPageFinished(view, url);
            swipeRefreshLayout.setRefreshing(false);
            // pbar.setVisibility(View.GONE);
        }
    }


}
