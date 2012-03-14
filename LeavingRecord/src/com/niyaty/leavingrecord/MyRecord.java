package com.niyaty.leavingrecord;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MyRecord implements Serializable {
    final static public String TABLE_NAME = "record";
    final static public String ID = "_id";
    final static public String ARRIVAL = "arrival";
    final static public String LEAVING = "leaving";
    final static public String REST_TIME = "rest_time";
    final static public String DATE = "date";
    final static public String REMARKS = "remarks";
    final static public String HOLIDAY  = "holiday";

    private int id;
    private String arrival;
    private String leaving;
    private String restTime;
    private String date;
    private String remarks;
    private int holiday;

    public MyRecord() {
        this.id = 0;
        this.arrival = null;
        this.leaving = null;
        this.restTime = null;
        this.date = null;
        this.remarks = null;
        this.holiday = 0;
    }

    public boolean isNull() {
        boolean isNull = false;
        if (id == 0) {
            isNull = true;
        }
        return isNull;
    }

    public void clear() {
        id = 0;
        arrival = null;
        leaving = null;
        restTime = null;
        date = null;
        remarks = null;
        holiday = 0;
    }

    /**
     * idを取得します。
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * idを設定します。
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * arrivalを取得します。
     * @return arrival
     */
    public String getArrival() {
        return arrival;
    }

    /**
     * arrivalを設定します。
     * @param arrival arrival
     */
    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    /**
     * leavingを取得します。
     * @return leaving
     */
    public String getLeaving() {
        return leaving;
    }

    /**
     * leavingを設定します。
     * @param leaving leaving
     */
    public void setLeaving(String leaving) {
        this.leaving = leaving;
    }

    /**
     * restTimeを取得します。
     * @return restTime
     */
    public String getRestTime() {
        return restTime;
    }

    /**
     * restTimeを設定します。
     * @param restTime restTime
     */
    public void setRestTime(String restTime) {
        this.restTime = restTime;
    }

    /**
     * dateを取得します。
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * dateを設定します。
     * @param date date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * remarksを取得します。
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * remarksを設定します。
     * @param remarks remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * holidayを取得します。
     * @return holiday
     */
    public int getHoliday() {
        return holiday;
    }

    /**
     * holidayを設定します。
     * @param holiday holiday
     */
    public void setHoliday(int holiday) {
        this.holiday = holiday;
    }
}
