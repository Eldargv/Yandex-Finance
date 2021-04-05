package com.Test.test_app.Adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.Test.test_app.Stock;
import com.Test.test_app.R;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DecimalFormat;
import java.util.List;

public class StocksAdapter extends RecyclerView.Adapter<StocksAdapter.StockViewHolder> {

    private SortedList<Stock> StockList;
    private OnStarListener onStarListener;

    public StocksAdapter(OnStarListener onStarListener) {
        this.onStarListener = onStarListener;
        StockList = new SortedList<>(Stock.class, new SortedList.Callback<Stock>() {
            @Override
            public int compare(Stock o1, Stock o2) {
                return 0;
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Stock oldItem, Stock newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Stock item1, Stock item2) {
                return item1.getTicker().equals(item2.getTicker());
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                Log.i("TAG", "try to remove from recycler ");
                notifyItemRangeRemoved(position, count);
                notifyItemRangeChanged(position, StockList.size() - 1 - count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_stock, parent, false);

        return new StockViewHolder(view, onStarListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        Stock company = StockList.get(position);
        int ResId = R.drawable.background_shape_blue;
        if (position % 2 == 1) {
            ResId = R.drawable.background_shape_white;
        }
        holder.bind(
                company.getTicker(),
                company.getName(),
                company.getLogoUrl(),
                company.getCurrency(),
                company.getCurrentPrice(),
                company.getDifferent(),
                company.getStarMode(),
                ResId
        );
    }

    @Override
    public int getItemCount() {
        return StockList.size();
    }

    public void setStockList(List<Stock> StockList) {
        this.StockList.replaceAll(StockList);
    }

    public Stock getItemAt(int pos) {
        return StockList.get(pos);
    }

    public void RemoveItemAt(int pos) {
        StockList.removeItemAt(pos);
    }

    static class StockViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView StockName, StockTick, Curr, Diff;
        ImageView Logo, Star;
        ConstraintLayout Background;
        ImageLoader imageLoader;
        OnStarListener onStarListener;

        public StockViewHolder(View ItemView, OnStarListener onStarListener1) {
            super(ItemView);
            onStarListener = onStarListener1;
            Background = ItemView.findViewById(R.id.stock_background);
            StockName = ItemView.findViewById(R.id.stock_name);
            StockTick = ItemView.findViewById(R.id.stock_tick);
            Curr = ItemView.findViewById(R.id.stock_current_price);
            Diff = ItemView.findViewById(R.id.stock_different_price);
            Logo = ItemView.findViewById(R.id.stock_logo);
            Star = ItemView.findViewById(R.id.stock_star);
            imageLoader = ImageLoader.getInstance();
            Star.setOnClickListener(this);
        }

        @SuppressLint("ResourceAsColor")
        void bind(String tick, String name, String logoUrl, String Currency, Double currentPrice, Double differentPrice, int starMode, Integer resId) {
            StockTick.setText(tick);
            StockName.setText(name);
            Background.setBackgroundResource(resId);
            Star.setImageResource(starMode);

            String sign = "+";
            Diff.setTextColor(Color.parseColor("#FF24B25D"));
            if (differentPrice < 0) {
                sign = "-";
                Diff.setTextColor(Color.parseColor("#FFB22424"));
            }

            // работаем с ценой
            differentPrice = Math.abs(differentPrice);
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            Curr.setText(Currency + decimalFormat.format(currentPrice));
            Diff.setText(
                    sign + Currency + decimalFormat.format(differentPrice) +
                            "(" + decimalFormat.format(100 * differentPrice / (differentPrice + currentPrice)) + "%)"
            );

            // Работаем с картинкой
            Glide.with(itemView.getContext())
                    .load(logoUrl)
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(Logo);
        }

        @Override
        public void onClick(View v) {
            onStarListener.onStarClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnStarListener{
        void onStarClick(int position);
    }
}
