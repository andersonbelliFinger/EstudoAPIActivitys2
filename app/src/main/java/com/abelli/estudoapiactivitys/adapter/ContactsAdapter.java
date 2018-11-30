package com.abelli.estudoapiactivitys.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abelli.estudoapiactivitys.listener.ChatsListener;
import com.abelli.estudoapiactivitys.R;
import com.abelli.estudoapiactivitys.entities.ChatsEntity;
import com.abelli.estudoapiactivitys.viewholder.ContactsViewHolder;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsViewHolder> {

    private Context mContext;
    private List<ChatsEntity> mGuestEntityList;
    private String[] listBackup;
    private ChatsListener mChatsListener;

    public ContactsAdapter(List<ChatsEntity> guestEntityList, ChatsListener chatsListener) {
        this.mGuestEntityList = guestEntityList;
        this.mChatsListener = chatsListener;
    }

    public ContactsAdapter(String[] listBackup, ChatsListener chatsListener) {
        this.listBackup = listBackup;
        this.mChatsListener = chatsListener;
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder guestViewHolder, int position) {
        if (mGuestEntityList == null) {
            ChatsEntity guestEntity = new ChatsEntity();
            String[] teste = listBackup;

            int positionDesc = position + 1;

            if(positionDesc >= listBackup.length){
                positionDesc --;
            }

            if(position % 2 == 0){
                guestEntity.setName(teste[position]);
                guestEntity.setDescription(teste[positionDesc]);
            }else{
                position++;
                positionDesc++;
                guestEntity.setName(teste[position]);
                guestEntity.setDescription(teste[positionDesc]);
            }
            guestViewHolder.bindData(guestEntity, this.mChatsListener);
        } else {
            ChatsEntity guestEntity = mGuestEntityList.get(position);
            guestEntity.setName(guestEntity.getName());
            guestEntity.setDescription(guestEntity.getDescription());
            guestViewHolder.bindData(guestEntity, this.mChatsListener);
        }
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View guestView = LayoutInflater.from(mContext)
                .inflate(R.layout.row_contacts, viewGroup, false);

        return new ContactsViewHolder(guestView, mContext);
    }

    @Override
    public int getItemCount() {
        if (mGuestEntityList == null) {
            int listBackupTam = listBackup.length / 2;
            return listBackupTam;
        } else {
            return mGuestEntityList.size();
        }
    }
}