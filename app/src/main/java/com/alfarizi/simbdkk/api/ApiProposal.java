package com.alfarizi.simbdkk.api;

import com.alfarizi.simbdkk.model.TrackProposal;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiProposal {
    @FormUrlEncoded
    @POST("proposal-track")
    Call<TrackProposal> trackProposal(@Field("proposalId") String proposalId);
}
