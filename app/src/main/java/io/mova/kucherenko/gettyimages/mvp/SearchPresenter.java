package io.mova.kucherenko.gettyimages.mvp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.mova.kucherenko.gettyimages.OnError;
import io.mova.kucherenko.gettyimages.api.ImageAPI;
import io.mova.kucherenko.gettyimages.db.manager.ImDBManager;
import io.mova.kucherenko.gettyimages.models.response.Hit;
import io.mova.kucherenko.gettyimages.models.response.Images;
import io.mova.kucherenko.gettyimages.utils.DateTimeUtils;
import io.realm.Realm;

public class SearchPresenter implements SearchContract.EventListener, OnError {

    private SearchContract.View view;
    private ImDBManager manager;
    private List<Hit> cachedHistoryList;

    public SearchPresenter(SearchContract.View view) {
        this.view = view;
        manager = ImDBManager.getInstance();
        cachedHistoryList = new ArrayList<>();
    }

    /**
     * load data from db
     */
    @Override
    public void loadHistory() {
        manager.readData().doOnError(this::onError).doOnNext(
                hits -> {
                    List<Hit> images = new ArrayList<>(hits);
                    Images im = new Images();
                    im.setHits(images);
                    cachedHistoryList.addAll(hits);
                    view.updateUI(im);
                }
        ).subscribe();
    }

    /**
     * for loading data without db request
     * In case of active session
     */
    @Override
    public void loadCachedHistory() {
        Images im = new Images();
        im.setHits(cachedHistoryList);
        view.updateUI(im);
    }


    /**
     * search request and UI update call in case of success
     *
     * @param word - search word
     */
    @Override
    public void search(String word) {
        ImageAPI.getInstance().getImages(word)
                .doOnNext(data -> {
                    if (data.getHits().size() != 0) {
                        save(data, word);
                        view.updateUI(data);
                    } else view.noDataAlert();
                })
                .doOnError(this::onError)
                .subscribe();
    }

    @Override
    public void onDestroy() {
        manager.close();
    }

    @Override
    public void onError(Throwable e) {
        Log.e("err", e.getMessage());
    }

    private void save(Images data, String word) {
        Hit result = data.getHits().get(0);
        result.setWord(word);
        result.setTime(DateTimeUtils.getFormatedDateNow());
        cachedHistoryList.add(0, result);
        manager.writeData(result);
    }
}
