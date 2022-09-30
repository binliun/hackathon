package com.usb.pocbot.cards;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.microsoft.bot.dialogs.Dialog;
import com.microsoft.recognizers.text.Metadata;


public class Answer {
    private String[] questions;
    private String answer;
    private double confidenceScore;

    @JsonIgnore
    private long id;
    @JsonIgnore
    private String source;
    @JsonIgnore
    private Metadata metadata;
    @JsonIgnore
    private Dialog dialog;

    public String[] getQuestions() { return questions; }
    public void setQuestions(String[] value) { this.questions = value; }

    public String getAnswer() { return answer; }
    public void setAnswer(String value) { this.answer = value; }

    public double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(double value) { this.confidenceScore = value; }
}
