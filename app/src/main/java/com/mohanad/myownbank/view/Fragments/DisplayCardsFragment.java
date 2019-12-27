package com.mohanad.myownbank.view.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Card;
import com.mohanad.myownbank.view.Adapters.CardsAdapter;
import com.mohanad.myownbank.view.Adapters.CardsExpandAdapter;
import com.mohanad.myownbank.viewmodel.HomeViewModel;
import com.mohanad.myownbank.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayCardsFragment extends Fragment {

    private MainViewModel mainViewModel=new MainViewModel();
    private  TextView holder;
    private ImageButton new_card;
    private TextView cardsNumber;
    private RecyclerView cards_recycler;
    private List<Card> cards;
    private int count=0;
    private HomeViewModel homeViewModel = new HomeViewModel();
    public DisplayCardsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_payment_profile, container, false);
        declaration(view);
        showCards();
        return view;
    }

    private void declaration(View view){
       holder=view.findViewById(R.id.Holder);
       new_card=view.findViewById(R.id.add_new_card);
       new_card.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openNewCard();
           }
       });
        cardsNumber=view.findViewById(R.id.cards_number);
        cards_recycler=view.findViewById(R.id.cards_expand);
       LinearLayoutManager vertical = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cards_recycler.setLayoutManager(vertical);
        cards = new ArrayList<>();
    }

    private void openNewCard() {
        getActivity(). getSupportFragmentManager().beginTransaction().
                replace(R.id.home, new AddCardFragment()).
                addToBackStack(null).commit();
    }

    private void showCards(){
        LiveData<Task<QuerySnapshot>> liveData = homeViewModel.getdataSnapshotLiveData();
        liveData.observe(this, new androidx.lifecycle.Observer<Task<QuerySnapshot>>() {
            @Override
            public void onChanged(Task<QuerySnapshot> querySnapshotTask) {
                if (querySnapshotTask.isSuccessful()) {

                    if (querySnapshotTask.getResult() != null) {
                        for (QueryDocumentSnapshot document : querySnapshotTask.getResult()) {
                            Card t = document.toObject(Card.class);
                            cards.add(t);
                            count++;

                        }
                        CardsExpandAdapter cardsAdapter = new CardsExpandAdapter(cards,getContext());
                        cards_recycler.setAdapter(cardsAdapter);
                        cardsNumber.setText(count+" Card(s)");
                    }

                }
            }
        });

        LiveData<Task<DocumentSnapshot>> liveData1 = mainViewModel.getdataSnapshotLiveData();
        liveData1.observe(this, new Observer<Task<DocumentSnapshot>>() {
            @Override
            public void onChanged(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    holder.setText(documentSnapshot.get("fullName").toString());

                }
            }
        });
    }

}
