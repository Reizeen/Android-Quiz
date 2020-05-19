package com.ericarias.quiz.Model;

public class Report {

    private String report;
    private int idQuestion;

    public Report(String report, int idQuestion) {
        this.report = report;
        this.idQuestion = idQuestion;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }
}
