package com.example.appmantenimientoalumnos;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class NuevoActivity extends AppCompatActivity {

    private TextInputEditText etCodigo, etNombres, etApellidos, etEmail;
    private DBAlumnos db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        // Habilitar el botón de "atrás" en la barra superior
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Nuevo Alumno");
        }

        etCodigo = findViewById(R.id.etCodigo);
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etEmail = findViewById(R.id.etEmail);

        db = new DBAlumnos(this);

        findViewById(R.id.btnGuardar).setOnClickListener(v -> guardarAlumno());
    }

    private void guardarAlumno() {
        String codigo = etCodigo.getText().toString().trim();
        String nombres = etNombres.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        // Validaciones
        if (codigo.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "⚠️ Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "⚠️ Email inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear objeto e insertar
        Alumnos alumno = new Alumnos();
        alumno.setCodigo(codigo);
        alumno.setNombres(nombres);
        alumno.setApellidos(apellidos);
        alumno.setEmail(email);

        long result = db.insertarAlumno(alumno);

        if (result > 0) {
            Toast.makeText(this, "✅ Alumno guardado correctamente", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            // Volver atrás después de 1 segundo
            new android.os.Handler().postDelayed(() -> finish(), 1000);
        } else {
            Toast.makeText(this, "❌ Error al guardar", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        etCodigo.setText("");
        etNombres.setText("");
        etApellidos.setText("");
        etEmail.setText("");
        etCodigo.requestFocus();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}