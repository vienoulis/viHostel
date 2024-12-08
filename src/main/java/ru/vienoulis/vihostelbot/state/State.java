package ru.vienoulis.vihostelbot.state;

public enum State {
    INIT,
    READY,
    IN_PROCESS,
    ERROR;
    // todo 
    private String process;

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }
}
