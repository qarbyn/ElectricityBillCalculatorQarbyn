package com.example.electricitybillcalculator;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BillDao {
    @Insert
    void insert(Bill bill);

    @Query("SELECT * FROM bills ORDER BY id DESC")
    LiveData<List<Bill>> getAllBills();

    @Query("SELECT * FROM bills WHERE id = :id")
    Bill getBillById(int id);
}