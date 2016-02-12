
/*
* Assignment : 2B
* File Name:MainActivity.java
* Full Name: Kedar Vijay Kulkarni
* */
package foodapp.kk.com.inclass2b;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult, textViewResultLabel;
    private EditText editTextInput;
    private RadioGroup rg;
    private double input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult=(TextView) findViewById(R.id.textViewResult);
        textViewResultLabel= (TextView) findViewById(R.id.textViewResultLabel);
        editTextInput=(EditText) findViewById(R.id.editTextInput);
        rg=(RadioGroup) findViewById(R.id.radioGroup);
    }

    public void convert(View v){
        int idChecked=rg.getCheckedRadioButtonId();
        if(idChecked==-1){
            Toast.makeText(MainActivity.this,R.string.emptySelection,Toast.LENGTH_LONG).show();
        }

        else if(editTextInput.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.this,R.string.emptyInput,Toast.LENGTH_LONG).show();
        }
        else if(idChecked==R.id.radioButtonClearAll){
            editTextInput.setText("");
            editTextInput.setHint(R.string.hint);
            textViewResultLabel.setText(R.string.result);
            textViewResult.setText("");

        }
        else if(idChecked == R.id.radioButtonToInch)
        {

            input=Double.parseDouble(editTextInput.getText().toString());
            textViewResultLabel.setText(R.string.inch);
            textViewResult.setText("" + input * 39.3701);

        }
        else if(idChecked==R.id.radioButtonToFeet){

            input=Double.parseDouble(editTextInput.getText().toString());
            textViewResultLabel.setText(R.string.feet);
            textViewResult.setText(""+input*3.28);

        }
        else if(idChecked==R.id.radioButtonToMiles){
            input=Double.parseDouble(editTextInput.getText().toString());
            textViewResultLabel.setText(R.string.miles);
            textViewResult.setText(""+input*0.0006);
        }


    }
}
