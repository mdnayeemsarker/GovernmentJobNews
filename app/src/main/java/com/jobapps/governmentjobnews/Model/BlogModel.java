package com.jobapps.governmentjobnews.Model;

public class BlogModel {

//    BlogModel blogModel = new JobsModel(id, title, description, status, slug, created_at, updated_at);

    private final String id;
    private final String title;
    private final String type;
    private final String images;
    private final String description;
    private final String status;
    private final String slug;
    private final String created_at;
    private final String updated_at;

    public BlogModel(String id, String title, String type, String images, String description, String status, String slug, String created_at, String updated_at) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.images = images;
        this.description = description;
        this.status = status;
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

    public String getType() {
        return type;
    }

    public String getImages() {
        return images;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
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

