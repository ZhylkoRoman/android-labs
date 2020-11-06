package by.bsu.lab1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private TextView textView;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submit = findViewById(R.id.submitButton);
        textView = findViewById(R.id.textView);
        if (getIntent().getStringExtra(Constants.EXTRA) != null)
            textView.setText(getIntent().getStringExtra(Constants.EXTRA));
        input = findViewById(R.id.input);
        submit.setOnClickListener(onSubmitClickListener);
    }

    View.OnClickListener onSubmitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            if (input.getText().toString().isEmpty())
                Toast.makeText(getApplicationContext(), R.string.submit_lock, Toast.LENGTH_SHORT).show();
            else {
                //intent.putExtra(Constants.EXTRA, Integer.parseInt(input.getText().toString()) + Integer.parseInt(textView.getText().toString()) + "");
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    };
}
