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

public class CardsExpandAdapter extends RecyclerView.Adapter {
    private List<Card> cards;
    private Context context;
    public CardsExpandAdapter(List<Card> cards,Context context) {

        this.cards = cards;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expand_layout, parent, false);
        return new CardsExpandAdapter.CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CardsExpandAdapter.CardHolder cardHolder = (CardsExpandAdapter.CardHolder) holder;
        cardHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

   private class CardHolder extends RecyclerView.ViewHolder {
        TextView holder;
        TextView number;
        TextView validate;
        TextView ccv;
        ImageView cardType;

        private CardHolder(@NonNull View itemView) {
            super(itemView);

            holder = itemView.findViewById(R.id.cardName);
            number = itemView.findViewById(R.id.cardNumber);
            validate = itemView.findViewById(R.id.cardExpire);
            ccv=itemView.findViewById(R.id.cardCVV);
            cardType=itemView.findViewById(R.id.cardType);
        }




        private void bind(int position) {

            holder.setText(cards.get(position).getCardHolder());
            number.setText(cards.get(position).getCardNomber());
            validate.setText(cards.get(position).getValidate());
            ccv.setText(cards.get(position).getCcv());
            if(cards.get(position).getType().equalsIgnoreCase("visa")){
                cardType.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.indigo_A700)));
                cardType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_visa));
            }else{
                cardType.setImageDrawable(context.getResources().getDrawable(R.drawable.master));
            }

        }
    }}