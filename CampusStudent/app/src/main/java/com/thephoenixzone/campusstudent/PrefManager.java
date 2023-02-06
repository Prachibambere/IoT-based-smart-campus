package com.thephoenixzone.campusstudent;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lincoln on 05/05/16.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences constants
    private static final String IS_HOME_ACTIVE = "IsActivityOpen";
    private static final String IS_NO_INTERNET_ACTIVE = "IsActivityClose";

    private static final String PREF_NAME = "MyPreference";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_USER_LOGIN = "IsUserLogin";
    private static final String USER_NAME = "UserName";
    private static final String USER_MOBILE = "Mobile";
    private static final String USER_EMAIL = "Email";
    private static final String USER_PASSWORD = "Password";
    private static final String EMP_ID = "emp_id";
    private static final String USER_ADDRESS = "User_Address";
    private static final String ZIP_CODE = "Zip_Code";
    private static final String DELIVERY_FEE = "Delivery_Fee";
    private static final String DELIVERY_KM = "Delivery_KM";
    private static final String NOTIFICATION_COUNT = "Notification_count";
    private static final String GENDER = "Gender";
    private static final String PROFILE = "Profile";
    private static final String USER_TYPE="";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setUserType(String userType)
    {
        editor.putString(USER_TYPE, userType);
        editor.commit();
    }
    public String getUserType()
    {
        return  pref.getString(USER_TYPE,"Owner");
    }
    public void setProfile(String profile) {
        editor.putString(PROFILE, profile);
        editor.commit();
    }

    public String getProfile() {
        return pref.getString(PROFILE, "assets/profiles/user.png");
    }


    //set Gender
    public void setGender(String gender) {
        editor.putString(GENDER, gender);
        editor.commit();
    }

    //get gender
    public String getGender() {
        return pref.getString(GENDER, "Please Select");
    }


    public void setNotificationCount(int notificationCount) {
        editor.putInt(NOTIFICATION_COUNT, notificationCount);
        editor.commit();
    }

    public void setIsHomeActive(boolean flag) {
        editor.putBoolean(IS_HOME_ACTIVE, flag);
        editor.commit();
    }

    public boolean getIsHomeActive() {
        return pref.getBoolean(IS_HOME_ACTIVE, false);
    }

    public void setIsNoInternetActive(boolean flag) {
        editor.putBoolean(IS_NO_INTERNET_ACTIVE, flag);
        editor.commit();
    }

    public boolean getIsNoInternet() {
        return pref.getBoolean(IS_NO_INTERNET_ACTIVE, false);
    }

    public int getNotificationCount() {
        return pref.getInt(NOTIFICATION_COUNT, 5);
    }

    public void setDeliveryFee(String fee) {
        editor.putString(DELIVERY_FEE, fee);
        editor.commit();
    }

    public void setDeliveryKm(String deliveryKm) {
        editor.putString(DELIVERY_KM, deliveryKm);
        editor.commit();
    }

    public String getDeliveryKM() {
        return pref.getString(DELIVERY_KM, "5");
    }

    public String getDeliveryFee() {
        return pref.getString(DELIVERY_FEE, "15");
    }

    public void setZipCode(String zipCode) {
        editor.putString(ZIP_CODE, zipCode);
        editor.commit();
    }

    public String getZipCode() {
        return pref.getString(ZIP_CODE, null);
    }

    public void setUserName(String userName) {
        editor.putString(USER_NAME, userName);
        editor.commit();
    }


    public void setUserAddress(String address) {
        editor.putString(USER_ADDRESS, address);
        editor.commit();
    }

    public String getUserAddress() {
        return pref.getString(USER_ADDRESS, "");
    }

    public String getUserName() {
        return pref.getString(USER_NAME, "");
    }


    public void setUserEmail(String email) {
        editor.putString(USER_EMAIL, email);
        editor.commit();
    }

    public String getUserEmail() {
        return pref.getString(USER_EMAIL, "");
    }

    public void setUserMobile(String mobile) {
        editor.putString(USER_MOBILE, mobile);
        editor.commit();
    }

    public String getUserMobile() {
        return pref.getString(USER_MOBILE, "");
    }

    public void setEmpId(String empId) {
        editor.putString(EMP_ID, empId);
        editor.commit();
    }

    public void setUserPassword(String password) {
        editor.putString(USER_PASSWORD, password);
        editor.commit();
    }

    public String getUserPassword() {
        return pref.getString(USER_PASSWORD, "");
    }

    public String getEmpId() {
        return pref.getString(EMP_ID, "");
    }

    public void setIsUserLogin(boolean isUserLogin) {
        editor.putBoolean(IS_USER_LOGIN, isUserLogin);
        editor.commit();
    }

    public boolean isUserLogin() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}
