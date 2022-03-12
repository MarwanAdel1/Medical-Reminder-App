package com.example.medicalreminder.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalreminder.R;
import com.example.medicalreminder.views.add_medication_screen.fragments.AddMedNameFragment;
import com.example.medicalreminder.views.login_screen.LoginScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import cz.msebera.android.httpclient.Header;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class SplashScreen extends AppCompatActivity {
    public static final String MEDICINES = "medicine";
    public static final String DISEASES = "diseases";

    public static ArrayList<String> medicines = new ArrayList<>();
    public static ArrayList<String> diseases = new ArrayList<>();

    private String url = "https://github.com/MarwanAdel1/Data-set/blob/origin/Medication%20Guides.xls?raw=true";

    private AsyncHttpClient asyncHttpClient;
    private WorkbookSettings workbookSettings;
    private Workbook workbook;

    FirebaseAuth firebaseAuth;
    Button logout_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logout_button = findViewById(R.id.logout_btn);

        //      Initialize firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //          Handle Buttons
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(SplashScreen.this, LoginScreen.class));
            }
        });

        asyncHttpClient = new AsyncHttpClient();

        asyncHttpClient.get(url, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Log.e(AddMedNameFragment.class.getSimpleName(), "Failed : " + statusCode);
                Log.e(AddMedNameFragment.class.getSimpleName(), "Failed : " + file.getName());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                workbookSettings = new WorkbookSettings();
                workbookSettings.setGCDisabled(true);

                if (file != null) {
                    try {
                        workbook = workbook.getWorkbook(file);
                        Sheet sheet = workbook.getSheet(0);

                        for (int i = 0; i < sheet.getRows(); i++) {
                            Cell[] row = sheet.getRow(i);

                            if (!row[0].getContents().isEmpty()) {
                                medicines.add(row[0].getContents());
                            }

                            if (!row[1].getContents().isEmpty()) {
                                diseases.add(row[1].getContents());
                            }
                        }
                        Intent intent = new Intent(SplashScreen.this, TabBar.class);
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (BiffException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}