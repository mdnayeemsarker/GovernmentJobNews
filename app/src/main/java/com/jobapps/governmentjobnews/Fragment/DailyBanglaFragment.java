package com.jobapps.governmentjobnews.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobapps.governmentjobnews.Adapter.NewsPaperAdapter;
import com.jobapps.governmentjobnews.Helper.Constant;
import com.jobapps.governmentjobnews.Model.NewsModel;
import com.jobapps.governmentjobnews.R;

import java.util.ArrayList;

public class DailyBanglaFragment extends Fragment {

    private NewsPaperAdapter newsPaperAdapter;
    private ArrayList<NewsModel> newsModelArrayList;

    private RecyclerView recyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_bangla, container, false);

        recyclerview = view.findViewById(R.id.recyclerviewId);

        lodeList();

        return view;
    }

    private void setList() {
        newsModelArrayList.add(new NewsModel(getString(R.string.prothom_alo), Constant.NEWSPAPER_URL + "prothom-alo-logo.jpg", "https://www.prothomalo.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.BangladeshProtidin), Constant.NEWSPAPER_URL + "Bangladesh_Pratidin_logo.png", "https://www.bd-pratidin.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.Jugantor), Constant.NEWSPAPER_URL + "jugantor.jpg", "https://www.jugantor.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.KalerKantho), Constant.NEWSPAPER_URL + "kaler-kontho.png", "https://www.kalerkantho.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.DailyStar), Constant.NEWSPAPER_URL + "daily-star.png", "https://www.thedailystar.net"));
        newsModelArrayList.add(new NewsModel(getString(R.string.Samakal), Constant.NEWSPAPER_URL + "samakal.png", "https://www.kalerkantho.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.TheDailyIttefaq), Constant.NEWSPAPER_URL + "Ittefaq.png", "https://www.ittefaq.com.bd"));
        newsModelArrayList.add(new NewsModel(getString(R.string.DailyManabZamin), Constant.NEWSPAPER_URL + "Daily-Manab-Zamin.png", "https://mzamin.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.DailyNayadiganta), Constant.NEWSPAPER_URL + "naya-diganta.png", "https://www.dailynayadiganta.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.DainikAmadershomoy), Constant.NEWSPAPER_URL + "Dainik-Amadershomoy.png", "https://www.dainikamadershomoy.com/"));

        // setting adapter to our recycler view.
        recyclerview.setAdapter(newsPaperAdapter);
//        newsPaperAdapter.notifyItemInserted(newsModelArrayList.size());
    }

    private void lodeList() {

        newsModelArrayList = new ArrayList<>();
        newsPaperAdapter = new NewsPaperAdapter(newsModelArrayList, getActivity());

        setList();

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerview.setHasFixedSize(true);
        // setting layout manager to our recycler view.
        recyclerview.setLayoutManager(manager);
    }

}