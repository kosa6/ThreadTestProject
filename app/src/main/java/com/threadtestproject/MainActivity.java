package com.threadtestproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button start;
    private Button stop;
    private TextView textNumber;
    private ProgressBar progressBar;
    private Button test;
    private ThreadCount thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textNumber = (TextView) findViewById(R.id.editText2);
        test = (Button) findViewById(R.id.Test);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        stop = (Button) findViewById(R.id.Stop);
        start = (Button) findViewById(R.id.Start);
        startOnClick();
        stopOnClick();
        testOnClick();
    }
    private void startOnClick() {
        start.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                int number = getNumber();
                toastMessage(Integer.toString(number));
                progressBar.setMax(number*10);
                progressBar.setProgress(0);
                thread = new ThreadCount();
                start.setClickable(false);
                thread.execute(number);
            }
        });
    }
    private void stopOnClick() {
        stop.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                thread.cancel(true);
                progressBar.setProgress(0);
                start.setClickable(true);
            }
        });
    }
    private void testOnClick() {
        test.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                toastMessage("Running");
                thread.getStatus();
            }
        });
    }
    private int getNumber() {
        return Integer.parseInt(textNumber.getText().toString());
    }
    private void toastMessage(String message)
    {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private class ThreadCount extends AsyncTask<Integer, Integer, Integer> {

        protected Integer doInBackground(Integer... number) {
            int x=0;
            for (int i = 0; i < number[0]; i++) {
                if(isCancelled()==true) {
                    break;
                }
                x+=10;
                try {
                    Thread.sleep(1000);
                    publishProgress(x);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return x;
        }

        protected void onProgressUpdate(Integer... progress) {
            progressBar.setProgress(progress[0]);
        }

        protected void onPostExecute(Integer result) {
            toastMessage("Done");
        }
    }
}
