package com.example.bookapi;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Queryutils {
    public static final String LOG_TAG = Queryutils.class.getSimpleName();
    public Queryutils(String s){

    }
    public static ArrayList<bookdata> fetchbookdata(String s)
    {     String urls="https://www.googleapis.com/books/v1/volumes?q=";
        urls+=s;
        urls+="&maxResults=20";
        URL url;
        url=createurl(urls);
        String SAMPLE_JSON_RESPONSE=null;
        try {
            SAMPLE_JSON_RESPONSE= makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        if (TextUtils.isEmpty(SAMPLE_JSON_RESPONSE)) {
            return null;
        }
        ArrayList<bookdata> book = new ArrayList<>();
        try {
            JSONObject root =new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray arr =root.getJSONArray("items");
            for(int i=0;i<arr.length();i++)
            {
                JSONObject obj=arr.getJSONObject(i);
                JSONObject ob1=obj.getJSONObject("volumeInfo");
                String s1=ob1.getString("title");
                String s2=ob1.getString("previewLink");
                book.add(new bookdata(s1,s2));
            }
        }
        catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return book;
    }
    private static URL createurl(String s)
    {
        URL url=null;
        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url)throws IOException
    {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}

