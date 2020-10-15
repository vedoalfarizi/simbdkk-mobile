package com.alfarizi.simbdkk.repository;

import android.app.Application;

import com.alfarizi.simbdkk.db.AppDatabase;
import com.alfarizi.simbdkk.db.ProposalDao;
import com.alfarizi.simbdkk.db.ProposalDb;

public class ProposalRepository {
    private ProposalDao proposalDao;

    public ProposalRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        proposalDao = db.proposalDao();
    }

    public void insert(final ProposalDb proposal){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                proposalDao.insertProposal(proposal);
            }
        });
    }
}
