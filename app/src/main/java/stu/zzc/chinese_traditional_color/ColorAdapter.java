package stu.zzc.chinese_traditional_color;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.VIBRATOR_SERVICE;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    private List<MyColor> mColorsList;
    private Context mContext;
    private Vibrator mVibrator;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ConstraintLayout colorCard;
        TextView colorInfo;
        TextView colorName;
        ImageView ic_flower;
        ImageView flower;

        public ViewHolder(View view) {
            super(view);
            colorCard = view.findViewById(R.id.ColorCard);
            cardView = view.findViewById(R.id.ColorCardView);
            colorInfo = view.findViewById(R.id.TVColorInfo);
            colorName = view.findViewById(R.id.TVColorName);
            ic_flower = view.findViewById(R.id.IViewIc_flower);
            flower = view.findViewById(R.id.IViewFlower);
        }
    }
    public ColorAdapter(List<MyColor> colorsList) {
        mColorsList = colorsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.color_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = holder.getAdapterPosition();
                MyColor myColor = mColorsList.get(position);
                ClipboardManager mClipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData =ClipData.newPlainText(null, "#" + myColor.getColorRGB().toUpperCase());
                mClipboardManager.setPrimaryClip(mClipData);
                Toast.makeText(mContext, "#" + myColor.getColorRGB().toUpperCase(), Toast.LENGTH_SHORT).show();
                mVibrator = (Vibrator) mContext.getSystemService(VIBRATOR_SERVICE);
                mVibrator.vibrate(30);
                return true;
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                MyColor myColor = mColorsList.get(position);
                Intent intent = new Intent(mContext, ColorDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", myColor.getColorname());
                bundle.putString("RGB", myColor.getColorRGB());
                bundle.putInt("darkColor", myColor.getDarkcolor());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyColor myColor = mColorsList.get(position);
        holder.colorInfo.setText("#" + myColor.getColorRGB().toUpperCase());
        holder.colorName.setText(myColor.getColorname());
        holder.colorCard.setBackgroundColor(Color.parseColor("#" + myColor.getColorRGB()));
        holder.colorInfo.setTextColor(myColor.getDarkcolor());
        holder.colorName.setTextColor(myColor.getDarkcolor());
        holder.ic_flower.setColorFilter(myColor.getDarkcolor());
        holder.flower.setColorFilter(myColor.getDarkcolor());
    }

    @Override
    public int getItemCount() {
        return mColorsList.size();
    }
}
