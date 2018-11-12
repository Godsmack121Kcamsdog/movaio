package io.mova.kucherenko.gettyimages.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import io.mova.kucherenko.gettyimages.R;
import io.mova.kucherenko.gettyimages.databinding.ImageItemBinding;
import io.mova.kucherenko.gettyimages.models.response.Hit;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    private List<Hit> list;
    private RequestOptions options;

    private Context ctx;
    private LayoutInflater inflater;

    public ImageAdapter(List<Hit> list, Context context) {
        this.list = list;
        this.ctx = context;
        inflater = LayoutInflater.from(ctx);
        setUpOptions();
    }

    public ImageAdapter(Context context) {
        this.list = new ArrayList<>();
        this.ctx = context;
        inflater = LayoutInflater.from(ctx);
        setUpOptions();
    }

    private void setUpOptions() {
        options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .priority(Priority.IMMEDIATE);
    }

    public List<Hit> getList() {
        return list;
    }

    public void setList(List<Hit> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageHolder(inflater.inflate(R.layout.image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        if (list.get(position) != null) {
            Glide.with(ctx)
                    .load(list.get(position).getLargeImageURL()).apply(options)
                    .thumbnail(0.5f)
                    .into(holder.binding.image);
            String data = list.get(position).getWord() + "\n" + list.get(position).getTime();

            //я бы мог сделать два разных холдера, но в рамках тестового лень )))
            if (list.get(position).getWord() == null)
                data = list.get(0).getWord() + "\n" + list.get(0).getTime();
            holder.binding.name.setText(data);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder {

        ImageItemBinding binding;

        ImageHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

    }
}