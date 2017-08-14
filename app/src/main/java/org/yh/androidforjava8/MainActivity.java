package org.yh.androidforjava8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable()
        {
            public void run()
            {
                System.out.println("Run!");
            }
        }).start();

        new Thread(() -> System.out.println("Run!")).start();
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
        findViewById(R.id.text).setOnClickListener(view -> Toast.makeText(this, "afadfasd", Toast
                .LENGTH_SHORT).show());
    }
}
