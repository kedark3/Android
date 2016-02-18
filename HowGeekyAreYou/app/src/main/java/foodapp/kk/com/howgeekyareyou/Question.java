package foodapp.kk.com.howgeekyareyou;

import android.os.Parcel;
import android.os.Parcelable;
import android.webkit.HttpAuthHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


/**
 * Created by Sairam on 2/16/2016.
 */
public class Question implements Parcelable {


    private String imageUrl;

    private HashMap<String,ArrayList<String>> hashMap= new  HashMap<String,ArrayList<String>>();
    public static Parcelable.Creator<Question> getCREATOR() {
        return CREATOR;
    }


    public Question(String questionData[])

    {
        ArrayList<String> answerList = new ArrayList<String>();

        if((questionData.length%2) ==0){
            for(int i=2;i<questionData.length;i++) {
                answerList.add(questionData[i]);
            }
            hashMap.put(questionData[1], answerList);
        } else if((questionData.length%2)!=0){

            for(int i=2;i<questionData.length-1;i++) {
                answerList.add(questionData[i]);

            }

            hashMap.put(questionData[1], answerList);
            imageUrl=questionData[questionData.length-1];
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public HashMap getHashMap(){
        return hashMap;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public ArrayList<String> getOptionsList(Object o){
        return hashMap.get(o);
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(hashMap);
        dest.writeString(imageUrl);
    }

    public static final Parcelable.Creator<Question> CREATOR
            = new Parcelable.Creator<Question>() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    private Question(Parcel in) {
        in.readMap(hashMap,String.class.getClassLoader());
        this.imageUrl=in.readString();

    }
    @Override
    public String toString() {

        return  hashMap.toString()+imageUrl;
    }


}

/*
package foodapp.kk.com.howgeekyareyou;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Sairam on 2/16/2016.

public class Question implements Parcelable {

    //private String question,optionA,optionB,optionC,optionD,optionAScore,optionBScore,optionCScore,optionDScore,
    String imageUrl;

    public HashMap<String,ArrayList<String>> hashMap= new  HashMap<String,ArrayList<String>>();
    public static Parcelable.Creator<Question> getCREATOR() {
        return CREATOR;
    }

    public Question(String questionData[])//, String optionA, String optionAScore, String optionB, String optionBScore,
    //String optionC, String optionCScore, String optionD,String optionDScore, String imageUrl) {
    {
        ArrayList<String> answerList = new ArrayList<String>();

        if((questionData.length%2) ==0){
            for(int i=2;i<questionData.length;i++) {
                answerList.add(questionData[i]);
            }
            hashMap.put(questionData[1], answerList);
        } else if((questionData.length%2)!=0){

            for(int i=2;i<questionData.length-1;i++) {
                answerList.add(questionData[i]);

            }

            hashMap.put(questionData[1], answerList);
            imageUrl=questionData[questionData.length-1];
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hashMap.toString());
        dest.writeString(imageUrl);
    }

    public static final Parcelable.Creator<Question> CREATOR
            = new Parcelable.Creator<Question>() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    private Question(Parcel in) {
        /*this.question = in.readString();
        this.optionA = in.readString();
        this.optionAScore = in.readString();
        this.optionB = in.readString();
        this.optionBScore = in.readString();
        this.optionC = in.readString();
        this.optionCScore = in.readString();
        this.optionD = in.readString();
        this.optionDScore =in.readString();
        this.imageUrl=in.readString();
    }
    @Override
    public String toString() {
        /*return "QuestionString='" + question + '\'' +
                ", Option A='" + optionA + '\'' +
                ", Option A Score='" + optionAScore + '\'' +
                ", Option B='" + optionB + '\'' +
                ", Option B Score='" + optionBScore + '\'' +
                ", Option C='" + optionC + '\'' +
                ", Option C Score='" + optionCScore + '\'' +
                ", Option D='" + optionD + '\'' +
                ", Option D Score='" + optionDScore + '\'' +
                ", image Url='" + imageUrl + '\'' +
                '}';
        return  hashMap.toString()+imageUrl;
    }


}

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }
    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionA(String optionA) {
        this.optionA=optionA;
    }

    public void setOptionB(String optionB) {
        this.optionB=optionB;
    }
    public void setOptionC(String optionC) {
        this.optionC=optionC;
    }

    public void setOptionD(String optionD) {
        this.optionD=optionD;
    }
*/
