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

public class TVChannelsFragment extends Fragment {

    private NewsPaperAdapter newsPaperAdapter;
    private ArrayList<NewsModel> newsModelArrayList;

    private RecyclerView recyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_channels, container, false);
        recyclerview = view.findViewById(R.id.recyclerviewId);
        lodeList();
        return view;
    }

    private void setList() {
        newsModelArrayList.add(new NewsModel(getString(R.string.ChannelI), Constant.NEWSPAPER_URL + "Channel-I.jpeg", "https://www.channelionline.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.IndependentTv), Constant.NEWSPAPER_URL + "Independent-Tv.png", "https://www.independent24.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.SomoyNews), Constant.NEWSPAPER_URL + "Somoy-News.jpg", "https://www.somoynews.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.NTVBD), Constant.NEWSPAPER_URL + "NTV-BD.png", "https://www.ntvbd.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.Channel24BD), Constant.NEWSPAPER_URL + "Channel-24-BD.png", "https://www.channel24bd.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.RTV), Constant.NEWSPAPER_URL + "RTV.png", "https://www.rtvonline.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.EkusheyTV), Constant.NEWSPAPER_URL + "Ekushey-TV.png", "https://www.ekushey-tv.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.ATNBangla), Constant.NEWSPAPER_URL + "atn-bangla.jpg", "http://www.atnbangla.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.JamunaTV), Constant.NEWSPAPER_URL + "jamuna.png", "https://www.jamuna.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.SATV), Constant.NEWSPAPER_URL + "satv.jpg", "http://www.satv.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.DeshTV), Constant.NEWSPAPER_URL + "Desh-TV.png", "https://www.desh.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.MaasrangaTV), Constant.NEWSPAPER_URL + "Maasranga-TV.png", "https://maasranga.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.BanglaVision), Constant.NEWSPAPER_URL + "Bangla-Vision.jpeg", "https://www.bvnews24.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.EkattorTV), Constant.NEWSPAPER_URL + "ekattor-tv.jpeg", "https://ekattor.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.ATNNews), Constant.NEWSPAPER_URL + "Atn-news-live.png", "https://atnnewstv.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.MyTV), Constant.NEWSPAPER_URL + "My_TV_Logo.png", "https://mytvbd.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.BoishakhiTV), Constant.NEWSPAPER_URL + "boishakhi-tv.jpg", "https://boishakhionline.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.MohonaTV), Constant.NEWSPAPER_URL + "Mohona-Television-logo.jpg", "https://mohona.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.GaziTVGTV), Constant.NEWSPAPER_URL + "GTVBangladeshLogo.png", "https://gazitv.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.AsianTV), Constant.NEWSPAPER_URL + "asiantv.jpg", "https://asiantv.com.bd"));
        // setting adapter to our recycler view.
        recyclerview.setAdapter(newsPaperAdapter);
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