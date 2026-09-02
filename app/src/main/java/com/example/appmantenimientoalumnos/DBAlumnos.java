package com.example.appmantenimientoalumnos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBAlumnos extends SQLiteOpenHelper {

    private static final String DATABASE_NOMBRE = "escuela.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_ALUMNOS = "t_alumnos";

    public DBAlumnos(Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ALUMNOS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "dni TEXT NOT NULL, " +
                "telefono TEXT NOT NULL, " +
                "correo TEXT, " +
                "direccion TEXT, " +
                "fechaNac TEXT, " +
                "carrera TEXT, " +
                "ciclo TEXT, " +
                "sede TEXT, " +
                "observaciones TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALUMNOS);
        onCreate(db);
    }

    public long insertarAlumno(Alumnos alumno) {
        long id = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", alumno.getNombre());
            values.put("dni", alumno.getDni());
            values.put("telefono", alumno.getTelefono());
            values.put("correo", alumno.getCorreo());
            values.put("direccion", alumno.getDireccion());
            values.put("fechaNac", alumno.getFechaNac());
            values.put("carrera", alumno.getCarrera());
            values.put("ciclo", alumno.getCiclo());
            values.put("sede", alumno.getSede());
            values.put("observaciones", alumno.getObservaciones());

            id = db.insert(TABLE_ALUMNOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    public List<Alumnos> listarAlumnos() {
        List<Alumnos> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_ALUMNOS, null);
            if (cursor.moveToFirst()) {
                do {
                    Alumnos alumno = new Alumnos();
                    alumno.setId(cursor.getInt(0));
                    alumno.setNombre(cursor.getString(1));
                    alumno.setDni(cursor.getString(2));
                    alumno.setTelefono(cursor.getString(3));
                    alumno.setCorreo(cursor.getString(4));
                    alumno.setDireccion(cursor.getString(5));
                    alumno.setFechaNac(cursor.getString(6));
                    alumno.setCarrera(cursor.getString(7));
                    alumno.setCiclo(cursor.getString(8));
                    alumno.setSede(cursor.getString(9));
                    alumno.setObservaciones(cursor.getString(10));

                    lista.add(alumno);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.toString();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lista;
    }

    public boolean eliminarAlumno(int id) {
        boolean correcto = false;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_ALUMNOS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;
    }
}