package by.bsu.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submit = findViewById(R.id.submitButton);
        textView = findViewById(R.id.textView);
        input = findViewById(R.id.input);
        submit.setOnClickListener(onSubmitClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra(Constants.EXTRA);
                textView.setText(result);
            }
        }
    }

    View.OnClickListener onSubmitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
            if (input.getText().toString().isEmpty())
                Toast.makeText(getApplicationContext(), R.string.submit_lock, Toast.LENGTH_SHORT).show();
            else {
                intent.putExtra(Constants.EXTRA, Integer.parseInt(input.getText().toString()) + Integer.parseInt(textView.getText().toString()) + "");
                startActivityForResult(intent, Constants.REQUEST_CODE);
            }
        }
    };
}
