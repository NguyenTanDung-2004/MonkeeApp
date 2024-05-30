package com.example.monkeeapp.Giang.ExpenseAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monkeeapp.Dat.util.HandleBug;
import com.example.monkeeapp.Dat_EditExpenseActivity;
import com.example.monkeeapp.Giang.ExpenseView.ExpenseView;
import com.example.monkeeapp.HomeFragment;
import com.example.monkeeapp.R;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.Viewholder> {

    Context context;
    List<ExpenseView> dataList;
    private OnItemClickListener listener;
    public ExpenseAdapter(List<ExpenseView> list) {this.dataList = list;}

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.giang_viewholder_expense, parent, false);
        return new Viewholder(inflate, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.name.setText(dataList.get(position).getCategoryName());
        holder.date.setText(dataList.get(position).getDate());
        holder.note.setText(dataList.get(position).getNote());
        holder.money.setText(dataList.get(position).getMoney());

        String imageUrl = dataList.get(position).getSrc();
        int resourceId = holder.itemView.getContext().getResources().getIdentifier(imageUrl, "drawable", holder.itemView.getContext().getPackageName());
        holder.imgCategory.setImageResource(resourceId);

        if (dataList.get(position).getMoney().contains("+"))
            holder.money.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.Giang_primary));
        if (dataList.get(position).getMoney().contains("-"))
            holder.money.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.Giang_green));

        ExpenseView expenseView = dataList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                HandleBug.expenseView = expenseView;
                Intent intent = new Intent(context, Dat_EditExpenseActivity.class);
                intent.putExtra("expenseId", expenseView.getExpenseId());
                intent.putExtra("expenseDate", expenseView.getDate());
                intent.putExtra("expenseNote", expenseView.getNote());
                intent.putExtra("expenseMoney", expenseView.getMoney());
                HandleBug.image = holder.imgCategory;
                HandleBug.title = holder.name;
                HandleBug.date = holder.date;
                HandleBug.description = holder.note;
                HandleBug.money = holder.money;
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataList != null)
            return dataList.size();
        return 0;
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgCategory;
        TextView name, date, note, money;
        public Viewholder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            imgCategory = itemView.findViewById(R.id.imgCategory);
            name = itemView.findViewById(R.id.txtCategoryName);
            date = itemView.findViewById(R.id.txtDate);
            note = itemView.findViewById(R.id.txtNote);
            money = itemView.findViewById(R.id.txtMoney);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
