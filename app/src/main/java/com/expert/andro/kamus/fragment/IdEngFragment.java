package com.expert.andro.kamus.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.expert.andro.kamus.R;
import com.expert.andro.kamus.adapter.KamusIdEngAdapter;
import com.expert.andro.kamus.db.KamusHelper;
import com.expert.andro.kamus.model.KamusModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class IdEngFragment extends Fragment {

    RecyclerView recyclerView;
    SearchView edt_search;
    ArrayList<KamusModel> kamusModels;
    KamusIdEngAdapter kamusAdapter;
    KamusHelper kamusHelper;

    public IdEngFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_id_eng, container, false);

        recyclerView = view.findViewById(R.id.recylerview);
        edt_search = view.findViewById(R.id.sv);
        kamusAdapter = new KamusIdEngAdapter(getActivity());
        kamusHelper = new KamusHelper(getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(kamusAdapter);

        edt_search.setQueryHint(getString(R.string.text_hint_search_id));
        edt_search.onActionViewExpanded();
        edt_search.setIconified(false);

        edt_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length()>0) {
                    recyclerView.setVisibility(View.VISIBLE);

                    kamusHelper.open();
                    kamusModels = kamusHelper.getDataIdEng(newText);
                    kamusHelper.close();

                    kamusAdapter.setDataIdEng(kamusModels);
                } else {
                    kamusModels.clear();
                    kamusAdapter.setDataIdEng(null);
                    recyclerView.setVisibility(View.GONE);
                }
                return false;
            }
        });
        return view;
    }

}
