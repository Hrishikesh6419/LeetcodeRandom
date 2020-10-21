package com.hrishikeshdarshan.leetcode.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hrishikeshdarshan.leetcode.R;
import com.hrishikeshdarshan.leetcode.model.QuestionsPOJO;
import com.hrishikeshdarshan.leetcode.model.QuestionsService;
import com.hrishikeshdarshan.leetcode.model.StatStatusPair;

import java.util.ArrayList;
import java.util.Random;
import java.util.zip.Inflater;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String BASE_URL = "https://leetcode.com/problems/";
    public static final String TAG = "MainActivity";
    Spinner spinner;
    Button generate;
    QuestionsService questionsService;
    CompositeDisposable disposable = new CompositeDisposable();

    TextView tvId, tvTitle, tvUrl;


    ArrayList<StatStatusPair> easyQuestionsList;
    ArrayList<StatStatusPair> mediumQuestionsList;
    ArrayList<StatStatusPair> hardQuestionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.s_main_selectDifficulty);
        generate = findViewById(R.id.bGenerate);

        tvId = findViewById(R.id.tvId);
        tvTitle = findViewById(R.id.tvTitle);
        tvUrl = findViewById(R.id.tvUrl);

        questionsService = new QuestionsService();
        easyQuestionsList = new ArrayList<>();
        mediumQuestionsList = new ArrayList<>();
        hardQuestionsList = new ArrayList<>();


        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this, R.array.choose_dificulty, android.R.layout.simple_spinner_item);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        saveAllQuestionsLocally();
        generate.setOnClickListener(this);

    }

    private void saveAllQuestionsLocally() {


        disposable.add(

                questionsService.getAllQuestion().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<QuestionsPOJO>() {
                            @Override
                            public void onSuccess(QuestionsPOJO questionsPOJO) {

                                categorizeQuestion(questionsPOJO);

                            }

                            @Override
                            public void onError(Throwable e) {

                                Toast.makeText(getApplicationContext(), "Please turn on your internet", Toast.LENGTH_SHORT).show();

                            }
                        }));




    }

    private void categorizeQuestion(QuestionsPOJO questionsPOJO) {

        ArrayList<StatStatusPair> all = new ArrayList<>(questionsPOJO.statStatusPairs);

        for(int i = 0; i< all.size(); i++){
            if(all.get(i).difficulty.level == 1){
                easyQuestionsList.add(all.get(i));
            }else if(all.get(i).difficulty.level == 2){
                mediumQuestionsList.add(all.get(i));
            }else if(all.get(i).difficulty.level == 3){
                hardQuestionsList.add(all.get(i));
            }
        }
    }

    @Override
    public void onClick(View view) {
        Random random = new Random();

        switch (spinner.getSelectedItemPosition()) {

            case 0:

                Toast.makeText(getApplicationContext(),"Select a difficulty level..", Toast.LENGTH_SHORT).show();

                break;

            case 1:
                int ran = random.nextInt(easyQuestionsList.size());
                getRandomQuestion(ran, easyQuestionsList);

                break;
            case 2:
                int ran1 = random.nextInt(mediumQuestionsList.size());
                getRandomQuestion(ran1, mediumQuestionsList);
                break;

            case 3:
                int ran2 = random.nextInt(hardQuestionsList.size());
                getRandomQuestion(ran2, hardQuestionsList);
                break;

        }

    }

    private void getRandomQuestion(int ran, ArrayList<StatStatusPair> list) {

        Log.d(TAG, "onClick: list size is: "+ list.size());
        Log.d(TAG, "onClick: Random Number: " + ran);

        String id = "Question ID: "+ String.valueOf( list.get(ran).stat.frontendQuestionId);
        String title ="Title: " + list.get(ran).stat.questionTitle;
        String url = "URL: \n " +BASE_URL + list.get(ran).stat.questionTitleSlug;

        Log.d(TAG, "onClick: Random generated is: " + id +"\n"+ title +"\n"+ url +"\n");

        tvId.setText(id);
        tvTitle.setText(title);
        tvUrl.setText(url);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       super.onOptionsItemSelected(item);

       switch (item.getItemId()){

           case R.id.mShare:

               if(tvUrl.getText().length() != 0 && tvId.getText().length() != 0 && tvTitle.getText().length() != 0){
                   Intent intent = new Intent(Intent.ACTION_SEND);
                   intent.setType("text/plain");
                   intent.putExtra(Intent.EXTRA_SUBJECT, "Can you solve this problem?");
                   String sId = tvId.getText().toString();
                   String sTitle = tvTitle.getText().toString();
                   String sUrl = tvUrl.getText().toString();
                   String sendText = sId +"\n" + sTitle + "\n" + sUrl;
                   intent.putExtra(Intent.EXTRA_TEXT,sendText );
                   startActivity(Intent.createChooser(intent, "Share with"));
               }else{
                   Toast.makeText(getApplicationContext(),"Please select a problem..", Toast.LENGTH_SHORT).show();
               }

               break;

       }


       return true;
    }
}