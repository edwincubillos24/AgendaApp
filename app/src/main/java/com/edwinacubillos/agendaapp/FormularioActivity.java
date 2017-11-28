package com.edwinacubillos.agendaapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FormularioActivity extends MenuActivity {

    private static TextView tCumple;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private EditText eNombre, eCorreo, eTelefono, eLat, eLong;
    private AgendaSQLiteHelper agendaSQLiteHelper;
    private SQLiteDatabase dbContactos;
    private int cant = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_formulario);

        //setContentView(R.layout.activity_main);
        FrameLayout fl = (FrameLayout) findViewById(R.id.frame);
        getLayoutInflater().inflate(R.layout.activity_formulario,fl);

        eNombre = (EditText) findViewById(R.id.eNombre);
        eCorreo = (EditText) findViewById(R.id.eCorreo);
        eTelefono = (EditText) findViewById(R.id.eTelefono);
        eLat = (EditText) findViewById(R.id.eLat);
        eLong = (EditText) findViewById(R.id.eLong);
        tCumple = (TextView) findViewById(R.id.tCumple);

   /*     agendaSQLiteHelper = new AgendaSQLiteHelper(this, "agendaDB", null,1);
        dbContactos = agendaSQLiteHelper.getWritableDatabase();*/

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("contactos");
    }

    public void fechaCumple(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void crear(View view) {

        Contactos contactos = new Contactos(eNombre.getText().toString(),
                eCorreo.getText().toString(),
                eTelefono.getText().toString(),
                Float.parseFloat(eLat.getText().toString()),
                Float.parseFloat(eLong.getText().toString()));
        myRef.child("id" + cant).setValue(contactos);
        cant++;
        clean();


        /*SQLITE

        ContentValues dataBD = new ContentValues();
        dataBD.put("nombre",eNombre.getText().toString());
        dataBD.put("correo",eCorreo.getText().toString());
        dataBD.put("telefono",eTelefono.getText().toString());
        dataBD.put("lat",eLat.getText().toString());
        dataBD.put("long",eLong.getText().toString());
        dataBD.put("cumple",tCumple.getText().toString());

        dbContactos.insert("Contactos",null,dataBD);
        clean();
        //dbContactos.execSQL("");*/
    }

    public void buscar(View view) {
        final String name = eNombre.getText().toString();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("id" + name).exists()) {
                    Contactos contactos = dataSnapshot.child("id" + name).
                            getValue(Contactos.class);
                    eCorreo.setText(contactos.getEmail());
                    eTelefono.setText(contactos.getPhone());
                    eLat.setText(String.valueOf(contactos.getLat()));
                    eLong.setText(String.valueOf(contactos.getLongit()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*SQLite

        Cursor cursor = dbContactos.rawQuery("SELECT * FROM Contactos WHERE " +
                "nombre='"+eNombre.getText().toString()+"'",null);

        if (cursor.moveToFirst()){
            eTelefono.setText(cursor.getString(2));
            eCorreo.setText(cursor.getString(3));
            tCumple.setText(cursor.getString(4));
            eLat.setText(cursor.getString(5));
            eLong.setText(cursor.getString(6));
        }*/
    }

    public void actualizar(View view) {
        final String name = eNombre.getText().toString();

       /*SQLite

        ContentValues dataBD = new ContentValues();
        dataBD.put("correo", eCorreo.getText().toString());
        dataBD.put("telefono", eTelefono.getText().toString());
        dataBD.put("lat", eLat.getText().toString());
        dataBD.put("long", eLong.getText().toString());
        dataBD.put("cumple", tCumple.getText().toString());

        dbContactos.update("Contactos", dataBD,
                "nombre='" + eNombre.getText().toString() + "'", null);
        Toast.makeText(this, "Contacto actualizado", Toast.LENGTH_LONG).show();*/

        myRef = database.getReference("contactos").child("id" + name);
        Map<String, Object> newData = new HashMap<>();
        newData.put("phone", eTelefono.getText().toString());
        newData.put("email", eCorreo.getText().toString());
        newData.put("lat", Float.parseFloat(eLat.getText().toString()));
        newData.put("longit", Float.parseFloat(eLong.getText().toString()));
        myRef.updateChildren(newData);
        clean();
    }

    public void borrar(View view) {
        final String name = eNombre.getText().toString();
        myRef = database.getReference("contactos").child("id" + name);
        myRef.removeValue();
        clean();

        /*SQLite
        dbContactos.delete("Contactos", "nombre='" + eNombre.getText().toString() + "'", null);
        Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_LONG).show();
        clean();
        */
    }

    public void clean() {
        eNombre.setText("");
        eTelefono.setText("");
        eCorreo.setText("");
        eLat.setText("");
        eLong.setText("");
    }

    public void mapa(View view) {
        Intent intent = new Intent(FormularioActivity.this,
                MapsActivity.class);
        intent.putExtra("lat",Float.parseFloat(eLat.getText().toString()));
        intent.putExtra("long", Float.parseFloat(eLong.getText().toString()));
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
            tCumple.setText(year + "/" + (month + 1) + "/" + day);
        }
    }
}
