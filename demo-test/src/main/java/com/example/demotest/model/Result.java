package com.example.demotest.model;

import java.util.Date;

public class Result {

    private Integer id;

    private String type;

    private String model;

    private String is_ok;

    private Date create_time;

    private String sample;

    private String ko;

    private String error;

    private String average;

    private String min;

    private String max;

    private String pct_90th;

    private String pct_95th;

    private String pct_99th;

    private String throughput;

    private String received;

    private String sent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getIs_ok() {
        return is_ok;
    }

    public void setIs_ok(String is_ok) {
        this.is_ok = is_ok;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }


    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getKo() {
        return ko;
    }

    public void setKo(String ko) {
        this.ko = ko;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getPct_90th() {
        return pct_90th;
    }

    public void setPct_90th(String pct_90th) {
        this.pct_90th = pct_90th;
    }

    public String getPct_95th() {
        return pct_95th;
    }

    public void setPct_95th(String pct_95th) {
        this.pct_95th = pct_95th;
    }

    public String getPct_99th() {
        return pct_99th;
    }

    public void setPct_99th(String pct_99th) {
        this.pct_99th = pct_99th;
    }

    public String getThroughput() {
        return throughput;
    }

    public void setThroughput(String throughput) {
        this.throughput = throughput;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }
}
