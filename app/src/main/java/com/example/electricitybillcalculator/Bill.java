package com.example.electricitybillcalculator;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bills")
public class Bill {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String month;
    private double unitsUsed;
    private double totalCharges;
    private double rebatePercentage;
    private double finalCost;

    public Bill() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public double getUnitsUsed() { return unitsUsed; }
    public void setUnitsUsed(double unitsUsed) { this.unitsUsed = unitsUsed; }

    public double getTotalCharges() { return totalCharges; }
    public void setTotalCharges(double totalCharges) { this.totalCharges = totalCharges; }

    public double getRebatePercentage() { return rebatePercentage; }
    public void setRebatePercentage(double rebatePercentage) { this.rebatePercentage = rebatePercentage; }

    public double getFinalCost() { return finalCost; }
    public void setFinalCost(double finalCost) { this.finalCost = finalCost; }
}