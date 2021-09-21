package com.welingtongomes.agendadecontatos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements DialogListeners {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(v -> {
            openDialog();
        });
    }

    private void openDialog() {
        DialogCustom  dialogCustom = new DialogCustom();
        dialogCustom.show(getSupportFragmentManager(), null);
    }

    @Override
    public void aplyTexts(String nome, String numero) {
        Toast.makeText(this,"Teste",Toast.LENGTH_SHORT).show();
    }
}