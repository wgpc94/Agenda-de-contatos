package com.welingtongomes.agendadecontatos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogListeners {

    private MainAdapter adapter;
    private SqlHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvMain = findViewById(R.id.rv_main);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter();
        db = SqlHelper.getInstance(this);
        searchForContacts();
        rvMain.setAdapter(adapter);



        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(v -> openDialog());
    }

    private void openDialog() {
        DialogCustom  dialogCustom = new DialogCustom();
        dialogCustom.show(getSupportFragmentManager(), null);
    }

    @Override
    public void aplyTexts(String nome, String numero) {
        if (valido(nome, numero)){
            new Thread(()->{
                long id = db.addContact(new ContactModel(nome, numero));
                runOnUiThread(()->{
                    if(id>0){
                        Toast.makeText(this, "Registro adicionado com sucesso", Toast.LENGTH_SHORT).show();
                        searchForContacts();
                    }
                });
            }).start();
        }
    }

    private void searchForContacts() {
        new Thread(() -> {
            List<ContactModel> list = db.getContactList();
            runOnUiThread(() -> {
                adapter.setList(list);
            });
        }).start();
    }

    private boolean valido(String nome, String numero) {
        boolean verifica = true;
        if (nome.isEmpty()){
            Toast.makeText(this,"NOME DEVE SER PRENCHIDO",Toast.LENGTH_SHORT).show();
            verifica = false;
        }
        if (numero.isEmpty() && numero.length()< 9 && numero.length() <8){
            Toast.makeText(this,"NUMERO DEVE TER NO MINIMO OITO NUMEROS",Toast.LENGTH_SHORT).show();
            verifica = false;
        }
        return verifica;
    }

   public static class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

       private List<ContactModel> list;

       public MainAdapter() {
       }

       public void setList(List<ContactModel> list) {
           this.list = list;
           notifyDataSetChanged();
       }

       @NonNull
       @Override
       public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
           return new MainViewHolder(v);
       }

       @Override
       public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
           ContactModel item = list.get(position);
           holder.bind(item);
       }

       @Override
       public int getItemCount() {
           return list.size();
       }

       public static class MainViewHolder extends RecyclerView.ViewHolder{

            public MainViewHolder(@NonNull View itemView) {
                super(itemView);
            }

           public void bind(ContactModel currentItem) {
               TextView nome = itemView.findViewById(R.id.nomeTv);
               TextView numero = itemView.findViewById(R.id.numeroTv);

               nome.setText(currentItem.getNome());
               numero.setText(currentItem.getNumero());
           }
        }


   }

}