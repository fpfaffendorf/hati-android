package ar.com.federicopfaffendorf.hatispace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class ServerConfig extends AppCompatActivity {

    EditText server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_config);
        server = findViewById(R.id.editText2);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sharedServer = preferences.getString("server", "DEFAULT");
        if (sharedServer == "DEFAULT") sharedServer = "http://hati.space/app";
        server.setText(sharedServer, TextView.BufferType.EDITABLE);
    }

    public void connectClick(View v)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("server", server.getText().toString());
        editor.commit();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("server", server.getText().toString());
        setResult(AppCompatActivity.RESULT_OK, returnIntent);
        finish();
    }

}
