package com.jobapps.governmentjobnews.Model;

public class PrivateJobModel {

    private final String id;
    private final String user_id;
    private final String name;
    private final String company_name;
    private final String vacancy;
    private final String salary;
    private final String context;
    private final String responsibility;
    private final String employment_status;
    private final String education;
    private final String experience;
    private final String additional_requirement;
    private final String location;
    private final String category;
    private final String gender;
    private final String start_date;
    private final String end_date;
    private final String apply_url;
    private final String created_at;
    private final String updated_at;


    public PrivateJobModel(String id, String user_id, String name, String company_name, String vacancy, String salary, String context, String responsibility, String employment_status, String education, String experience, String additional_requirement, String location, String category, String gender, String start_date, String end_date, String apply_url, String created_at, String updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.company_name = company_name;
        this.vacancy = vacancy;
        this.salary = salary;
        this.context = context;
        this.responsibility = responsibility;
        this.employment_status = employment_status;
        this.education = education;
        this.experience = experience;
        this.additional_requirement = additional_requirement;
        this.location = location;
        this.category = category;
        this.gender = gender;
        this.start_date = start_date;
        this.end_date = end_date;
        this.apply_url = apply_url;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getVacancy() {
        return vacancy;
    }

    public String getSalary() {
        return salary;
    }

    public String getContext() {
        return context;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public String getEmployment_status() {
        return employment_status;
    }

    public String getEducation() {
        return education;
    }

    public String getExperience() {
        return experience;
    }

    public String getAdditional_requirement() {
        return additional_requirement;
    }

    public String getLocation() {
        return location;
    }

    public String getCategory() {
        return category;
    }

    public String getGender() {
        return gender;
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

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
