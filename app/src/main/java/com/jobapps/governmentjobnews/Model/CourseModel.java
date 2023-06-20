package com.jobapps.governmentjobnews.Model;

public class CourseModel {
    private final String id;
    private final String name;
    private final String duration;
    private final String fee;
    private final String created_at;
    private final String updated_at;

    public CourseModel(String id, String name, String duration, String fee, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.fee = fee;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public String getFee() {
        return fee;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
