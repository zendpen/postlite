package com.zendpen.postlite.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zendpen.postlite.QuestionClass;
import com.zendpen.postlite.R;

public class TabTwo extends Fragment {

    @Nullable
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.tab_two, container, false);
        ListView listView = (ListView) view.findViewById(R.id.s_list_view);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef =  database.getReference().child("Statements");
        listView.setAdapter(new FirebaseListAdapter<QuestionClass>(getActivity(), QuestionClass.class,
                android.R.layout.simple_expandable_list_item_2, mRef) {

            // Populate view as needed
            @Override
            protected void populateView(View view, QuestionClass question, int position) {
                ((TextView) view.findViewById(android.R.id.text1)).setText(question.getQuestion());
            }
        });

        return view;
    }
}
