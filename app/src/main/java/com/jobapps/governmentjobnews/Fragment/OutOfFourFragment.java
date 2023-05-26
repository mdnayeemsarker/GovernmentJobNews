package com.jobapps.governmentjobnews.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jobapps.governmentjobnews.Adapter.CgpaAdapter;
import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Model.CgpaModel;
import com.jobapps.governmentjobnews.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OutOfFourFragment extends Fragment {

    private CgpaAdapter cgpaAdapter;
    private ArrayList<CgpaModel> cgpaModelArrayList;

    private RecyclerView recyclerview;
    private Button calculateCgpaBtn;
    double total = 0;
    double totalCredit = 0;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_out_of_four, container, false);

        EditText subNameEdt = view.findViewById(R.id.edtSubName);
        EditText subGradeEdt = view.findViewById(R.id.edtSubGrade);
        EditText subCreditEdt = view.findViewById(R.id.edtSubCredit);

        recyclerview = view.findViewById(R.id.recyclerviewId);
        calculateCgpaBtn = view.findViewById(R.id.calculateCgpaBtnId);
        TextView cgpaTV = view.findViewById(R.id.cgpaTVID);

        LinearLayout cgpaLayout = view.findViewById(R.id.cgpaLayoutId);

        cgpaModelArrayList = new ArrayList<>();

        cgpaAdapter = new CgpaAdapter(cgpaModelArrayList);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerview.setHasFixedSize(true);
        // setting layout manager to our recycler view.
        recyclerview.setLayoutManager(manager);

        view.findViewById(R.id.addSubjectNameBtnId).setOnClickListener(v -> {

            String subName = subNameEdt.getText().toString();
            String subGrade = subGradeEdt.getText().toString();
            String subCredit = subCreditEdt.getText().toString();

            if (subName.isEmpty()) {
                subNameEdt.requestFocus();
                Toast.makeText(getActivity(), "বিষয়ের নাম লিখুন", Toast.LENGTH_SHORT).show();
            } else if (subGrade.isEmpty()) {
                subGradeEdt.requestFocus();
                Toast.makeText(getActivity(), "বিষয়ে প্রাপ্ত গ্রেড লিখুন", Toast.LENGTH_SHORT).show();
            } else if (subCredit.isEmpty()) {
                subCreditEdt.requestFocus();
                Toast.makeText(getActivity(), "বিষয়ের গ্রেড লিখুন", Toast.LENGTH_SHORT).show();
            } else {

                double subGradeDouble = Double.parseDouble(subGrade);
                double subCreditDouble = Double.parseDouble(subCredit);
                double subTotal = subGradeDouble * subCreditDouble;

                cgpaModelArrayList.add(new CgpaModel(subName, subGrade, subCredit, String.valueOf(subTotal)));
                // setting adapter to our recycler view.
                recyclerview.setAdapter(cgpaAdapter);
                cgpaAdapter.notifyItemInserted(cgpaModelArrayList.size());

                subNameEdt.clearFocus();
                subGradeEdt.clearFocus();
                subCreditEdt.clearFocus();

                subNameEdt.setText("");
                subGradeEdt.setText("");
                subCreditEdt.setText("");

                calculateCgpaBtn.setVisibility(View.VISIBLE);
                ApiConfig.hideKeyboard(getActivity(), v);
            }

        });

        calculateCgpaBtn.setOnClickListener(v -> {
            ApiConfig.checkConnection(requireActivity());
            // creating a new variable for gson.
            Gson gson = new Gson();
            // getting data from gson and storing it in a string.
            String json = gson.toJson(cgpaModelArrayList);
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String subTotal = object.getString("subTotal");
                    double subTotalDouble = Double.parseDouble(subTotal);
                    String subCredit = object.getString("subCredit");
                    double subCreditDouble = Double.parseDouble(subCredit);
                    total += subTotalDouble;
                    totalCredit += subCreditDouble;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cgpaLayout.setVisibility(View.VISIBLE);
            double cgpa = total / totalCredit;
            cgpaTV.setText("Your Gpa: " + new DecimalFormat("##.##").format(cgpa));
            Log.d("test", json);
            ApiConfig.loadInterstitial(requireActivity());
        });

        view.findViewById(R.id.resetBtnId).setOnClickListener(v -> {
            ApiConfig.checkConnection(requireActivity());
            ApiConfig.loadInterstitial(requireActivity());
            cgpaModelArrayList.clear();
            calculateCgpaBtn.setVisibility(View.GONE);
            cgpaLayout.setVisibility(View.GONE);
        });
        return view;
    }
}