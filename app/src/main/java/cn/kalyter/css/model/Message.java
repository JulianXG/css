package cn.kalyter.css.model;

import java.util.Date;

/**
 * Created by Kalyter on 2017-4-10 0010.
 */

public class Message {

    private int id;
    private int communityId;
    private String content;
    private User user;
    private int userId;
    private boolean isTop;
    private Date postTime;
    private String source;
    private Date topEndTime;
    private Date topStartTime;

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(boolean isTop) {
        this.isTop = isTop;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getTopEndTime() {
        return topEndTime;
    }

    public void setTopEndTime(Date topEndTime) {
        this.topEndTime = topEndTime;
    }

    public Date getTopStartTime() {
        return topStartTime;
    }

    public void setTopStartTime(Date topStartTime) {
        this.topStartTime = topStartTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
