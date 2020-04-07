package com.example.loginactivity;
import java.io.Serializable;
import java.util.List;

public class LectureHistory implements Serializable {
    List<LectureReviewDetails> lecture_history;
    boolean success;

    public List<LectureReviewDetails> getLecture_history() {
        return lecture_history;
    }

    @Override
    public String toString() {
        return "LectureHistory{" +
                "lecture_history=" + lecture_history +
                ", success=" + success +
                '}';
    }

    public void setLecture_history(List<LectureReviewDetails> lecture_history) {
        this.lecture_history = lecture_history;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
