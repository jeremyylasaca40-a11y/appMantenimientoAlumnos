package com.example.appmantenimientoalumnos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBAlumnos extends SQLiteOpenHelper {

    // Constantes de la base de datos
    private static final String DB_NAME = "db_alumnos";
    private static final int DB_VERSION = 1;

    // Constantes de la tabla
    private static final String TABLE_NAME = "alumnos";
    private static final String COL_ID = "id";
    private static final String COL_CODIGO = "codigo";
    private static final String COL_NOMBRES = "nombres";
    private static final String COL_APELLIDOS = "apellidos";
    private static final String COL_EMAIL = "email";

    public DBAlumnos(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_CODIGO + " TEXT NOT NULL, "
                + COL_NOMBRES + " TEXT NOT NULL, "
                + COL_APELLIDOS + " TEXT NOT NULL, "
                + COL_EMAIL + " TEXT NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // INSERTAR nuevo alumno
    public long insertarAlumno(Alumnos alumno) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CODIGO, alumno.getCodigo());
        values.put(COL_NOMBRES, alumno.getNombres());
        values.put(COL_APELLIDOS, alumno.getApellidos());
        values.put(COL_EMAIL, alumno.getEmail());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    // LISTAR todos los alumnos
    public List<Alumnos> listarAlumnos() {
        List<Alumnos> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_ID + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Alumnos a = new Alumnos();
                a.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                a.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(COL_CODIGO)));
                a.setNombres(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRES)));
                a.setApellidos(cursor.getString(cursor.getColumnIndexOrThrow(COL_APELLIDOS)));
                a.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)));
                lista.add(a);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    // ELIMINAR alumno por ID
    public int eliminarAlumno(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }
}