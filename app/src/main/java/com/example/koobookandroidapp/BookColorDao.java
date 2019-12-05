package com.example.koobookandroidapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;


@Dao
public interface BookColorDao {

    @Query("SELECT * FROM Color WHERE book_bookId = :bookId")
    Color getColorsOfBook(int bookId);
}
