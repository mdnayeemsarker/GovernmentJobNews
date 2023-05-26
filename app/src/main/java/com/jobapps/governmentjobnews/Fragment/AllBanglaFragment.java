package com.jobapps.governmentjobnews.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jobapps.governmentjobnews.Adapter.NewsPaperAdapter;
import com.jobapps.governmentjobnews.Helper.Constant;
import com.jobapps.governmentjobnews.Model.NewsModel;
import com.jobapps.governmentjobnews.R;

import java.util.ArrayList;

public class AllBanglaFragment extends Fragment {

    private NewsPaperAdapter newsPaperAdapter;
    private ArrayList<NewsModel> newsModelArrayList;

    private RecyclerView recyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_bangla, container, false);

        recyclerview = view.findViewById(R.id.recyclerviewId);

        lodeList();

        return view;
    }

    private void setList() {
        newsModelArrayList.add(new NewsModel(getString(R.string.prothom_alo), Constant.NEWSPAPER_URL + "prothom-alo-logo.jpg", "https://www.prothomalo.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.BangladeshProtidin), Constant.NEWSPAPER_URL + "Bangladesh_Pratidin_logo.png", "https://www.bd-pratidin.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.Jugantor), Constant.NEWSPAPER_URL + "jugantor.jpg", "https://www.jugantor.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.Samakal), Constant.NEWSPAPER_URL + "samakal.png", "https://www.kalerkantho.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.ATNBangla), Constant.NEWSPAPER_URL + "atn-bangla.jpg", "http://www.atnbangla.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.JamunaTV), Constant.NEWSPAPER_URL + "jamuna.png", "https://www.jamuna.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.Channel24BD), Constant.NEWSPAPER_URL + "Channel-24-BD.png", "https://www.channel24bd.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.DailyStar), Constant.NEWSPAPER_URL + "daily-star.png", "https://www.thedailystar.net"));
        newsModelArrayList.add(new NewsModel(getString(R.string.MaasrangaTV), Constant.NEWSPAPER_URL + "Maasranga-TV.png", "https://maasranga.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.BanglaVision), Constant.NEWSPAPER_URL + "Bangla-Vision.jpeg", "https://www.bvnews24.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.TheDailyIttefaq), Constant.NEWSPAPER_URL + "Ittefaq.png", "https://www.ittefaq.com.bd"));
        newsModelArrayList.add(new NewsModel(getString(R.string.DailyManabZamin), Constant.NEWSPAPER_URL + "Daily-Manab-Zamin.png", "https://mzamin.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.DailyNayadiganta), Constant.NEWSPAPER_URL + "naya-diganta.png", "https://www.dailynayadiganta.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.DainikAmadershomoy), Constant.NEWSPAPER_URL + "Dainik-Amadershomoy.png", "https://www.dainikamadershomoy.com/"));
        newsModelArrayList.add(new NewsModel(getString(R.string.BanglaNews24), Constant.NEWSPAPER_URL + "BanglaNews24.png", "https://www.banglanews24.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.BDNews24), Constant.NEWSPAPER_URL + "BDNews24.jpg", "https://bangla.bdnews24.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.AsianTV), Constant.NEWSPAPER_URL + "asiantv.jpg", "https://asiantv.com.bd"));
        newsModelArrayList.add(new NewsModel(getString(R.string.KalerKantho), Constant.NEWSPAPER_URL + "kaler-kontho.png", "https://www.kalerkantho.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.IndependentTv), Constant.NEWSPAPER_URL + "Independent-Tv.png", "https://www.independent24.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.SomoyNews), Constant.NEWSPAPER_URL + "Somoy-News.jpg", "https://www.somoynews.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.NTVBD), Constant.NEWSPAPER_URL + "NTV-BD.png", "https://www.ntvbd.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.RTV), Constant.NEWSPAPER_URL + "RTV.png", "https://www.rtvonline.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.EkusheyTV), Constant.NEWSPAPER_URL + "Ekushey-TV.png", "https://www.ekushey-tv.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.SATV), Constant.NEWSPAPER_URL + "satv.jpg", "http://www.satv.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.Barta24), Constant.NEWSPAPER_URL + "Barta24.png", "https://barta24.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.DeshTV), Constant.NEWSPAPER_URL + "Desh-TV.png", "https://www.desh.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.AmarDesh), Constant.NEWSPAPER_URL + "AmarDesh.jpeg", "https://www.amardesh.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.EkattorTV), Constant.NEWSPAPER_URL + "ekattor-tv.jpeg", "https://ekattor.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.ATNNews), Constant.NEWSPAPER_URL + "Atn-news-live.png", "https://atnnewstv.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.ChannelI), Constant.NEWSPAPER_URL + "Channel-I.jpeg", "https://www.channelionline.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.MyTV), Constant.NEWSPAPER_URL + "My_TV_Logo.png", "https://mytvbd.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.BoishakhiTV), Constant.NEWSPAPER_URL + "boishakhi-tv.jpg", "https://boishakhionline.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.DMPNews), Constant.NEWSPAPER_URL + "DMPNews.jpg", "https://dmpnews.org"));
        newsModelArrayList.add(new NewsModel(getString(R.string.MohonaTV), Constant.NEWSPAPER_URL + "Mohona-Television-logo.jpg", "https://mohona.tv"));
        newsModelArrayList.add(new NewsModel(getString(R.string.GaziTVGTV), Constant.NEWSPAPER_URL + "GTVBangladeshLogo.png", "https://gazitv.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.JagoNews24), Constant.NEWSPAPER_URL + "JagoNews24.jpg", "https://www.jagonews24.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.Priyo), Constant.NEWSPAPER_URL + "Priyo.png", "https://www.priyo.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.Bd24live), Constant.NEWSPAPER_URL + "Bd24live.png", "https://www.bd24live.com/bangla"));
        newsModelArrayList.add(new NewsModel(getString(R.string.Risingbd), Constant.NEWSPAPER_URL + "Risingbd.png", "https://www.risingbd.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.BanglaInsider), Constant.NEWSPAPER_URL + "BanglaInsider.jpeg", "https://www.banglainsider.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.DailyBangladesh), Constant.NEWSPAPER_URL + "DailyBangladesh.png", "https://www.daily-bangladesh.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.Gonews24), Constant.NEWSPAPER_URL + "Gonews24.jpeg", "https://www.gonews24.com"));
        newsModelArrayList.add(new NewsModel(getString(R.string.BSSNews), Constant.NEWSPAPER_URL + "BSSNews.jpg", "https://www.bssnews.net"));
        newsModelArrayList.add(new NewsModel(getString(R.string.BanglaTribune), Constant.NEWSPAPER_URL + "BanglaTribune.png", "https://www.banglatribune.com"));
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