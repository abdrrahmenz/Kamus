package com.expert.andro.kamus.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.expert.andro.kamus.activity.DetailActivity;
import com.expert.andro.kamus.R;
import com.expert.andro.kamus.model.KamusModel;

import java.util.ArrayList;

/**
 * Created by adul on 21/09/17.
 */

public class KamusEngIdAdapter extends RecyclerView.Adapter<KamusEngIdAdapter.EngIdViewHolder> {

    private ArrayList<KamusModel> dataEngId = new ArrayList<>();
    private Context context;
    private LayoutInflater mInfalter;

    public KamusEngIdAdapter(Context context) {
        this.context = context;
        mInfalter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArrayList<KamusModel> getDataEngId() {
        return dataEngId;
    }

    public void setDataEngId(ArrayList<KamusModel> dataEngId) {
        this.dataEngId = dataEngId;
        notifyDataSetChanged();
    }

    @Override
    public EngIdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kamus_row,parent,false);
        return new EngIdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EngIdViewHolder holder, int position) {
        holder.tvName.setText(getDataEngId().get(position).getName());
        holder.tvDesc.setText(getDataEngId().get(position).getDescription());

        holder.tvName.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                KamusModel model = new KamusModel();
                model.setName(getDataEngId().get(position).getName());
                model.setDescription(getDataEngId().get(position).getDescription());

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_KAMUS, model);
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return dataEngId.size();
    }

    public class EngIdViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvDesc;

        public EngIdViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txt_nama);
            tvDesc = itemView.findViewById(R.id.txt_desc);
        }
    }
}
