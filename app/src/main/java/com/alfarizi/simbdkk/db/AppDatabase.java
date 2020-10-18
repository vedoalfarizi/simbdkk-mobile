package com.alfarizi.simbdkk.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.alfarizi.simbdkk.util.DateRoomConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ProposalDb.class}, version = 1, exportSchema = false)
@TypeConverters({DateRoomConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProposalDao proposalDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "simbdkk")
                            .addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
