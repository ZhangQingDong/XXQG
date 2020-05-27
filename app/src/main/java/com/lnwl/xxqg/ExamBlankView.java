package com.lnwl.xxqg;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * description : TODO:类的作用
 * author : zqd
 * date : 2019-10-31 15:37
 */
public class ExamBlankView extends LinearLayout {

    private Context context;
    private QuestionBean questionBean;
    private TextAdapter textAdapter;
    private RecyclerView rv_list;

    private int examType = -1;//考试类型，是考试还是试题回顾
    private int realFakeType = -1;//是自测还是考试类型

    public int getRealFakeType() {
        return realFakeType;
    }

    public void setRealFakeType(int realFakeType) {
        this.realFakeType = realFakeType;
    }

    public int getExamType() {
        return examType;
    }

    public void setExamType(int examType) {
        this.examType = examType;
    }

    public ExamBlankView(Context context) {
        this(context, null);
    }

    public ExamBlankView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExamBlankView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ExamBlankView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.layout_exam_blank_view, this);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        rv_list = findViewById(R.id.rv_list);
        textAdapter = new TextAdapter(new ArrayList<>());
        int screenWidth = getScreenWidth();//获取屏幕宽度
        int contentWidth = screenWidth - DensityUtils.dp2px(context, Constans.EXAM_PADDING_DP);//去除padding宽度
        int numInLine = contentWidth / DensityUtils.dp2px(context, Constans.EXAM_LETTER_DP);//计算出一行字数
        rv_list.setLayoutManager(new GridLayoutManager(context, numInLine));//设置一行个数
        rv_list.setAdapter(textAdapter);
    }

    /**
     * 设置题目数据
     */
    public void setQuestion(QuestionBean questionBean) {
        this.questionBean = questionBean;
        if (this.questionBean != null) {
            List<BlankTextBean> adapterData = getAdapterData(questionBean.getQuestionContent());
            if (textAdapter != null) {
                textAdapter.clearList();//清空edittextlist
                textAdapter.setNewData(adapterData);
            }
        }
    }

    /**
     * 转换适配器数据
     *
     * @param content
     */
    private List<BlankTextBean> getAdapterData(String content) {
        List<BlankTextBean> blankTextBeanList = new ArrayList<>();
        if (content == null || content.isEmpty()) {
            return blankTextBeanList;
        } else {
            for (int i = 0; i < content.length(); i++) {
                String str = String.valueOf(content.charAt(i));
                BlankTextBean blankTextBean = new BlankTextBean();
                if (Constans.BALNK_TEXT.equals(str)) {
                    blankTextBean.setText("");
                    blankTextBean.setBlank(true);
                } else {
                    blankTextBean.setText(str);
                    blankTextBean.setBlank(false);
                }
                blankTextBeanList.add(blankTextBean);
            }
            return blankTextBeanList;
        }
    }

    class TextAdapter extends BaseQuickAdapter<BlankTextBean, BaseViewHolder> {

        private List<EditText> editTextList = new ArrayList<>();
        private boolean isDelete = false;

        public TextAdapter(@Nullable List<BlankTextBean> data) {
            super(R.layout.item_blank_text, data);
        }

        public void clearList() {
            editTextList.clear();
        }

        /**
         * 保存答案
         */
        private void saveData() {
            String str = "";
            for (int i = 0; i < editTextList.size(); i++) {
                str += editTextList.get(i).getText().toString().trim();
            }
            if (ExamBlankView.this.questionBean != null) {
                ExamBlankView.this.questionBean.setUserAnswer(str);
            }
        }

        /**
         * 获取首个焦点位置
         *
         * @return
         */
        private int getFirstFocusPosition() {
            for (int i = 0; i < editTextList.size(); i++) {
                if (!editTextList.get(i).getText().toString().isEmpty()) {
                    if (i == (editTextList.size() - 1)) {
                        return i;
                    }
                } else {
                    return i;
                }
            }
            return 0;
        }

        /**
         * 获取点击的edittext在集合的位置
         *
         * @param editText
         * @return
         */
        private int getEdittextPosition(EditText editText) {
            for (int i = 0; i < editTextList.size(); i++) {
                if (editText.hashCode() == editTextList.get(i).hashCode()) {
                    return i;
                }
            }
            return 0;
        }


        @Override
        protected void convert(BaseViewHolder helper, BlankTextBean item) {
            TextView tv_text = helper.getView(R.id.tv_text);
            EditText et_text = helper.getView(R.id.et_text);
            tv_text.setText(item.getText());
            if (item.isBlank()) {
                editTextList.add(et_text);
                //设置答案文字
                if (ExamBlankView.this.questionBean != null && ExamBlankView.this.questionBean.getUserAnswer() != null) {
                    if (editTextList.size() <= ExamBlankView.this.questionBean.getUserAnswer().length()) {
                        editTextList.get(editTextList.size() - 1).setText(String.valueOf(ExamBlankView.this.questionBean.getUserAnswer().charAt(editTextList.size() - 1)));
                    }
                }
                tv_text.setVisibility(View.GONE);
                et_text.setVisibility(View.VISIBLE);
            } else {
                tv_text.setVisibility(View.VISIBLE);
                et_text.setVisibility(View.GONE);
            }
            /**
             * 设置获取焦点事件，使得焦点在第一个没字的edittext上，如果都有字停留在最后一个格子处
             */
            et_text.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus && !isDelete) {
                    editTextList.get(getFirstFocusPosition()).requestFocus();
                }
            });

            /**
             * 焦点的转移
             */
            et_text.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int edittextPosition = getEdittextPosition(et_text);//当前点击的edittext在几何中的位置
                    if (s.toString().length() >= 1 && (edittextPosition + 1) < editTextList.size()) {//增加
                        editTextList.get(edittextPosition + 1).requestFocus();
                    } else if (s.toString().length() < 1 && (edittextPosition - 1) >= 0) {//删除
                        isDelete = true;
                        editTextList.get(edittextPosition - 1).requestFocus();
                        isDelete = false;
                    }
                }
            });

            /**
             * 退格键删除空时的操作
             */
            et_text.setOnKeyListener((v, keyCode, event) -> {
                int edittextPosition = getEdittextPosition(et_text);
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (et_text.getText().toString().isEmpty()) {//当前文字为空
                        isDelete = true;
                        if (edittextPosition - 1 >= 0) {
                            editTextList.get(edittextPosition - 1).requestFocus();
                        } else {
                            editTextList.get(edittextPosition).requestFocus();
                        }
                        isDelete = false;
                        return true;
                    } else if (!et_text.getText().toString().isEmpty()) {//当前文字不为空
                        isDelete = true;
                        et_text.setText("");
                        if (edittextPosition - 1 >= 0) {
                            editTextList.get(edittextPosition - 1).requestFocus();
                        } else {
                            editTextList.get(edittextPosition).requestFocus();
                        }
                        isDelete = false;
                        return true;
                    }
                    return false;
                }
                return false;
            });
        }
    }

    /**
     * 保存答案
     */
    public void saveData() {
        if (textAdapter != null) {
            textAdapter.saveData();
        }
    }

    /**
     * 获取屏幕宽度
     */
    public int getScreenWidth() {
        DisplayMetrics dm2 = getResources().getDisplayMetrics();
        return dm2.widthPixels;
    }

}
