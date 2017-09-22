package com.maliket.digimark.carecoop;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private TextView textView;
    private Toolbar toolbar;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(NetworkAvailable()){
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.carecooptoobar);
        setSupportActionBar(toolbar);
        if(NetworkAvailable()){
            toolbar.setVisibility(View.GONE);
        }
        toolbar.setTitle("CareCoop Zambia");
        //toolbar.setBackgroundColor(Color.parseColor("#ffffff"));

        webView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://carecoop.herokuapp.com/");
        webView.setWebChromeClient(new WebChromeClient());

        textView = (TextView)findViewById(R.id.noInternetText);
        settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                Intent intent=new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
            }
        });

        if(NetworkAvailable()){

            textView.setVisibility(View.GONE);
            settingsButton.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);

        }else{

            textView.setVisibility(View.VISIBLE);
            settingsButton.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_about:

            CustomDialog(this,"About","About Carecoop");

                break;
            case R.id.action_help:

                CustomDialog(this,"Help","Customer Care");

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void CustomDialog(Context ctx, String Title, String Message) {
        // custom dialog
        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle(Title);

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(Message);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private boolean NetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
