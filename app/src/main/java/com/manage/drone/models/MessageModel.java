package com.manage.drone.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phí Văn Tuấn on 1/11/2018.
 */

public class MessageModel {
    public static final int SEEN=1;
    public static final int NOT_SEEN=2;
    private String from;
    private String description;
    private String date;
    private int state;

    public MessageModel() {
    }

    public MessageModel(String from, String description, String date,int state) {
        this.from = from;
        this.description = description;
        this.date = date;
        this.state=state;
    }

    public String getFrom() {
        return from;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getState() {
        return state;
    }
    public List<MessageModel> getMessage(){
        List<MessageModel> lstMessage=new ArrayList<>();
        lstMessage.add(new MessageModel("Admin","Thông báo drone của bạn hết pin","23:39:14 31/10/2018",NOT_SEEN));
        lstMessage.add(new MessageModel("Admin","Khẩn cấp drone của bạn đi lạc","23:32:14 31/10/2018",SEEN));
        lstMessage.add(new MessageModel("Admin","Thông báo ảnh được gửi về hệ thống","23:29:14 31/10/2018",SEEN));
        lstMessage.add(new MessageModel("Admin","Thông báo drone của bạn hết pin","23:39:14 31/10/2018",SEEN));
        lstMessage.add(new MessageModel("Admin","Thông báo drone của bạn hết pin","23:39:14 31/10/2018",SEEN));
        lstMessage.add(new MessageModel("Admin","Thông báo drone của bạn hết pin","23:39:14 31/10/2018",SEEN));
        return lstMessage;
    }
}
