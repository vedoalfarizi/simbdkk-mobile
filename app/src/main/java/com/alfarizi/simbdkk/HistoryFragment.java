package com.alfarizi.simbdkk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alfarizi.simbdkk.api.ApiProposal;
import com.alfarizi.simbdkk.db.ProposalDb;
import com.alfarizi.simbdkk.model.Proposal;
import com.alfarizi.simbdkk.model.TrackProposal;
import com.alfarizi.simbdkk.repository.ProposalRepository;
import com.alfarizi.simbdkk.service.ApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    private ProposalRepository proposalRepository;
    private ProgressDialog progressDialog;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    public HistoryFragment() {
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_histories);
        HistoryAdapter adapter = new HistoryAdapter(getAllProposals());
        adapter.setOnItemClick(new HistoryAdapter.OnItemClick() {
            @Override
            public void click(Proposal p) {
                searchProposal(p.getId());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return view;
    }


    private ArrayList<Proposal> getAllProposals(){
        List<ProposalDb> proposalDbs =  proposalRepository.getListProposal();
        ArrayList<Proposal> proposals = new ArrayList<>();

        for (ProposalDb pro : proposalDbs){
            Proposal proposal = new Proposal(
                    pro.getId(),
                    pro.getTitle(),
                    pro.getStatus(),
                    sdf.format(pro.getUpdatedAt())
            );
            proposals.add(proposal);
        }

        return proposals;
    }

    private void searchProposal(String proposalId){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Memuat...");
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

    private void searchingProposalHandler(Proposal body){
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

        proposalRepository.update(proposal);

        Intent intent = new Intent(getActivity(), ResultTrackActivity.class);
        intent.putExtra("id", body.getId());
        intent.putExtra("title", body.getTitle());
        intent.putExtra("status", body.getStatus());
        intent.putExtra("updatedAt", body.getUpdatedAt());
        startActivity(intent);
    }
}