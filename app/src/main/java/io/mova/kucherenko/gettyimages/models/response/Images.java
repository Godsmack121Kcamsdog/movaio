
package io.mova.kucherenko.gettyimages.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Images {

    @SerializedName("id")
    @Expose
    private Integer id = 0;

    @SerializedName("totalHits")
    @Expose
    private int totalHits;

    @SerializedName("hits")
    @Expose
    private List<Hit> hits = null;
    @SerializedName("total")
    @Expose
    private int total;

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
