package com.manage.drone.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phí Văn Tuấn on 1/11/2018.
 */

public class MessageModel {
    public static final int SEEN = 1;
    public static final int NOT_SEEN = 2;
    private String from;
    private String description;
    private String date;
    private int state;

    public MessageModel() {
    }

    public MessageModel(String from, String description, String date, int state) {
        this.from = from;
        this.description = description;
        this.date = date;
        this.state = state;
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

    public List<MessageModel> getMessage() {
        List<MessageModel> lstMessage = new ArrayList<>();
        lstMessage.add(new MessageModel("Admin", "Khẩn cấp tọa độ (15,224) có nghi ngờ chặt phá rừng", "13:39:14 31/10/2018", NOT_SEEN));
        lstMessage.add(new MessageModel("Admin", "Khẩn cấp tọa độ (153,124) có nghi ngờ chặt phá rừng", "13:39:14 31/10/2018", NOT_SEEN));
        lstMessage.add(new MessageModel("Admin", "Khẩn cấp tọa độ (125,224) có nghi ngờ chặt phá rừng", "13:32:14 31/10/2018", SEEN));
        lstMessage.add(new MessageModel("Admin", "Khẩn cấp tọa độ (15,244) có nghi ngờ chặt phá rừng", "13:29:14 31/10/2018", SEEN));
        lstMessage.add(new MessageModel("Admin", "Khẩn cấp tọa độ (125,124) có nghi ngờ chặt phá rừng", "13:39:14 31/10/2018", SEEN));
        lstMessage.add(new MessageModel("Admin", "Khẩn cấp tọa độ (153,294) có nghi ngờ chặt phá rừng", "13:39:14 31/10/2018", SEEN));
        lstMessage.add(new MessageModel("Admin", "Khẩn cấp tọa độ (145,224) có nghi ngờ chặt phá rừng", "13:39:14 31/10/2018", SEEN));
        return lstMessage;
    }
}
