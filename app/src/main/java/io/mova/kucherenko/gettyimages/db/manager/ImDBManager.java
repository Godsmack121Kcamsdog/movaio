package io.mova.kucherenko.gettyimages.db.manager;

import io.mova.kucherenko.gettyimages.models.response.Hit;
import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ImDBManager {

    private static ImDBManager ourInstance;
    private Realm realm;

    public static synchronized ImDBManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new ImDBManager();
            ourInstance.realm = Realm.getDefaultInstance();
        }
        return ourInstance;
    }

    private ImDBManager() {
    }

    public Flowable<RealmResults<Hit>> readData() {
        RealmQuery<Hit> query = realm.where(Hit.class);
        if (realm.isAutoRefresh()) {
            return query.findAllAsync().asFlowable().filter(RealmResults::isLoaded);
        } else {
            return Flowable.just(query.findAll());
        }
    }

    public void writeData(Hit data) {
        if (!realm.isInTransaction())
            realm.executeTransaction(realm -> {
                Number numb = realm.where(Hit.class).max("primary_id");
                int id = numb == null ? 0 : numb.intValue() + 1;
                data.setPrimary_id(id);
                realm.copyToRealmOrUpdate(data);
            });
    }

    public void close() {
        realm.close();
    }


}
