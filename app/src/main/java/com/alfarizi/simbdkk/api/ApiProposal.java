package com.alfarizi.simbdkk.api;

import com.alfarizi.simbdkk.model.Proposal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiProposal {
    @POST("proposal-track")
    Call<Proposal> trackProposal(
            @Body Proposal proposalId
    );
}
