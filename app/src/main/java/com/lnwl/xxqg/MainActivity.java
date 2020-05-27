package com.lnwl.xxqg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ExamBlankView e_blank_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        e_blank_view = findViewById(R.id.e_blank_view);
        QuestionBean questionBean = new QuestionBean();
        questionBean.setQuestionContent("题目内容题目内容题目内容□□□□题目内容题目内容题目内容。");
        e_blank_view.setQuestion(questionBean);
    }
}
