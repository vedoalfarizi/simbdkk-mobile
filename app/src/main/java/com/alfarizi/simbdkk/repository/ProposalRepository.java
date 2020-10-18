package com.alfarizi.simbdkk.repository;

import android.app.Application;

import com.alfarizi.simbdkk.db.AppDatabase;
import com.alfarizi.simbdkk.db.ProposalDao;
import com.alfarizi.simbdkk.db.ProposalDb;

import java.util.List;

public class ProposalRepository {
    private ProposalDao proposalDao;
    private List<ProposalDb> proposalDbs;

    public ProposalRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        proposalDao = db.proposalDao();
        proposalDbs = proposalDao.getProposals();
    }

    public void insert(final ProposalDb proposal){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                proposalDao.insertProposal(proposal);
            }
        });
    }

    public List<ProposalDb> getListProposal(){
        return proposalDbs;
    }
}
