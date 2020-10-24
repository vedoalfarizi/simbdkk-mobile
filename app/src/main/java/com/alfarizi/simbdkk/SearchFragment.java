package com.alfarizi.simbdkk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alfarizi.simbdkk.api.ApiProposal;
import com.alfarizi.simbdkk.databinding.FragmentSearchBinding;
import com.alfarizi.simbdkk.db.ProposalDb;
import com.alfarizi.simbdkk.model.TrackProposal;
import com.alfarizi.simbdkk.repository.ProposalRepository;
import com.alfarizi.simbdkk.service.ApiClient;
import com.alfarizi.simbdkk.model.Proposal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private String proposalId;
    private ProgressDialog progressDialog;
    private ProposalRepository proposalRepository;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        proposalRepository = new ProposalRepository(getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proposalId = Objects.requireNonNull(binding.proposalId.getText()).toString();
                if(proposalId.equals("")){
                    Toast.makeText(getContext(), "Proposal ID tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Mencari...");
                    progressDialog.show();
                    ApiProposal apiProposal = ApiClient.getRetrofitInstance().create(ApiProposal.class);
                    Call<TrackProposal> trackProposalCall = apiProposal.trackProposal(proposalId);
                    trackProposalCall.enqueue(new Callback<TrackProposal>() {
                        @Override
                        public void onResponse(Call<TrackProposal> call, Response<TrackProposal> response) {
                            progressDialog.dismiss();
                            if(response.isSuccessful()){
                                searchingProposalHandler(response.body().getProposal());
                            }else{
                                try {
                                    String error = response.errorBody().string();
                                    Log.d("error message", error);

                                    JSONObject errObj = new JSONObject(error);

                                    String message = errObj.getString("message");
                                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                } catch (IOException | JSONException e) {
                                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<TrackProposal> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Searching fail", Toast.LENGTH_SHORT).show();
                            Log.e("onFailure", t.toString());
                        }
                    });
                }
            }
        });

        return view;
    }

    private void searchingProposalHandler(Proposal body){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        ProposalDb proposal = null;
        try {
            proposal = new ProposalDb(
                    body.getId(),
                    body.getTitle(),
                    body.getStatus(),
                    new Date(sdf.parse(body.getUpdatedAt()).getTime())
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        proposalRepository.insert(proposal);

        Intent intent = new Intent(getActivity(), ResultTrackActivity.class);
        intent.putExtra("id", body.getId());
        intent.putExtra("title", body.getTitle());
        intent.putExtra("status", body.getStatus());
        intent.putExtra("updatedAt", body.getUpdatedAt());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}