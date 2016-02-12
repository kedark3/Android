package foodapp.kk.com.inclass2a;

/*
* Assignment : 2A
* File Name:MainActivity.java
* Full Name: Kedar Vijay Kulkarni
* */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextInput;
    private double metersInput;
    private TextView resultTextView,resultTextViewLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextInput=(EditText)findViewById(R.id.EditTextInput);
        resultTextView= (TextView)  findViewById(R.id.textViewResult);
        resultTextViewLabel=(TextView) findViewById(R.id.textViewResultLabel);
    }
    public void convertToInch(View v){
        if(!editTextInput.getText().toString().isEmpty()) {
            metersInput = Double.parseDouble(editTextInput.getText().toString());
            resultTextView.setText("" + (39.3701 * metersInput));
            resultTextViewLabel.setText(R.string.inch);
        }
        else
        {
            Toast.makeText(MainActivity.this,R.string.no_input,Toast.LENGTH_LONG).show();
        }
    }

    public void convertToFeets(View v){
        if(!editTextInput.getText().toString().isEmpty())
        {
            metersInput= Double.parseDouble(editTextInput.getText().toString());
            resultTextView.setText(""+(3.28*metersInput));
            resultTextViewLabel.setText(R.string.feet);
        }
        else
        {
            Toast.makeText(MainActivity.this,R.string.no_input,Toast.LENGTH_LONG).show();
        }
    }
    public void convertToMiles(View v){
        if(!editTextInput.getText().toString().isEmpty())
        {
            metersInput= Double.parseDouble(editTextInput.getText().toString());
            resultTextView.setText(""+(0.0006*metersInput));
            resultTextViewLabel.setText(R.string.miles);
        }
        else
        {
            Toast.makeText(MainActivity.this,R.string.no_input,Toast.LENGTH_LONG).show();
        }
    }

    public void clearAll(View v){
        editTextInput.setText("");
        editTextInput.setHint(R.string.hint);
        resultTextViewLabel.setText(R.string.result);
        resultTextView.setText("");
    }
}
