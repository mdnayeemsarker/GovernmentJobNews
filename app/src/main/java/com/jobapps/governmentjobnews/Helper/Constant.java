package com.jobapps.governmentjobnews.Helper;

public class Constant {

    //**************URL***************

    //Admin panel url
    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";
    public static final String DOMAIN = "governmentjobnews.com.bd/";

//    public static final String BASE_URL_SD = HTTP + SUB_DOMAIN + DOMAIN;
    public static final String BASE_URL_SD_S = HTTPS + DOMAIN;

    //**************API***************
    public static final String CONTACT_US_URL = BASE_URL_SD_S + "pages/contact-us";
    public static final String ABOUT_US_URL = BASE_URL_SD_S + "pages/about-us";
    public static final String PRIVACY_US_URL = BASE_URL_SD_S + "pages/privacy";
    public static final String TERMS_CON_URL = BASE_URL_SD_S + "pages/terms-condition";
    public static final String APPLICATION_HELPER_URL = "http://forms.mygov.bd/site/view/category_content/%E0%A6%9A%E0%A6%BE%E0%A6%95%E0%A7%81%E0%A6%B0%E0%A6%BF%20%E0%A6%B8%E0%A6%82%E0%A6%95%E0%A7%8D%E0%A6%B0%E0%A6%BE%E0%A6%A8%E0%A7%8D%E0%A6%A4";

    public static final String NEWSPAPER_URL = BASE_URL_SD_S + "public/news_paper/";

    public static final String FORGET_URL = "https://governmentjobnews.com.bd/password/reset";
    public static final String API_PATH = BASE_URL_SD_S + "api/";
    public static final String JOB_LIST_URL = API_PATH + "jobs/list";
    public static final String SHORT_BY_DATE_URL = API_PATH + "jobs/orderBy/date";
    public static final String JOB_DETAILS_URL = API_PATH + "jobs/details/";
    public static final String FCM_UPDATE_URL = API_PATH + "profile/update/fcm";
    public static final String FAVORITE_ADD_URL = BASE_URL_SD_S + "api/jobs/favourite/add/";
    public static final String FAVORITE_REMOVE_URL = BASE_URL_SD_S + "api/jobs/favourite/remove/";

    //**************parameters***************
    public static final String ACCEPT = "Accept";
    public static final String APPLICATIONJSON = "application/json";

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String FCM_TOKEN = "token";

    public static final String API_KEY = "API_KEY";
    public static final String API_KEY_VAL = "key";
    public static final String API_SECRET = "API_SECRET";
    public static final String API_SECRET_VAL = "pass";

    public static final String IS_LOGIN = "is_login";
    public static final String IS_RATING = "is_rating";
    public static final String IS_ALERT = "is_alert";
    public static final String IS_ALERT_NET = "is_alert_net";
    public static final String IS_ALERT_VPN = "is_alert_vpn";

    public static final String ABMN = "abmn";
    public static final String ABMN_TYPE = "abmn_type";

    public static final String ABMN_LOGIN = "login";
    public static final String ABMN_REGISTER = "register";

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String MIDDLE_NAME = "middle_name";

    public static final String ERROR = "error";
    public static final String STATUS = "status";
    public static final String SUCCESS = "success";
    public static final String MESSAGE = "message";
    public static final String TOKEN = "token";
    public static final String DATA = "data";
    public static final String ID = "id";
    public static final String EXPIRY_DATE = "expiry_date";
    public static final String CAREER_LEVEL_ID = "career_level_id";
    public static final String IS_ACTIVE = "is_active";
    public static final String IMAGES = "images";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String JOBS = "jobs";
    public static final String BLOGS = "blogs";
    public static final String NOTICES = "notices";
    public static final String JOB = "job";
    public static final String COMPANY_NAME = "company_name";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String SLUG = "slug";
    public static final String APPLY_URL = "apply_url";
}
