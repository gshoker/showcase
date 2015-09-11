package com.sourtimestudios.www.materialtest.flickr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sourtimestudios.www.materialtest.R;

//import com.sourtime.exhibits.R;
//import com.sourtime.exhibits.VisibleFragment;

/**
 * Created by user on 27/11/14.
 */
public class PhotoPageFragment extends Fragment {
    private static final String TAG = "PhotoPageFragment";
    private String mUri;
    private WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mUri = getActivity().getIntent().getDataString();
        Log.i(TAG, "Uri received: " + mUri);
    }

    @SuppressWarnings("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_page,container,false);

        final ProgressBar progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        progressBar.setMax(100);
        final TextView titleTextView = (TextView)v.findViewById(R.id.titleTextView);

        mWebView = (WebView)v.findViewById(R.id.webView);

        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                return false;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView webview, int progress){
                if(progress == 100){
                    progressBar.setVisibility(View.INVISIBLE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }
            }

            public void onReceivedTitle(WebView webView, String title){
                titleTextView.setText(title);
            }

        });

        mWebView.loadUrl(mUri);

        return v;
    }
}
