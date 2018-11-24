package com.manage.drone.customs;

public class Step {
    private String step;
    private boolean isDone;

    public Step(String step, boolean isDone) {
        this.step = step;
        this.isDone = isDone;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
