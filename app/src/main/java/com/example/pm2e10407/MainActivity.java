package com.example.pm2e10407;
//REALIZADO POR:
//Mirian Fatima Ordoñez Amador
//Katherin Nicole Amador Maradiaga

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pm2e10407.Configuracion.Operaciones;
import com.example.pm2e10407.Configuracion.SQLiteConexion;
import com.hbb20.CountryCodePicker;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity
{
    SQLiteConexion conexion = new SQLiteConexion(this, Operaciones.NameDatabase,null,1);
    SQLiteDatabase db;

    EditText nombre, telefono, nota;
    ImageView foto;
    Button btnSelecFoto,btnTake,btnguardar,btnlistaC;

    static final int PETICION_ACCESO_CAM = 100;
    static final int TAKE_PIC_REQUEST = 101;
    Bitmap imagen;
    CountryCodePicker ccp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = (EditText) findViewById(R.id.txtNombre);
        telefono = (EditText) findViewById(R.id.txttelefono);
        nota = (EditText) findViewById(R.id.txtNota);
        foto = (ImageView) findViewById(R.id.imagen);

        btnSelecFoto = (Button) findViewById(R.id.btnSelecFoto);
        btnTake = (Button) findViewById(R.id.btntake);
        btnguardar = (Button) findViewById(R.id.btnGuardar);
        btnlistaC = (Button) findViewById(R.id.btnListaC);

        btnlistaC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityListaContacto.class);
                startActivity(intent);
            }
        });

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    validaciones();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Debe de tomarse una foto ",Toast.LENGTH_LONG).show();
                }

            }
        });

        btnSelecFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirGaleria();
            }
        });
        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos();
            }
        });
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicacion"),10);
    }
    private void permisos() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},PETICION_ACCESO_CAM);
        }else{
            tomarFoto();
        }
    }
    private void tomarFoto() {
        Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takepic.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takepic,TAKE_PIC_REQUEST);
        }
    }
    protected void onActivityResult(int requescode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requescode, resultCode, data);

        if(requescode == TAKE_PIC_REQUEST && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            imagen = (Bitmap) extras.get("data");
            foto.setImageBitmap(imagen);
        }else if (resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            foto.setImageURI(imageUri);
        }

    }

    private void validaciones() {
        if (nombre.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), " Alerta! Debe de escribir un nombre." ,Toast.LENGTH_LONG).show();
        }else if (telefono.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), " Alerta! Debe de escribir un telefono." ,Toast.LENGTH_LONG).show();
        }else if (nota.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Alerta! Debe de escribir una nota." ,Toast.LENGTH_LONG).show();
        }else{
            guardarContactos(imagen);
        }
    }
    private void guardarContactos(Bitmap bitmap) {
        db = conexion.getWritableDatabase();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] ArrayFoto  = stream.toByteArray();

        ContentValues valores = new ContentValues();

        valores.put(Operaciones.nombre, nombre.getText().toString());
        valores.put(Operaciones.telefono, telefono.getText().toString());
        valores.put(Operaciones.nota, nota.getText().toString());
        valores.put(String.valueOf(Operaciones.foto),ArrayFoto);

        Long resultado = db.insert(Operaciones.tablacontactos, Operaciones.id, valores);

        Toast.makeText(getApplicationContext(), "Contacto guardado correctamente, ID: " + resultado.toString()
                ,Toast.LENGTH_LONG).show();
        db.close();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}