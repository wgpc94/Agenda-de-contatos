package com.welingtongomes.agendadecontatos;

public interface DialogListeners {
    void aplyTexts(String nome, String numero);
    void updatedContact(int id, String nome, String numero);
}
