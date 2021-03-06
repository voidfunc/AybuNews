package com.example.aybunews;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class fragment_yemek_listesi extends Fragment {

    private WebView webView = null;
    private ListView listView;
    public ArrayList liste = new ArrayList();
    private ArrayAdapter<String> adapter;

    public fragment_yemek_listesi() {
        // Required empty public constructor
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_yemek_listesi,container,false);

        /*webView = v.findViewById(R.id.webView_yemek);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://aybu.edu.tr/muhendislik/bilgisayar/");*/

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, liste);
        listView = v.findViewById(R.id.listview_yemek);

            new veriGetir().execute();

        return v;
    }

    private class veriGetir extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            liste.clear();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc = Jsoup.connect("http://web.archive.org/web/20190406185041/https://aybu.edu.tr/sks/")
                        .timeout(30*1000).get();

                Elements yemekler = doc.select("p[style=\"text-align: center;\"]");
                for(int i = 0; i < yemekler.size(); i++){
                    liste.add(yemekler.get(i).text());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            listView.setAdapter(adapter);

        }
    }
}
