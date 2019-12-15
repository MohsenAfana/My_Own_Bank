package com.mohanad.myownbank.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Card;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter {
    private List<Card> cards;

    public CardsAdapter(List<Card> cards) {

        this.cards = cards;
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


        private CardHolder(@NonNull View itemView) {
            super(itemView);

            holder = itemView.findViewById(R.id.cardHolder);
            number = itemView.findViewById(R.id.card_number);
            validate = itemView.findViewById(R.id.validate);
        }




        private void bind(int position) {

                holder.setText(cards.get(position).getCardHolder());
                number.setText(cards.get(position).getCardNomber());
                validate.setText(cards.get(position).getValidate());


        }
    }}