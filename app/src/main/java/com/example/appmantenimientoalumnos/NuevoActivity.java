package com.example.appmantenimientoalumnos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NuevoActivity extends AppCompatActivity {

    private EditText etNombre, etDni, etTelefono, etCorreo, etDireccion,
            etFechaNac, etCarrera, etCiclo, etSede, etObservaciones;
    private Button btnGuardar;
    private DBAlumnos db;

    private int alumnoId = -1; // -1 indica que es un registro nuevo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

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
        btnGuardar = findViewById(R.id.btnGuardar);

        db = new DBAlumnos(this);

        // Verificar si vienen datos para EDICIÓN
        if (getIntent().hasExtra("ID")) {
            alumnoId = getIntent().getIntExtra("ID", -1);
            etNombre.setText(getIntent().getStringExtra("NOMBRE"));
            etDni.setText(getIntent().getStringExtra("DNI"));
            etTelefono.setText(getIntent().getStringExtra("TELEFONO"));
            etCorreo.setText(getIntent().getStringExtra("CORREO"));
            etDireccion.setText(getIntent().getStringExtra("DIRECCION"));
            etFechaNac.setText(getIntent().getStringExtra("FECHA_NAC"));
            etCarrera.setText(getIntent().getStringExtra("CARRERA"));
            etCiclo.setText(getIntent().getStringExtra("CICLO"));
            etSede.setText(getIntent().getStringExtra("SEDE"));
            etObservaciones.setText(getIntent().getStringExtra("OBSERVACIONES"));

            if (btnGuardar != null) {
                btnGuardar.setText("Actualizar");
            }

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("Editar Alumno");
            }
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("Nuevo Alumno");
            }
        }

        btnGuardar.setOnClickListener(v -> guardarAlumno());
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

        if (alumnoId != -1) {
            // MODO EDICIÓN
            alumno.setId(alumnoId);
            int filas = db.actualizarAlumno(alumno);
            if (filas > 0) {
                Toast.makeText(this, "✅ Alumno actualizado correctamente", Toast.LENGTH_SHORT).show();
                new android.os.Handler().postDelayed(this::finish, 800);
            } else {
                Toast.makeText(this, "❌ Error al actualizar en la base de datos", Toast.LENGTH_SHORT).show();
            }
        } else {
            // MODO INSERCIÓN
            long result = db.insertarAlumno(alumno);
            if (result > 0) {
                Toast.makeText(this, "✅ Alumno guardado correctamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
                new android.os.Handler().postDelayed(this::finish, 800);
            } else {
                Toast.makeText(this, "❌ Error al guardar en la base de datos", Toast.LENGTH_SHORT).show();
            }
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