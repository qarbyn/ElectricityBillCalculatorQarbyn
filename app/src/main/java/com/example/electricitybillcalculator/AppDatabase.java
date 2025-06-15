package com.example.electricitybillcalculator;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Bill.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BillDao billDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "bill_database")
                    .allowMainThreadQueries() // For simplicity in this assignment
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}