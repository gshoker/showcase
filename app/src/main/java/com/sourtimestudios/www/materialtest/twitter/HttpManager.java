package com.sourtimestudios.www.materialtest.twitter;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by user on 02/02/15.
 */
public class HttpManager {


    private final String XML_Micropost = "span class=\"content\"";

    public static String getData(RequestPackage p) {

        BufferedReader reader = null;
        String uri = p.getUri();
        if (p.getMethod().equals("GET")) {
            uri += "?" + p.getEncodedParams();
        }

        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            OkHttpClient client = OKHelper.getClient(c);
//            HttpURLConnection con = (Http);
//            RetrofitHttpClient retrofitHttpClient = new RetrofitHttpClient();
//            retrofitHttpClient.

//            con.setReadTimeout(10000);
//            con.setConnectTimeout(15000);
            con.setRequestMethod(p.getMethod());

            if(p.getMethod().equals("POST")){
                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(p.getEncodedParams());
                writer.flush();
            }

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK){
                Log.i("HttpManager", con.getHeaderField(""));
            }

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }

//    public ArrayList<Micropost> getMicroposts(RequestPackage p){
//        ArrayList<Micropost> microposts = new ArrayList<Micropost>();
//        try{
//
//            String xmlString = getData(p);
////            Log.i(TAG, "Received XML: " + xmlString);
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            XmlPullParser parser = factory.newPullParser();
//            parser.setInput(new StringReader(xmlString));
//
//            parseItems(microposts, parser);
//        }catch (IOException e){
////            Log.e(TAG, "Failed to fetch items: " + e);
//        }catch (XmlPullParserException e){
////            Log.e(TAG, "XML Parser exception: " + e);
//        }
//        return microposts;
//    }

//    void parseItems(ArrayList<Micropost> microposts,XmlPullParser parser) throws XmlPullParserException, IOException{
//        int eventType = parser.next();
//
//        while(eventType != XmlPullParser.END_DOCUMENT){
//            if(eventType == XmlPullParser.START_TAG && XML_Micropost.equals(parser.getName())){
//                String post = parser.getAttributeValue("0",null);
//
//
//                Micropost micropost = new Micropost();
//                micropost.setText(post);
//            }
//            eventType = parser.next();
//        }
//    }
}
