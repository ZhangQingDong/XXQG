package com.lnwl.xxqg;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * description : TODO:类的作用
 * author : zqd
 * date : 2019-10-11 17:53
 */
public class QuestionBean implements Parcelable {

    /**
     * 是否选中
     */
    private boolean select = false;
    private String rowGuid;
    /**
     * 题目类型（选择填空。。。）
     */
    private int questionType;
    /**
     * 题目内容
     */
    private String questionContent;
    /**
     * 知识分类
     */
    private String contentType;
    /**
     * 知识分类
     */
    private String contentTypeText;
    /**
     * 1简单2一般3困难
     */
    private String questionLevelText;
    /**
     * 题目类型（选择填空。。。）
     */
    private String questionTypeText;
    /**
     * 1简单2一般3困难
     */
    private String questionLevel;
    /**
     * userAnswer
     * 用户写的答案
     */
    private String userSolution;
    /**
     * 答案
     */
    private String solution;
    /**
     * 答案解析
     */
    private String description;
    /**
     * 题目分数
     */
    private String questionScore;
    /**
     * 审核状态
     */
    private String auditStatus;

    /**
     * 出题人
     */
    private String userGuid;
    /**
     * 出题时间
     */
    private String operateDate;

    /**
     * 出题单位
     */
    private String orgGuid;

    /**
     * 题目是否正确
     * 1错
     * 0对
     * 2针对于多选题的，漏选情况
     */
    private String answerIsRight;

    private String score;

    /**
     * 获取答案状态文字
     *
     * @return
     */
    public String getAnswerIsRightText() {
        switch (getAnswerIsRight() == null ? "" : getAnswerIsRight()) {
            case "1":
                return "错误";
            case "0":
                return "正确";
            case "2":
                return "漏选";
            default:
                return "";
        }
    }

    public String getAnswerIsRight() {
        return answerIsRight;
    }

    public void setAnswerIsRight(String answerIsRight) {
        this.answerIsRight = answerIsRight;
    }

    public String getUserAnswer() {
        return userSolution;
    }

    public void setUserAnswer(String userSolution) {
        this.userSolution = userSolution;
    }

    public String getRowGuid() {
        return rowGuid;
    }

    public void setRowGuid(String rowGuid) {
        this.rowGuid = rowGuid;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentTypeText() {
        return contentTypeText;
    }

    public void setContentTypeText(String contentTypeText) {
        this.contentTypeText = contentTypeText;
    }

    public String getQuestionLevelText() {
        return questionLevelText;
    }

    public void setQuestionLevelText(String questionLevelText) {
        this.questionLevelText = questionLevelText;
    }

    public String getQuestionTypeText() {
        return questionTypeText;
    }

    public void setQuestionTypeText(String questionTypeText) {
        this.questionTypeText = questionTypeText;
    }

    public String getQuestionLevel() {
        return questionLevel;
    }

    public void setQuestionLevel(String questionLevel) {
        this.questionLevel = questionLevel;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuestionScore() {
        return score;
    }

    public void setQuestionScore(String score) {
        this.score = score;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(String operateDate) {
        this.operateDate = operateDate;
    }

    public String getOrgGuid() {
        return orgGuid;
    }

    public void setOrgGuid(String orgGuid) {
        this.orgGuid = orgGuid;
    }

    public String getQuestionTiXing() {
        String str = "";
        return str;
    }

    public String getQuestionFenLei() {
        String str = "";
        return str;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public QuestionBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
        dest.writeString(this.rowGuid);
        dest.writeInt(this.questionType);
        dest.writeString(this.questionContent);
        dest.writeString(this.contentType);
        dest.writeString(this.contentTypeText);
        dest.writeString(this.questionLevelText);
        dest.writeString(this.questionTypeText);
        dest.writeString(this.questionLevel);
        dest.writeString(this.userSolution);
        dest.writeString(this.solution);
        dest.writeString(this.description);
        dest.writeString(this.questionScore);
        dest.writeString(this.auditStatus);
        dest.writeString(this.userGuid);
        dest.writeString(this.operateDate);
        dest.writeString(this.orgGuid);
        dest.writeString(this.answerIsRight);
        dest.writeString(this.score);
    }

    protected QuestionBean(Parcel in) {
        this.select = in.readByte() != 0;
        this.rowGuid = in.readString();
        this.questionType = in.readInt();
        this.questionContent = in.readString();
        this.contentType = in.readString();
        this.contentTypeText = in.readString();
        this.questionLevelText = in.readString();
        this.questionTypeText = in.readString();
        this.questionLevel = in.readString();
        this.userSolution = in.readString();
        this.solution = in.readString();
        this.description = in.readString();
        this.questionScore = in.readString();
        this.auditStatus = in.readString();
        this.userGuid = in.readString();
        this.operateDate = in.readString();
        this.orgGuid = in.readString();
        this.answerIsRight = in.readString();
        this.score = in.readString();
    }

    public static final Creator<QuestionBean> CREATOR = new Creator<QuestionBean>() {
        @Override
        public QuestionBean createFromParcel(Parcel source) {
            return new QuestionBean(source);
        }

        @Override
        public QuestionBean[] newArray(int size) {
            return new QuestionBean[size];
        }
    };
}
