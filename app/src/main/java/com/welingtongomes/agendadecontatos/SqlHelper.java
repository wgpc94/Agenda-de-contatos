package com.welingtongomes.agendadecontatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SqlHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "CONTACTS_DB";
    private static final int DB_VERSION = 1;

    private static SqlHelper sqlHelperInstance;

    static SqlHelper getInstance(Context context){
        if(sqlHelperInstance == null){
            sqlHelperInstance = new SqlHelper(context);
        }
        return sqlHelperInstance;
    }

    private SqlHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE contacts (id INTEGER primary key, name TEXT, number TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("TEST", "onUpgrade shot");
    }

    long addContact(ContactModel contact){
        long id = 0;
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();

            values.put("name", contact.getNome());
            values.put("number", contact.getNumero());
            id = db.insertOrThrow("contacts", null, values);
            db.setTransactionSuccessful();

        }catch (Exception e){
            Log.e("ERROR", "addContact: " + e.getMessage(), e);
        }finally {
            if (db.isOpen()){
                db.endTransaction();
            }
        }
        return id;
    }

    List<ContactModel> getContactList(){
        List<ContactModel> contactList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM contacts  ", null);
        try {
            if (cursor.moveToFirst()){
                do{
                    ContactModel contactModel = new ContactModel();
                    contactModel.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    contactModel.setNome(cursor.getString(cursor.getColumnIndex("name")));
                    contactModel.setNumero(cursor.getString(cursor.getColumnIndex("number")));
                    contactList.add(contactModel);
                }while (cursor.moveToNext());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!= null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return contactList;
    }

    long removeContact(int id){
        long contactId = 0;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            contactId = db.delete("contacts","id = ?",new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

        return contactId;
    }


    long updateContact(int id, String nome, String numero){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        int contactId = 0;

        try {
            ContentValues values = new ContentValues();
            values.put("name", nome);
            values.put("number", numero);

            contactId = db.update("contacts", values, "id = ?", new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

        return contactId;
    }
}
