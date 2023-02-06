package com.thephoenixzone.campusstudent;

public class ServerUtility {

    public static String Server_URL = "http://192.168.43.115:5000/";
    public static double latitude = 18.151490, longitude = 74.576135;
    public static boolean flag_Activity = false;


    public static String url_get_user_info() {
        return ServerUtility.Server_URL + "StudentLogin";
    }
    public static String url_get_attendance_info() {
        return ServerUtility.Server_URL + "MyAttendance";
    }
    public static String url_get_book_info() {
        return ServerUtility.Server_URL + "MyBooks";
    }
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_ALERT = "Alert!";

}
