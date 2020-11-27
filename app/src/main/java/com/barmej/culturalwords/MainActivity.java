package com.barmej.culturalwords;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Array;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BUNDLE_CURRENT_IMAGE = "BUNDLE_CURRENT_IMAGE";
    private static final String BUNDLE_CURRENT_DESCRIPTION = "BUNDLE_CURRENT_DESCRIPTION";

    private ImageView image_view_question;
    private ImageView button_change_language;
    private static final int[] images = {
            R.drawable.icon_1,
            R.drawable.icon_2,
            R.drawable.icon_3,
            R.drawable.icon_4,
            R.drawable.icon_5,
            R.drawable.icon_6,
            R.drawable.icon_7,
            R.drawable.icon_8,
            R.drawable.icon_9,
            R.drawable.icon_10,
            R.drawable.icon_11,
            R.drawable.icon_12,
            R.drawable.icon_13,
    };
    private String[] answerDescription;
    private int mCurrentImages;
    private String mCurrentAnswerDescription;
    private static final int PERMISSIONS_WRITE_EXTERNAL_STORAGE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        String appLang = sharedPreferences.getString("app_lang", "");
        if (!appLang.equals(""))
            LocaleHelper.setLocale(this, appLang);

        setContentView(R.layout.activity_main);
        image_view_question = findViewById(R.id.image_view_question);
        answerDescription = getResources().getStringArray(R.array.answer_description);
        showNewImage();
        button_change_language = findViewById(R.id.button_change_language);
        button_change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLanguagesDialog();
            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_change_lang) {
            showLanguagesDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }*/

    private void showLanguagesDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.change_lang_text)
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String language = "ar";
                        switch (which) {
                            case 0:
                                language = "ar";
                                break;
                            case 1:
                                language = "en";
                                break;
                            case 2:
                                language = "fr";
                                break;
                        }

                        saveLanguage(language);
                        LocaleHelper.setLocale(MainActivity.this, language);
                        recreate();
                        /*Intent i = new Intent(getApplicationContext(),
                                MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);*/
                    }
                }).create();
        alertDialog.show();
    }

    private void saveLanguage(String lang) {
        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("app_lang", lang);
        editor.apply();
    }

    private void showNewImage(){
        Random random = new Random();
        int randomImageIndex = random.nextInt(images.length);
        mCurrentImages = images[randomImageIndex];
        mCurrentAnswerDescription = answerDescription[randomImageIndex];
        image_view_question.setImageDrawable(ContextCompat.getDrawable(this,mCurrentImages));
    }

    public void onChangeImageClicked(View view){
        showNewImage();
    }

    public void onOpenAnswerClicked(View view){
        Intent intent = new Intent(MainActivity.this,Answer.class);
        intent.putExtra("theAnswer",mCurrentAnswerDescription);
        startActivity(intent);
    }

    public void onShareQuestionClicked(View view){
        Intent i = new Intent(MainActivity.this,Share.class);
        i.putExtra("theImage",mCurrentImages);
        startActivity(i);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_CURRENT_IMAGE,mCurrentImages);
        outState.putString(BUNDLE_CURRENT_DESCRIPTION,mCurrentAnswerDescription);
        Log.i(TAG,"OnSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null){
            mCurrentImages = savedInstanceState.getInt(BUNDLE_CURRENT_IMAGE);
            image_view_question.setImageDrawable(ContextCompat.getDrawable(this,mCurrentImages));
            mCurrentAnswerDescription = savedInstanceState.getString(BUNDLE_CURRENT_DESCRIPTION);
        }
        Log.i(TAG,"OnRestoreInstanceState");
    }
}
