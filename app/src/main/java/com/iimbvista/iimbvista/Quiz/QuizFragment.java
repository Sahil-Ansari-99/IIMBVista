package com.iimbvista.iimbvista.Quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iimbvista.iimbvista.Model.Quiz;
import com.iimbvista.iimbvista.Model.QuizRoot;
import com.iimbvista.iimbvista.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.e;

public class QuizFragment extends Fragment {
    private List<Quiz> itemList;
    private TextView questionText;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private int currQuestion;
    private Button submitButton;
    private View view;
//    private String API_LINK = "http://www.iimb-vista.com/2019/Quiz.json";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.quiz_fragment, container, false);
        radioGroup = (RadioGroup)view.findViewById(R.id.quiz_radioGroup);
        rb1 = (RadioButton)view.findViewById(R.id.option1);
        rb2 = (RadioButton)view.findViewById(R.id.option2);
        rb3 = (RadioButton)view.findViewById(R.id.option3);
        rb4 = (RadioButton)view.findViewById(R.id.option4);
        questionText = (TextView)view.findViewById(R.id.quiz_card_question);
        itemList = new ArrayList<>();
        submitButton = (Button)view.findViewById(R.id.quiz_card_submit);

        loadQuizData();

        return view;
    }

    private void loadQuizData() {
        Log.e("Start", "Loading Data");
        String API_LINK = "http://www.iimb-vista.com/2019/Quiz.json";

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        StringRequest stringRequest =  new StringRequest(Request.Method.GET, API_LINK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                QuizRoot rootObject=gson.fromJson(response, new TypeToken<QuizRoot>(){}.getType());
                itemList=rootObject.getQuiz();
                Log.e("Test", itemList.toString());
                setViews();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
//        String tempString = temp();
//        Gson gson=new Gson();
//        QuizRoot rootObject=gson.fromJson(tempString, new TypeToken<QuizRoot>(){}.getType());
//        itemList=rootObject.getQuiz();
//        setViews();
    }

    private String temp() {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try{
            bufferedReader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("Quiz.json")));
            String line = "";
            while((line = bufferedReader.readLine())!= null){
                stringBuilder.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public void setViews() {
        currQuestion = 0;
        changeView(0);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton)view.findViewById(selectedId);
                if (radioButton.getText().equals(itemList.get(currQuestion).getSolution())) {
                    Toast.makeText(getContext(), "Correct!", Toast.LENGTH_LONG).show();
                    currQuestion++;
                    radioGroup.clearCheck();
                    if (currQuestion < itemList.size()-1) {
                        changeView(currQuestion);
                    }
                    else {
                        startActivity(new Intent(getContext(), QuizCongo.class));
                    }
                } else {
                    Toast.makeText(getContext(), "Incorrect, Try Again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void changeView(int id) {
        questionText.setText(itemList.get(id).getQuestion());
        rb1.setText(itemList.get(id).getAnswers().get(0));
        rb2.setText(itemList.get(id).getAnswers().get(1));
        rb3.setText(itemList.get(id).getAnswers().get(2));
        rb4.setText(itemList.get(id).getAnswers().get(3));
    }
}
