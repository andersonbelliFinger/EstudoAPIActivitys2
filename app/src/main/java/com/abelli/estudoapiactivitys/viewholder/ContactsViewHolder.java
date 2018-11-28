package com.abelli.estudoapiactivitys.viewholder;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abelli.estudoapiactivitys.ChatsListener;
import com.abelli.estudoapiactivitys.R;
import com.abelli.estudoapiactivitys.entities.ChatsEntity;
import java.util.List;

public class ContactsViewHolder extends RecyclerView.ViewHolder{

    private List<ChatsEntity> mChatsEntities;
    private ChatsEntity mChatsEntity;
    private Context mContext;

    private TextView mTextName;
    private TextView mTextDesc;
    private LinearLayout mLinearRow;

    public ContactsViewHolder(View itemView, Context context) {
        super(itemView);

        mLinearRow = itemView.findViewById(R.id.linear_row);
        mTextName = itemView.findViewById(R.id.txt_name);
        mTextDesc= itemView.findViewById(R.id.txt_desc);
        this.mContext = context;
    }

    public void bindData(final ChatsEntity guestEntity, final ChatsListener listener) {
    //public void bindData(ChatsEntity guestEntity) {
        // Objeto que será clicado
        mTextName.setText(guestEntity.getId());
        mTextDesc.setText(guestEntity.getDescription());

        //Manipulação do objeto clicado
        this.mLinearRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChatClick(guestEntity.getId(),guestEntity.getName(),guestEntity.getDescription());
            }
        });

        this.mLinearRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Confirmar remoção")
                        .setMessage("Deseja remover?")
                        //.setIcon(R.drawable.remove)
                        .setPositiveButton("Sis", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //listener.onChatClick();
                                Toast.makeText(mContext,"Id é esse" + guestEntity.getId(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNeutralButton("Não", null)
                        .show();
                return true;
            }
        });
    }
}
