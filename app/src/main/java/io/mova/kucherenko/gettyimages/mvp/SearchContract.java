package io.mova.kucherenko.gettyimages.mvp;

import io.mova.kucherenko.gettyimages.models.response.Images;

public interface SearchContract {

    interface View {
        void updateUI(Images images);
        void startLoadAnimation();
        void stopLoadAnimation();
        void noDataAlert();
    }

    interface EventListener {
        void loadHistory();
        void loadCachedHistory();
        void search(String word);
        void onDestroy();
    }
}
