package com.jobapps.governmentjobnews.Model;

public class JobsModel {

    private final String id;
    private final String title;
    private final String company_name;
    private final String description;
    private final String career_level;
    private final String expiry_date;
    private final String is_active;
    private final String slug;
    private final String created_at;
    private final String updated_at;

    public JobsModel(String id, String title, String company_name, String description, String career_level, String expiry_date, String is_active, String slug, String created_at, String updated_at) {
        this.id = id;
        this.title = title;
        this.company_name = company_name;
        this.description = description;
        this.career_level = career_level;
        this.expiry_date = expiry_date;
        this.is_active = is_active;
        this.slug = slug;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getDescription() {
        return description;
    }

    public String getCareer_level() {
        return career_level;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getSlug() {
        return slug;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
