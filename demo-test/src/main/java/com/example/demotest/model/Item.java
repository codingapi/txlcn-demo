package com.example.demotest.model;

public class Item {

    private String caption;

    private String commandLine;

    private String processId;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    @Override
    public String toString() {
        return "caption:"+getCaption()+",processId:"+processId+",commandLine:"+commandLine;
    }

}
