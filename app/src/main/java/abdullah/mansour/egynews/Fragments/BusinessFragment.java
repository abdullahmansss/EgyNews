package abdullah.mansour.egynews.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.List;

import abdullah.mansour.egynews.Models.NewsModel;
import abdullah.mansour.egynews.R;
import abdullah.mansour.egynews.Utils;

public class BusinessFragment extends Fragment
{
    View view;

    RotateLoading rotateLoading;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    List<NewsModel> newsModels;
    Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.business_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerview);
        rotateLoading = view.findViewById(R.id.signinrotateloading);

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        rotateLoading.start();

        newsModels = new ArrayList<>();

        AsyncTask newtask = new AsyncTask();
        newtask.execute("https://newsapi.org/v2/top-headlines?country=eg&category=business&apiKey=d92a31bce9204f11b827c45c5cee6c66\n");
    }

    public class AsyncTask extends android.os.AsyncTask<String, Void, List<NewsModel>>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            rotateLoading.start();
        }

        @Override
        protected List<NewsModel> doInBackground(String... strings)
        {
            if (strings.length < 1 || strings[0] == null)
            {
                return null;
            }

            List <NewsModel> books = Utils.fetchBooksData(strings[0]);

            return books;
        }

        @Override
        protected void onPostExecute(List<NewsModel> dataClasses)
        {
            newsModels.clear();

            if (dataClasses != null && !dataClasses.isEmpty())
            {
                rotateLoading.stop();
                newsModels.addAll(dataClasses);

                adapter = new Adapter(newsModels);
                recyclerView.setAdapter(adapter);
            }
        }
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.itemviewHolder>
    {
        List<NewsModel> models;

        Adapter(List<NewsModel> models)
        {
            this.models = models;
        }

        @NonNull
        @Override
        public Adapter.itemviewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
        {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.news_item, viewGroup, false);
            return new Adapter.itemviewHolder(view);
        }

        @Override
        public void onBindViewHolder(Adapter.itemviewHolder viewHolder, int i)
        {
            final String name = models.get(i).getTitle();
            final String image = models.get(i).getImageurl();

            viewHolder.textView.setText(name);

            Picasso.get()
                    .load(image)
                    .placeholder(R.drawable.ic_newsletter)
                    .error(R.drawable.ic_newsletter)
                    .into(viewHolder.imageView);
        }

        @Override
        public int getItemCount()
        {
            return models.size();
        }

        private class itemviewHolder extends RecyclerView.ViewHolder
        {
            ImageView imageView;
            TextView textView;

            private itemviewHolder(View itemView)
            {
                super(itemView);

                imageView = itemView.findViewById(R.id.news_image);
                textView = itemView.findViewById(R.id.news_title);
            }
        }
    }
}
