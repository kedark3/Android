package foodapp.kk.com.inclass5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Name:Kedar Vijay Kulkarni
 * Filename:getImage.java
 * Assignment: In Class 05
 */
public class getImage extends AsyncTask<RequestParams,Void,Bitmap> {
    MainActivity activity;

    @Override
    protected void onPreExecute() {
        activity.pDiaglog2.show();
        activity.pDiaglog2.setCancelable(false);
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {

        activity.pDiaglog2.dismiss();
        MainActivity.imageViewResult.setImageBitmap(bitmap);
        //activity.setImage(bitmap);
    }

    @Override
    protected Bitmap doInBackground(RequestParams... params) {
        try {
            Log.d("DemoURL",params[0].getEncodedUrl().toString());
            HttpURLConnection con= params[0].setupConnection();
            Bitmap image= BitmapFactory.decodeStream(con.getInputStream());
            if(image==null)Log.d("Demo","ImageNull");
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}