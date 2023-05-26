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

public class OnlineBanglaFragment extends Fragment {

    private NewsPaperAdapter newsPaperAdapter;
    private ArrayList<NewsModel> newsModelArrayList;

    private RecyclerView recyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_online_bangla, container, false);

        recyclerview = view.findViewById(R.id.recyclerviewId);

        lodeList();

        return view;
    }

    private void setList() {
        newsModelArrayList.add(new NewsModel(getString(R.string.BDNews24), Constant.NEWSPAPER_URL + "BDNews24.jpg", "https://bangla.bdnews24.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.BanglaNews24), Constant.NEWSPAPER_URL + "BanglaNews24.png", "https://www.banglanews24.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.JagoNews24), Constant.NEWSPAPER_URL + "JagoNews24.jpg", "https://www.jagonews24.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.Priyo), Constant.NEWSPAPER_URL + "Priyo.png", "https://www.priyo.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.Bd24live), Constant.NEWSPAPER_URL + "Bd24live.png", "https://www.bd24live.com/bangla"));
        newsModelArrayList.add(new NewsModel(getString(R.string.Risingbd), Constant.NEWSPAPER_URL + "Risingbd.png", "https://www.risingbd.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.DailyBangladesh), Constant.NEWSPAPER_URL + "DailyBangladesh.png", "https://www.daily-bangladesh.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.Gonews24), Constant.NEWSPAPER_URL + "Gonews24.jpeg", "https://www.gonews24.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.BanglaTribune), Constant.NEWSPAPER_URL + "BanglaTribune.png", "https://www.banglatribune.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.BanglaInsider), Constant.NEWSPAPER_URL + "BanglaInsider.jpeg", "https://www.banglainsider.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.BSSNews), Constant.NEWSPAPER_URL + "BSSNews.jpg", "https://www.bssnews.net"));
        newsModelArrayList.add(new NewsModel(getString(R.string.Barta24), Constant.NEWSPAPER_URL + "Barta24.png", "https://barta24.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.AmarDesh), Constant.NEWSPAPER_URL + "AmarDesh.jpeg", "https://www.amardesh.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.DMPNews), Constant.NEWSPAPER_URL + "DMPNews.jpg", "https://dmpnews.org"));
        newsModelArrayList.add(new NewsModel(getString(R.string.SheershaSangbad), Constant.NEWSPAPER_URL + "SheershaSangbad.png", "https://www.sheershasangbad.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.SangbadProtidin), Constant.NEWSPAPER_URL + "SangbadProtidin.png", "https://www.sangbadpratidin.in"));
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