package com.dsterwz.dbtest_legend.views;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dsterwz.dbtest_legend.MainActivity;
import com.dsterwz.dbtest_legend.OneItemActivity;
import com.dsterwz.dbtest_legend.R;
import com.dsterwz.dbtest_legend.models.Dish;

import java.util.ArrayList;
import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishHolder> {
    private List<Dish> dishes = new ArrayList<>();
    private List<Dish> dishesFull = new ArrayList<>();
    private OneItemClickListener listener;
    private int mExpandedPosition = -1;
    private int previousExpandedPosition = -1;

    public interface OneItemClickListener {
        void onItemClick(Dish dish);
    }

    public void setOnItemClickListener(OneItemClickListener listener) {
        this.listener = listener;
    }

    class DishHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewPrice;
        private TextView textViewCount;
        private TextView textViewMore;
        private ImageView imageViewIcon;

        private AppCompatButton buttonMinus;
        private AppCompatButton buttonPlus;
        private AppCompatImageButton buttonBack;
        private AppCompatButton buttonAddToCart;
        private AppCompatButton buttonContinueShop;
        private AppCompatButton buttonGoToCart;

        private TextView textViewTitleExpanded;
        private TextView textViewPriceExpanded;
        private ImageView imageViewIconExpanded;
        private ConstraintLayout expandedDishLayout;
        private ConstraintLayout dishLayout;
        private int countOfDish;

        public DishHolder(@NonNull View itemView, OneItemClickListener listener) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewPrice = itemView.findViewById(R.id.text_view_price);
            imageViewIcon = itemView.findViewById(R.id.dish_icon);

            dishLayout = itemView.findViewById(R.id.mini_dish);
            textViewCount = itemView.findViewById(R.id.text_view_count);
            textViewTitleExpanded = itemView.findViewById(R.id.text_view_title_expanded);
            textViewPriceExpanded = itemView.findViewById(R.id.text_view_price_expanded);
            imageViewIconExpanded = itemView.findViewById(R.id.dish_icon_expanded);
            expandedDishLayout = itemView.findViewById(R.id.expanded_dish);
            textViewMore = itemView.findViewById(R.id.text_view_more);

            buttonMinus = itemView.findViewById(R.id.button_minus);
            buttonPlus = itemView.findViewById(R.id.button_plus);
            buttonBack = itemView.findViewById(R.id.button_back);
            buttonAddToCart = itemView.findViewById(R.id.button_add_to_cart);
            buttonContinueShop = itemView.findViewById(R.id.button_continue_shop);
            buttonGoToCart = itemView.findViewById(R.id.button_go_to_cart);

            countOfDish = 0;
/*
            buttonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    countOfDish++;
                    textViewCount.setText(String.valueOf(countOfDish));
                }
            });*/

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(dishes.get(position));
                        //isExpanded = true;
                    }

                    /*if (isExpanded) {
                        expandedDishLayout.setVisibility(View.VISIBLE);
                        dishLayout.setVisibility(View.GONE);
                    }
                    else {
                        dishLayout.setVisibility(View.VISIBLE);
                        expandedDishLayout.setVisibility(View.GONE);
                    }//
                }
            });*/
        }
    }


    @NonNull
    @Override
    public DishHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dish_item, parent, false);
        return new DishHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DishHolder holder, int position) {
        Dish currentDish = dishes.get(position);
        String mDishName = currentDish.getNameDish();

        holder.textViewTitle.setText(mDishName);
        holder.textViewPrice.setText(String.valueOf(currentDish.getPrice()));
        holder.imageViewIcon.setImageResource(R.drawable.pngegg);

        holder.textViewTitleExpanded.setText(mDishName);
        holder.textViewPriceExpanded.setText(String.valueOf(currentDish.getPrice()));
        holder.imageViewIconExpanded.setImageResource(R.drawable.pngegg);

        if (mDishName.length() >= 18 && mDishName.length() < 29)
            holder.textViewTitle.setTextSize(20);
        else if (mDishName.length() >= 29) {
            holder.textViewTitle.setTextSize(18);
            holder.textViewTitleExpanded.setTextSize(16);
        }
        else holder.textViewTitle.setTextSize(22);


        final boolean isExpanded = position==mExpandedPosition;
        holder.expandedDishLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.dishLayout.setVisibility(isExpanded?View.GONE:View.VISIBLE);
        holder.itemView.setActivated(isExpanded);


        holder.buttonPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.countOfDish++;
                        holder.textViewCount.setText(String.valueOf(holder.countOfDish));
                    }
                });
        holder.buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.countOfDish > 0) holder.countOfDish--;
                holder.textViewCount.setText(String.valueOf(holder.countOfDish));
            }
        });
        holder.buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.buttonAddToCart.setVisibility(View.GONE);
                holder.buttonPlus.setVisibility(View.GONE);
                holder.buttonMinus.setVisibility(View.GONE);
                holder.buttonContinueShop.setVisibility(View.VISIBLE);
                holder.buttonGoToCart.setVisibility(View.VISIBLE);
            }
        });
        holder.buttonContinueShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.buttonAddToCart.setVisibility(View.VISIBLE);
                holder.buttonPlus.setVisibility(View.VISIBLE);
                holder.buttonMinus.setVisibility(View.VISIBLE);
                holder.buttonContinueShop.setVisibility(View.GONE);
                holder.buttonGoToCart.setVisibility(View.GONE);
                mExpandedPosition = isExpanded ? -1:holder.getAdapterPosition();;
                notifyDataSetChanged();
            }
        });
        holder.buttonGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //flex
            }
        });
        holder.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExpandedPosition = isExpanded ? -1:holder.getAdapterPosition();;
                notifyDataSetChanged();
            }
        });
        holder.textViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "zalupa", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), OneItemActivity.class);
                intent.putExtra("Title", mDishName);
                intent.putExtra("Price", currentDish.getPrice());
                intent.putExtra("Icon", R.drawable.pngegg);
                view.getContext().startActivity(intent);
            }
        });

        if (isExpanded) previousExpandedPosition = holder.getAdapterPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExpandedPosition = isExpanded ? -1:holder.getAdapterPosition();
                notifyDataSetChanged();
                //notifyItemChanged(previousExpandedPosition);
                //notifyItemChanged(position);
            }
        });

        /*holder.buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.countOfDish++;
                holder.textViewCount.setText(String.valueOf(holder.countOfDish));
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
        dishesFull = new ArrayList<>(dishes);
        notifyDataSetChanged();
    }

    public Filter getDishFilter() {
        return dishFilter;
    }

    private Filter dishFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Dish> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(dishesFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Dish item : dishesFull) {
                    if (item.getNameDish().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;


            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            dishes.clear();
            dishes.addAll((List) filterResults.values);
            /*
            dishes.add(new Dish(200,
                    "Foods",
                    "Залупа цветочная с горошком",
                    999_999,
                    "zalupa.jpg",
                    "1.3"));*/
            notifyDataSetChanged();
        }
    };
}
