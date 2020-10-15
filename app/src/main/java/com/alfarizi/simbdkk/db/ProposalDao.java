package com.alfarizi.simbdkk.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface ProposalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProposal(ProposalDb proposalDb);
}
