package com.example.appmantenimientoalumnos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlumnoAdapter adapter;
    private DBAlumnos db;
    private TextView tvVacio;
    private FloatingActionButton fabAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewAlumnos);
        tvVacio = findViewById(R.id.tvVacio);
        fabAgregar = findViewById(R.id.fabAgregar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabAgregar.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, NuevoActivity.class));
        });

        db = new DBAlumnos(this);
        cargarAlumnos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarAlumnos();
    }

    private void cargarAlumnos() {
        List<Alumnos> lista = db.listarAlumnos();
        if (lista.isEmpty()) {
            tvVacio.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvVacio.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new AlumnoAdapter(lista, this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_nuevo) {
            startActivity(new Intent(this, NuevoActivity.class));
            return true;
        } else if (id == R.id.action_acerca) {
            mostrarAcercaDe();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mostrarAcercaDe() {
        new AlertDialog.Builder(this)
                .setTitle("Acerca de")
                .setMessage("Mantenimiento de Alumnos v1.0\nDesarrollado con SQLite\nSeminario IA 501")
                .setPositiveButton("OK", null)
                .show();
    }

    public void confirmarEliminacion(Alumnos alumno) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar")
                .setMessage("¿Eliminar al alumno " + alumno.getNombre() + "?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    db.eliminarAlumno(alumno.getId());
                    Toast.makeText(this, "Alumno eliminado", Toast.LENGTH_SHORT).show();
                    cargarAlumnos();
                })
                .setNegativeButton("No", null)
                .show();
    }
}