package foodapp.kk.com.inclass5;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

/*
        * Name:Kedar Vijay Kulkarni
        * Filename:getImageIdData.java
        * Assignment: In Class 05
        */
public class getImageIdData extends AsyncTask<RequestParams,Void,String> {
    MainActivity activity;
    @Override
    protected void onPreExecute() {
        activity.pDialog.show();
        activity.pDialog.setCancelable(false);
    }




    @Override
    protected String doInBackground(RequestParams... params) {
        BufferedReader reader=null;
        String[] keyValues, keyVal;
        try{
            //Log.d("DemoURL",params[0].getEncodedUrl().toString());
            HttpURLConnection con= params[0].setupConnection();
            reader= new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb=new StringBuilder();
            String line="";
            while ((line=reader.readLine()) != null){
                sb.append(line + "\n");
                keyValues= line.split(";");

                for(String s : keyValues){
                    keyVal=s.split(",");

                    //Log.d("Demo",keyVal[0].toString()+"\n"+keyVal[1].toString());
                    if(MainActivity.imageIds.get(keyVal[0])!=null){
                        ArrayList<String> valueList=MainActivity.imageIds.get(keyVal[0]);
                        valueList.add(keyVal[1]);
                        MainActivity.imageIds.put(keyVal[0],valueList);
                    }
                    else{
                        ArrayList<String> valueList=new ArrayList<>();
                        valueList.add(keyVal[1]);
                        MainActivity.imageIds.put(keyVal[0],valueList);
                    }


                }



            return sb.toString();
        }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {

        /*textView.setText(imageIds.get(0));
        RequestParams params= new RequestParams("GET", "http://dev.theappsdr.com/lectures/inclass_photos/index.php");
        params.addParams("pid",imageIds.get(0));
        new getImage().execute(params);*/
        activity.pDialog.dismiss();
        //Log.d("Demo",MainActivity.imageIds.toString());
    }
}
