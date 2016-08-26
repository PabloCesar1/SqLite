package com.facci.proyectopceb;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivityPCEB extends AppCompatActivity {

    DBHelper dbSQLITE;
    EditText Nombre;
    EditText Apellido;
    EditText RecintoElectoral;
    EditText Nacimiento;
    EditText ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_pceb);
        dbSQLITE = new DBHelper(this);
    }

    public void insertarClick(View v){

        Nombre = (EditText) findViewById(R.id.txtNombre);
        Apellido = (EditText) findViewById(R.id.txtApellido);
        RecintoElectoral = (EditText) findViewById(R.id.txtRecintoElectoral);
        Nacimiento = (EditText) findViewById(R.id.txtAnoNacimiento);
        boolean estaInsertado = dbSQLITE.insertar(Nombre.getText().toString()
                 ,Apellido.getText().toString()
                 ,RecintoElectoral.getText().toString(),
                 Integer.parseInt(Nacimiento.getText().toString()));
        if(estaInsertado)
            Toast.makeText(MainActivityPCEB.this,"Agregado Correctamente",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivityPCEB.this,"Error al agregar",Toast.LENGTH_SHORT).show();

    }

    public void verTodosClick(View v){

        Cursor res = dbSQLITE.selectVerTodos();
        if(res.getCount() == 0){
            mostrarMensaje("Error","No se encontraron registros");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            buffer.append("Id : "+res.getString(0)+"\n");
            buffer.append("Nombre : "+res.getString(1)+"\n");
            buffer.append("Apellido : "+res.getString(2)+"\n");
            buffer.append("Recinto : "+res.getString(3)+"\n");
            buffer.append("Año de Nacimiento : "+res.getInt(4)+"\n\n");
        }
        mostrarMensaje("Registros",buffer.toString());
    }
    public void mostrarMensaje(String titulo, String Mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titulo);
        builder.setMessage(Mensaje);
        builder.show();
    }


    public void modificarRegistroClick(View v){

        Nombre = (EditText) findViewById(R.id.txtNombre);
        Apellido = (EditText) findViewById(R.id.txtApellido);
        RecintoElectoral = (EditText) findViewById(R.id.txtRecintoElectoral);
        Nacimiento = (EditText) findViewById(R.id.txtAnoNacimiento);
        ID = (EditText) findViewById(R.id.txtID);

        boolean estaAcutalizado = dbSQLITE.modificarRegistro(ID.getText().toString(), Nombre.getText().toString()
                 ,Apellido.getText().toString()
                 ,RecintoElectoral.getText().toString(),
                Integer.parseInt(Nacimiento.getText().toString()));

        if (estaAcutalizado == true){
            Toast.makeText(MainActivityPCEB.this,"Actualizado Correctamente",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivityPCEB.this,"Error al actualizar",Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarRegistroClick(View v){
        ID = (EditText) findViewById(R.id.txtID);
        Integer registrosEliminados = dbSQLITE.eliminarRegistro(ID.getText().toString());
        if(registrosEliminados > 0 ){
            Toast.makeText(MainActivityPCEB.this,"Registro(s) Eliminado(s)",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivityPCEB.this,"Error al eliminar",Toast.LENGTH_SHORT).show();
        }

    }


}
