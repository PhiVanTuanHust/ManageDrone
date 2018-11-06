package com.journaldev.navigationviewexpandablelistview.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phí Văn Tuấn on 5/11/2018.
 */

public class QuestionModel extends BaseItemModel{
    public QuestionModel(String title) {
        super(title);
    }

    public QuestionModel() {
    }

    @Override
    public int getType() {
        return 0;
    }
    public List<QuestionModel> getQuestionSuggest(){
        List<QuestionModel> lst=new ArrayList<>();
        lst.add(new QuestionModel("Làm cách nào chọn nội dung thông báo tôi sẽ nhận"));
        lst.add(new QuestionModel("Làm cách nào để đối mật khẩu"));
        lst.add(new QuestionModel("Làm cách nào để khoanh vùng khu vực cần quan sát"));
        lst.add(new QuestionModel("Tôi có thể tìm thấy cài đặt của mình ở đâu "));
        lst.add(new QuestionModel("Làm cách nào chọn nội dung thông báo tôi sẽ nhận"));
        return lst;
    }
}
