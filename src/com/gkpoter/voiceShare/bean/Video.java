package com.gkpoter.voiceShare.bean;

/**
 * Created by dy on 2016/10/26.
 */
public class Video {

    private Integer VideoId;
    private Integer UserId;
    private String ImagePath;
    private String VideoPath;
    private String VideoYearTop;
    private String VideoMonthTop;
    private String StarState;
    private String UpTime;
    private String VideoInformation;

    public Integer getVideoId() {
        return VideoId;
    }

    public void setVideoId(Integer videoId) {
        VideoId = videoId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getStarState() {
        return StarState;
    }

    public void setStarState(String starState) {
        StarState = starState;
    }

    public String getUpTime() {
        return UpTime;
    }

    public void setUpTime(String upTime) {
        UpTime = upTime;
    }

    public String getVideoInformation() {
        return VideoInformation;
    }

    public void setVideoInformation(String videoInformation) {
        VideoInformation = videoInformation;
    }

    public String getVideoMonthTop() {
        return VideoMonthTop;
    }

    public void setVideoMonthTop(String videoMonthTop) {
        VideoMonthTop = videoMonthTop;
    }

    public String getVideoPath() {
        return VideoPath;
    }

    public void setVideoPath(String videoPath) {
        VideoPath = videoPath;
    }

    public String getVideoYearTop() {
        return VideoYearTop;
    }

    public void setVideoYearTop(String videoYearTop) {
        VideoYearTop = videoYearTop;
    }
}
