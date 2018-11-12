package io.mova.kucherenko.gettyimages.mvp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import io.mova.kucherenko.gettyimages.R;
import io.mova.kucherenko.gettyimages.adapter.ImageAdapter;
import io.mova.kucherenko.gettyimages.databinding.ActivitySearchBinding;
import io.mova.kucherenko.gettyimages.models.response.Images;
import io.mova.kucherenko.gettyimages.utils.SpaceItemDecoration;
import io.mova.kucherenko.gettyimages.utils.network.Connection;

public class SearchView extends AppCompatActivity implements SearchContract.View {

    private SearchPresenter presenter;
    private ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        presenter = new SearchPresenter(this);

        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.addItemDecoration(new SpaceItemDecoration(3));
        binding.recycler.setAdapter(adapter = new ImageAdapter(this));


        binding.search.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (Connection.isNetworkAvailable(SearchView.this))
                    presenter.search(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        binding.search.setOnCloseListener(() -> {
            presenter.loadCachedHistory();
            return false;
        });
        presenter.loadHistory();
    }

    @Override
    public void updateUI(Images images) {
        if (images.getHits() != null) {
            adapter.setList(images.getHits());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void startLoadAnimation() {

    }

    @Override
    public void stopLoadAnimation() {

    }

    @Override
    public void noDataAlert() {
        Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
