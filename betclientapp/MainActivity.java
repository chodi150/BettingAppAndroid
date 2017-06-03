package chodi.com.betclientapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText usernameTxt;
    private EditText passwordTxt;
    private TextView errorTxt;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        initializeWidgets();
        setOnClickForLoginBtn();
    }

    private void setOnClickForLoginBtn() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectWithServer();
            }
        });
    }

    private void connectWithServer() {
        ServerConnector serverConnector = new ServerConnector();
        ServerClient serverClient = new ServerClient();

        try {
            if(serverClient.login(ServerConnector.clientSocket, usernameTxt.getText().toString(), passwordTxt.getText().toString()))
            {
                errorTxt.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getApplicationContext(), MenuTabbedAcivity.class);


                UserData.login = usernameTxt.getText().toString();
                UserData.upcomingMatches = serverClient.receiveScheduledMatchesFromServer(ServerConnector.clientSocket);
                UserData.finishedMatches = serverClient.receiveScheduledMatchesFromServer(ServerConnector.clientSocket);
                UserData.rankOfPlayers = serverClient.receiveRankOfPlayersFromServer(ServerConnector.clientSocket);
                startActivity(intent);
            }
            else
            {
                errorTxt.setVisibility(View.VISIBLE);
            }
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Problems while connecting to server\nTry again later.", Toast.LENGTH_SHORT).show();
        }
    }


    private void initializeWidgets() {
        loginBtn = (Button) findViewById(R.id.loginBtn);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        usernameTxt = (EditText) findViewById(R.id.usernameTxt);
        errorTxt = (TextView)findViewById(R.id.errorTxt);

    }

}
