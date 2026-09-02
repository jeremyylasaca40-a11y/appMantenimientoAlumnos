package com.example.appmantenimientoalumnos;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NuevoActivity extends AppCompatActivity {

    // Se cambió TextInputEditText por EditText
    private EditText etNombre, etDni, etTelefono, etCorreo, etDireccion,
            etFechaNac, etCarrera, etCiclo, etSede, etObservaciones;
    private DBAlumnos db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Nuevo Alumno");
        }

        etNombre = findViewById(R.id.txtNombre);
        etDni = findViewById(R.id.txtDni);
        etTelefono = findViewById(R.id.txtTelefono);
        etCorreo = findViewById(R.id.txtCorreo);
        etDireccion = findViewById(R.id.txtDireccion);
        etFechaNac = findViewById(R.id.txtFechaNac);
        etCarrera = findViewById(R.id.txtCarrera);
        etCiclo = findViewById(R.id.txtCiclo);
        etSede = findViewById(R.id.txtSede);
        etObservaciones = findViewById(R.id.txtObservaciones);

        db = new DBAlumnos(this);

        findViewById(R.id.btnGuardar).setOnClickListener(v -> guardarAlumno());
    }

    private void guardarAlumno() {
        String nombre = etNombre.getText().toString().trim();
        String dni = etDni.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();
        String fechaNac = etFechaNac.getText().toString().trim();
        String carrera = etCarrera.getText().toString().trim();
        String ciclo = etCiclo.getText().toString().trim();
        String sede = etSede.getText().toString().trim();
        String observaciones = etObservaciones.getText().toString().trim();

        if (nombre.isEmpty() || dni.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(this, "⚠️ Nombre, DNI y Teléfono son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!correo.isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "⚠️ Email inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        Alumnos alumno = new Alumnos();
        alumno.setNombre(nombre);
        alumno.setDni(dni);
        alumno.setTelefono(telefono);
        alumno.setCorreo(correo);
        alumno.setDireccion(direccion);
        alumno.setFechaNac(fechaNac);
        alumno.setCarrera(carrera);
        alumno.setCiclo(ciclo);
        alumno.setSede(sede);
        alumno.setObservaciones(observaciones);

        long result = db.insertarAlumno(alumno);

        if (result > 0) {
            Toast.makeText(this, "✅ Alumno guardado correctamente", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            new android.os.Handler().postDelayed(this::finish, 1000);
        } else {
            Toast.makeText(this, "❌ Error al guardar en la base de datos", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        etNombre.setText("");
        etDni.setText("");
        etTelefono.setText("");
        etCorreo.setText("");
        etDireccion.setText("");
        etFechaNac.setText("");
        etCarrera.setText("");
        etCiclo.setText("");
        etSede.setText("");
        etObservaciones.setText("");
        etNombre.requestFocus();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}