package com.company.androidprojectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tv_question; //= findViewById(R.id.tv_question);
    EditText et_answer; //= findViewById(R.id.et_answer);
    Button bt_submit; //= findViewById(R.id.bt_submit);

    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_question = findViewById(R.id.tv_question);
        et_answer = findViewById(R.id.et_answer);
        bt_submit = findViewById(R.id.bt_submit);


        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

                runTextToSpeech();

            }

            @Override
            public void onFinish() {

                et_answer.setEnabled(true);
                bt_submit.setEnabled(true);

            }
        }.start();

        /*final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runTextToSpeech();
            }
        }, 5000);

        et_answer.setEnabled(true);
        bt_submit.setEnabled(true);*/




        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String answer = "4";
                if(et_answer.getText().toString().trim().equals(answer)){

                    Toast.makeText(MainActivity.this, "CORRECT", Toast.LENGTH_LONG).show();

                }

                else{

                    Toast.makeText(MainActivity.this, "INCORRECT", Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    @Override
    protected void onDestroy() {

        if(textToSpeech != null){

            textToSpeech.stop();
            textToSpeech.shutdown();

        }

        super.onDestroy();
    }

    public void runTextToSpeech(){

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                if(i == TextToSpeech.SUCCESS){

                    int result = textToSpeech.setLanguage(Locale.US);

                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){

                        Log.i("ERROR", "Some Error");

                    }

                    else {
                        textToSpeech.setSpeechRate(1.0f);

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

                            textToSpeech.speak(tv_question.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);

                        }

                        else{

                            textToSpeech.speak(tv_question.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);

                        }

                    }

                }

                else{

                    Log.i("ERROR", "Some Error");

                }

            }
        });


    }
}