package com.welingtongomes.agendadecontatos;

public interface OnAdapterItemClick {
    void onClick(ContactModel id);
    void onLongClick(int id, int position);
}
