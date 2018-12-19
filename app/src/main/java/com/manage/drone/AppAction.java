package com.manage.drone;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public enum AppAction {


    DONE_STEP_1("finish_step1"),
    REVERSE_STEP_1("reverse_step1"),
    REVERSE_STEP_2("reverse_step2"),
    CHECK_STEP("check_step"),
    DO_START("do_start"),
    DONE_STEP_2("finish_step2"),
    DONE_STEP_3("finish_step3");

    public String value;

    AppAction(String value) {
        this.value = value;
    }

    public String getExtraData() {
        return extraData;
    }

    private String extraData;

    public AppAction setData(Object extraData) {
        this.extraData = new Gson().toJson(extraData);
        return this;
    }

    public <T> T getData(Class<T> target) {
        return new Gson().fromJson(extraData, target);
    }


    @Override
    public String toString() {
        return value;
    }
}
