package com.jobapps.governmentjobnews.Model;

public class JobsModel {

    private final String id;
    private final String name;
    private final String company_name;
    private final String start_date;
    private final String end_date;
    private final String apply_url;
    private final String description;
    private final String created_at;
    private final String updated_at;

    public JobsModel(String id, String name, String company_name, String start_date, String end_date, String apply_url, String description, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.company_name = company_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.apply_url = apply_url;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getApply_url() {
        return apply_url;
    }

    public String getDescription() {
        return description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
