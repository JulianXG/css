package cn.kalyter.css.model;

import java.util.Date;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public class Repair {
    private Long id;
    private String code;
    private String reporter;
    private int type;
    private String reporterTel;
    private Date reportTime;
    private Date expectHandleTime;
    private String description;
    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
