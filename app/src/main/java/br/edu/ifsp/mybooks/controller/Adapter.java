package br.edu.ifsp.mybooks.controller;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.mybooks.R;
import br.edu.ifsp.mybooks.activities.ViewBookDetailActivity;
import br.edu.ifsp.mybooks.model.Livro;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Bruno on 05/11/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<Livro> livroList;
    private Context context;
    private Vibrator vibe;

    public Adapter(Context context) {
        this.context = context;
        livroList = new ArrayList<>();
        vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_livro, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Livro livro = livroList.get(position);
        holder.livroNameTextView.setText(livro.getNome());

            Glide.with(context)
                    .load(livro.getImagem())
                    .apply(RequestOptions.centerCropTransform())
                    .transition(withCrossFade())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .into(holder.livroPicImageView);

        Resources resources = context.getResources();

        holder.livroPicImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewBookDetailActivity.class);
                intent.putExtra("livroId", livro.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return livroList.size();
    }

    public void setLivroList(List<Livro> livroList) {
        this.livroList.addAll(livroList);
        notifyDataSetChanged();
    }

    public void setFilter(ArrayList<Livro> newList) {
        livroList = new ArrayList<Livro>();
        livroList.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected ImageView livroPicImageView;
        protected TextView livroNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            livroPicImageView = (ImageView) itemView.findViewById(R.id.livroPicImageView);
            livroNameTextView = (TextView) itemView.findViewById(R.id.textViewLivroName);
        }
    }
}
