package com.example.loginactivity;

public class TeacherLoginResponse {
        private TeacherLoginResult result;
        private TeacherData data;
        public TeacherLoginResult getResult() {
            return result;
        }
        public void setResult(TeacherLoginResult result) {
            this.result = result;
        }

    @Override
    public String toString() {
        return "TeacherLoginResponse{" +
                "result=" + result +
                ", data=" + data +
                '}';
    }

    public TeacherData getData() {
            return data;
        }
        public void setData(TeacherData data) {
            this.data = data;
        }


}
