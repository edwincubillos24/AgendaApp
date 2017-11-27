package com.edwinacubillos.agendaapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class FormularioActivity extends Activity {

    private EditText eNombre, eCorreo, eTelefono, eLat, eLong;
    private static TextView tCumple;
    private AgendaSQLiteHelper agendaSQLiteHelper;
    private SQLiteDatabase dbContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        eNombre = (EditText) findViewById(R.id.eNombre);
        eCorreo = (EditText) findViewById(R.id.eCorreo);
        eTelefono = (EditText) findViewById(R.id.eTelefono);
        eLat = (EditText) findViewById(R.id.eLat);
        eLong = (EditText) findViewById(R.id.eLong);
        tCumple = (TextView) findViewById(R.id.tCumple);

        agendaSQLiteHelper = new AgendaSQLiteHelper(this, "agendaDB", null,1);
        dbContactos = agendaSQLiteHelper.getWritableDatabase();
    }

    public void fechaCumple(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void crear(View view) {

        ContentValues dataBD = new ContentValues();
        dataBD.put("nombre",eNombre.getText().toString());
        dataBD.put("correo",eCorreo.getText().toString());
        dataBD.put("telefono",eTelefono.getText().toString());
        dataBD.put("lat",eLat.getText().toString());
        dataBD.put("long",eLong.getText().toString());
        dataBD.put("cumple",tCumple.getText().toString());

        dbContactos.insert("contactos",null,dataBD);
        clean();
        //dbContactos.execSQL("");
    }

    public void buscar(View view) {
        Cursor cursor = dbContactos.rawQuery("SELECT * FROM contactos WHERE " +
                "nombre='"+eNombre.getText().toString()+"'",null);

        if (cursor.moveToFirst()){
            eTelefono.setText(cursor.getString(2));
            eCorreo.setText(cursor.getString(3));
            tCumple.setText(cursor.getString(4));
            eLat.setText(cursor.getString(5));
            eLong.setText(cursor.getString(6));
        }
    }

    public void actualizar(View view) {
        ContentValues dataBD = new ContentValues();
        dataBD.put("correo",eCorreo.getText().toString());
        dataBD.put("telefono",eTelefono.getText().toString());
        dataBD.put("lat",eLat.getText().toString());
        dataBD.put("long",eLong.getText().toString());
        dataBD.put("cumple",tCumple.getText().toString());

        dbContactos.update("contactos",dataBD,
                "nombre='"+eNombre.getText().toString()+"'",null);
        Toast.makeText(this, "Contacto actualizado",Toast.LENGTH_LONG).show();
        clean();
    }

    public void borrar(View view) {
        dbContactos.delete("contactos", "nombre='"+eNombre.getText().toString()+"'",null);
        Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_LONG).show();
        clean();
    }

    public void clean(){
        eNombre.setText("");
        eTelefono.setText("");
        eCorreo.setText("");
        eLat.setText("");
        eLong.setText("");


    }

    public void mapa(View view) {
        Intent intent = new Intent(FormularioActivity.this,
                MapsActivity.class);
        startActivity(intent);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            tCumple.setText(year+"/"+(month+1)+"/"+day);
        }
    }



}
