package com.alfarizi.simbdkk.api;

import com.alfarizi.simbdkk.model.MailVerification;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiMail {
    @FormUrlEncoded
    @POST("mail-verification")
    Call<MailVerification> verifiedMail(@Field("mailId") String mailId);
}
