package ar.com.federicopfaffendorf.hatispace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;
    private ProgressDialog myProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebView = findViewById(R.id.webview);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sharedServer = preferences.getString("server", "DEFAULT");
        if (sharedServer == "DEFAULT") sharedServer = "http://hati.space/app";
        loadServer(sharedServer);
    }

    public void loadServer(String server) {
        myProgressDialog = ProgressDialog.show(
                this,
                "",
                "Connecting to server ...",
                true,
                true,
                new DialogInterface.OnCancelListener(){
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        myWebView.stopLoading();
                        onBackPressed();
                    }
                });
        myProgressDialog.setCanceledOnTouchOutside(false);
        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                if ((myProgressDialog != null) && (myProgressDialog.isShowing())) myProgressDialog.dismiss();
            }
        });
        myWebView.loadUrl(server);
        myWebView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, ServerConfig.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState ){
        super.onSaveInstanceState(outState);
        myWebView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        myWebView.restoreState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == AppCompatActivity.RESULT_OK){
                loadServer(data.getStringExtra("server"));
            }
        }
    }

}
