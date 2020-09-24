package com.touchmediaproductions.CovidNetTFLite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.touchmediaproductions.CovidNetTFLite.R;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button btnClassify;
    TextView resultOutput;
    SwitchCompat modelSwitch;

    ProgressBar loadingCircle;

    TextView resultDetails;

    Uri imageuri;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);
        btnClassify = findViewById(R.id.btn_classify);
        resultOutput = findViewById(R.id.result);
        resultDetails = findViewById(R.id.resultdetails);
        modelSwitch = findViewById(R.id.toggle_modelAorB);

        loadingCircle = findViewById(R.id.loadingcircle);

        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 12);
            }
        });


        btnClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classifyForCOVIDAndPopulateUI();
            }
        });
    }

    private void classifyForCOVIDAndPopulateUI(){
        if(bitmap != null) {

            loadingCircle.setVisibility(View.VISIBLE);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Moves the current Thread into the background
                    // This approach reduces resource competition between the Runnable object's thread and the UI thread.
                    android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

                    //Get whether model is A or B
                    MLModels modelToUse;
                    if (modelSwitch.isChecked()) {
                        modelToUse = MLModels.MODEL_A_COVIDNET;
                    } else {
                        modelToUse = MLModels.MODEL_B_COVIDNET;
                    }

                    //Run Classification over the image and return a Prediction object containing top 3 results
                    final MLHelper.Prediction prediction = MLHelper.runClassificationOnBitmap(MainActivity.this, bitmap, modelToUse);

                    loadingCircle.post(new Runnable() {
                        @Override
                        public void run() {
                            loadingCircle.setVisibility(View.GONE);
                        }
                    });

                    resultOutput.post(new Runnable() {
                        @Override
                        public void run() {
                            //Display result
                            String result = prediction.getFirst();
                            resultOutput.setText(result);

                            //Make it red if COVID, blue if Normal and Orange if Pneumonia
                            if (result.contains("COVID-19")) {
                                resultOutput.setTextColor(Color.RED);
                            } else if (result.contains("Pneumonia")) {
                                resultOutput.setTextColor(Color.rgb(255, 165, 0));
                            } else if (result.contains("Normal")) {
                                resultOutput.setTextColor(getResources().getColor(R.color.colorPrimary, getTheme()));
                            }
                        }
                    });

                    resultDetails.post(new Runnable() {
                        @Override
                        public void run() {
                            resultDetails.setVisibility(View.VISIBLE);
                            String details = "AI Prediction: " + (int) prediction.getFirstValue()
                                    + "% " + prediction.getFirst()
                                    + " | " + (int) prediction.getSecondValue()
                                    + "% " + prediction.getSecond()
                                    + " | " + (int) prediction.getThirdValue()
                                    + "% " + prediction.getThird();
                            resultDetails.setText(details);
                        }
                    });

                }
            }).start();
        } else {
            Toast toast = new Toast(MainActivity.this);
            toast.makeText(MainActivity.this, "Please choose a photo first.", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 12 && resultCode == RESULT_OK && data != null){
            imageuri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}