package com.alfarizi.simbdkk.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProposalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProposal(ProposalDb proposalDb);

    @Query("SELECT * FROM proposals ORDER BY accessed_at ASC LIMIT 5")
    List<ProposalDb> getProposals();
}
