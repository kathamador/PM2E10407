package com.example.pm2e10407;
//REALIZADO POR:
//Mirian Fatima Ordoñez Amador
//Katherin Nicole Amador Maradiaga

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pm2e10407.Configuracion.Operaciones;
import com.example.pm2e10407.Configuracion.SQLiteConexion;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;


public class ActivityActualizarContacto extends AppCompatActivity {

    SQLiteConexion conexion = new SQLiteConexion(this, Operaciones.NameDatabase,null,1);

    Button btnatras,btnEditar;
    EditText codigo, nombrecompleto, telefono, nota;
    CountryCodePicker codigoPais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_contacto);
        btnatras = (Button) findViewById(R.id.btnatras);
        codigo = (EditText)findViewById(R.id.txtActCodigo);
        nombrecompleto = (EditText)findViewById(R.id.txtActNombre);
        telefono = (EditText)findViewById(R.id.txtActTelefono);
        nota = (EditText)findViewById(R.id.txtActNota);
        codigoPais = (CountryCodePicker)findViewById(R.id.ccp1);
        btnEditar = (Button) findViewById(R.id.btnEditar);

        codigo.setText(getIntent().getStringExtra("codigo"));
        nombrecompleto.setText(getIntent().getStringExtra("nombre"));
        telefono.setText(getIntent().getStringExtra("numero"));
        nota.setText(getIntent().getStringExtra("nota"));


        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityListaContacto.class);
                startActivity(intent);
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditarContacto();
            }
        });
    }
    private void EditarContacto() {

        SQLiteDatabase db = conexion.getWritableDatabase();

        String ObjCodigo = codigo.getText().toString();

        ContentValues valores = new ContentValues();

        valores.put(Operaciones.nombre, nombrecompleto.getText().toString());
        valores.put(Operaciones.telefono, telefono.getText().toString());
        valores.put(Operaciones.nota, nota.getText().toString());

        try {
            db.update(Operaciones.tablacontactos,valores, Operaciones.id +" = "+ ObjCodigo, null);
            db.close();
            Toast.makeText(getApplicationContext(),"Se actualizo correctamente", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ActivityListaContacto.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();


        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"No se actualizo", Toast.LENGTH_SHORT).show();
        }
    }
}