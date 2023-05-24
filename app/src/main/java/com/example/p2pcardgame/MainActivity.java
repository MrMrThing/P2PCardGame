package com.example.p2pcardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    NetworkClass networkClass = new NetworkClass();

    private String baseURL = "http://10.0.2.2:5001/casino";

    class NetworkClass extends Thread implements Runnable {

        public void getPlayer(String playername){
            try{

                URL url = new URL ("http://10.0.2.2:5001/casino/get/player/" + playername);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.connect();

                int responseCode = conn.getResponseCode();
                System.out.println("responseCode: " + responseCode);

                if(responseCode != 200){
                    throw new RuntimeException("HttpResponseCode: " + responseCode);

                } else{
                    StringBuilder responseString = new StringBuilder();
                    Scanner scanner = new Scanner(url.openStream());

                    while (scanner.hasNext()){
                        responseString.append(scanner.nextLine());
                    }
                    scanner.close();
                    System.out.println(responseString);

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            System.out.println("Hello world");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(MainActivity.this);

        Thread netWorkThread = new Thread(networkClass);
        netWorkThread.start();
    }

    @Override
    public void onClick(View view) {

        networkClass.getPlayer("George");
    }

}