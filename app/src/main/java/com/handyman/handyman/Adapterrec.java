package com.handyman.handyman;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapterrec extends RecyclerView.Adapter<Adapterrec.ViewHoleder> {

    private String[] data;

    public Adapterrec(String[] data)
    {
        this.data=data;
    }

    @NonNull
    @Override
    public ViewHoleder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.list_item,viewGroup,false);
        return new ViewHoleder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoleder viewHoleder, int i) {

        String title=data[i];
        String Dis=data[i];

        viewHoleder.tv1.setText(title);
        viewHoleder.tv2.setText(Dis);

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHoleder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView tv1,tv2;

        public ViewHoleder(@NonNull View itemView) {

            super(itemView);

            img=(CircleImageView) itemView.findViewById(R.id.imgview);
            tv1=(TextView) itemView.findViewById(R.id.tv1);
            tv2=(TextView) itemView.findViewById(R.id.tv2);
        }
    }

}