package com.mohanad.myownbank.view.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Card;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter {
    private List<Card> cards;
    private Context context;
    public CardsAdapter(List<Card> cards,Context context) {

        this.cards = cards;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new CardsAdapter.CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CardsAdapter.CardHolder cardHolder = (CardsAdapter.CardHolder) holder;
        cardHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    class CardHolder extends RecyclerView.ViewHolder {
        TextView holder;
        TextView number;
        TextView validate;
        TextView ccv;
        ImageView cardType;

        private CardHolder(@NonNull View itemView) {
            super(itemView);

            holder = itemView.findViewById(R.id.card_name);
            number = itemView.findViewById(R.id.card_number);
            validate = itemView.findViewById(R.id.card_expire);
            ccv=itemView.findViewById(R.id.card_cvv);
            cardType=itemView.findViewById(R.id.card_type);
        }




        private void bind(int position) {

                holder.setText(cards.get(position).getCardHolder());
                number.setText(cards.get(position).getCardNomber());
                validate.setText(cards.get(position).getValidate());
                ccv.setText(cards.get(position).getCcv());
                if(cards.get(position).getType().equalsIgnoreCase("visa")){
                    cardType.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.whiteCardColor)));
                    cardType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_visa));
                }else{
                    cardType.setImageDrawable(context.getResources().getDrawable(R.drawable.master));
                }

        }
    }}