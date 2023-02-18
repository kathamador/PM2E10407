package com.example.pm2e10407.Configuracion;
//REALIZADO POR:
//Mirian Fatima Ordoñez Amador
//Katherin Nicole Amador Maradiaga
public class Operaciones {
    public static final String NameDatabase = "PM01DB";
    public static final String tablacontactos = "contactos";

    public static final String pais = "pais";
    public static final String id = "id";
    public static final String nombre = "nombre";
    public static final String telefono = "telefono";
    public static final String nota = "nota";
    public static final String foto = "foto";

    public static final String createTableContacto = "CREATE TABLE " + tablacontactos +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre TEXT, telefono INTEGER, nota TEXT, foto BLOB)";

    public static final String dropTableContacto = "DROP TABLE IF EXIST" + tablacontactos;
}