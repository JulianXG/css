package cn.kalyter.css.model;

import java.util.Date;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public class Repair {
    private int id;
    private int userId;
    private int communityId;
    private House house;
    private int houseId;
    private String code;
    private String reporter;
    private int type;
    private String reporterTel;
    private Date reportTime;
    private Date expectHandleTime;
    private String description;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getReporterTel() {
        return reporterTel;
    }

    public void setReporterTel(String reporterTel) {
        this.reporterTel = reporterTel;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Date getExpectHandleTime() {
        return expectHandleTime;
    }

    public void setExpectHandleTime(Date expectHandleTime) {
        this.expectHandleTime = expectHandleTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
