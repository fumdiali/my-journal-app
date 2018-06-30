package com.digirealis.thejournal.database.model;

/**
 * Created by fum on 6/29/18.
 */

public class Entry {

    public static final String TABLE_NAME = "entries";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ENTRY = "entry";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String entry;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_ENTRY + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Entry() { /*default constructor */}

    public Entry(int id, String entry, String timestamp) {
        this.id = id;
        this.entry = entry;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}// end of class
