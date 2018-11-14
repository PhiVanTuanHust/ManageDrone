package com.manage.drone.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phí Văn Tuấn on 5/11/2018.
 */

public class QuestionModel extends BaseItemModel{
    public QuestionModel(String title,String answer) {
        super(title);
        this.answer  = answer;
    }
    private String answer;
    public QuestionModel() {
    }

    @Override
    public int getType() {
        return 0;
    }
    public List<QuestionModel> getQuestionSuggest(){
        List<QuestionModel> lst=new ArrayList<>();
        lst.add(new QuestionModel("Làm cách nào chọn nội dung thông báo tôi sẽ nhận","Bạn vào phần menu abc"));
        lst.add(new QuestionModel("Làm cách nào để đối mật khẩu","Bạn vào phần quên mật khẩu của giao diện login"));
        lst.add(new QuestionModel("Làm cách nào để khoanh vùng khu vực cần quan sát","Bạn chọn chức năng khoanh vùng khu vực quan sát trên thanh menu"));
        lst.add(new QuestionModel("Tôi có thể tìm thấy cài đặt của mình ở đâu ","Bạn chọn Cài đặt ở menu bên trái"));
        return lst;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
