package com.sourtimestudios.www.materialtest.flickr;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 23/11/14.
 */
public class ThumbnailDownloader<Token> extends HandlerThread {
    private static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;

    private LruCache<String, Bitmap> imageCache;

    Handler mHandler;
    Map<Token, String> requestMap = Collections.synchronizedMap(new HashMap<Token, String>());

    Handler mResponseHandler;
    Listener<Token> mListener;

    public interface Listener<Token>{
        void onThumbnailDownloaded(Token token, Bitmap thumbnail);
    }

    public void setListener(Listener<Token> listener){
        mListener = listener;
    }

    public ThumbnailDownloader(Handler responseHandler){
        super(TAG);
        mResponseHandler = responseHandler;

        final int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<String, Bitmap>(cacheSize);
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == MESSAGE_DOWNLOAD){
                    @SuppressWarnings("unchecked")
                    Token token = (Token)msg.obj;
                    Log.i(TAG, "Got a request for url: " + requestMap.get(token));
                    handleRequest(token);
                }
            }
        };
    }

    public void queueThumbnail(Token token, String url){
        requestMap.put(token, url);

        mHandler.obtainMessage(MESSAGE_DOWNLOAD,token).sendToTarget();
    }

    private void handleRequest(final Token token){
        try{
            final String url = requestMap.get(token);
            if (url == null) return;

            final Bitmap bitmap = imageCache.get(url);
            if(bitmap != null) {
                mResponseHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (requestMap.get(token) != url)
                            return;
                        ;

                        requestMap.remove(token);
                        mListener.onThumbnailDownloaded(token, bitmap);
                    }
                });
            }else {
                byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
                final Bitmap bitmap2 = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
                imageCache.put(url, bitmap2);
                mResponseHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (requestMap.get(token) != url)
                            return;
                        ;

                        requestMap.remove(token);
                        mListener.onThumbnailDownloaded(token, bitmap2);
                    }
                });
            }

        }catch (IOException e){

        }
    }

    public void clearQueue(){
        mHandler.removeMessages(MESSAGE_DOWNLOAD);
        requestMap.clear();
    }
}
