package foodapp.kk.com.howgeekyareyou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Quiz extends AppCompatActivity {
    ArrayList<Question> questionArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionArrayList=getIntent().getParcelableArrayListExtra("QuestionList");
        /*Code below this is debugging purpose only you can see how questions and options and
        *all things are being retrieved*/
        int i=0;
        for(Question q : questionArrayList)
        {
            for(Object S:q.getHashMap().keySet() )
            {
                Log.d("Quiz Questions"+i, S.toString());
                ArrayList<String> OptionsNScores= q.getOptionsList(S);
                for(int j=0;j<OptionsNScores.size();j++)
                    Log.d("Quiz options for Q:" + i, OptionsNScores.get(j).toString());
            }



            if(q.getImageUrl()!=null)
                Log.d("Quiz Questions"+i+"Image Url", q.getImageUrl());
            i++;

        }//Debug code ends here
    }
}
