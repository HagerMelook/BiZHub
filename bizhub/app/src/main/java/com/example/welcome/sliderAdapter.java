package com.example.welcome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class sliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    public int []listI = {R.drawable.bizhub,
            R.drawable.search,
            R.drawable.order,
            R.drawable.organise

    };

    public String[] listT={
            "Welcome to BizHub",
            "Search" ,
            "Order",
            "Manage"

    };

    public sliderAdapter (Context context){
        this.context= context;
    }

    public String[] listD ={
            "Lorem ipsum dolor sit amet, consectetur  adipiscing elit. Nam gravida venenatis  accumsan. In mi massa, tempus",
            "Lorem ipsum dolor sit amet, consectetur  adipiscing elit. Nam gravida venenatis  accumsan. In mi massa, tempus",
            "Lorem ipsum dolor sit amet, consectetur  adipiscing elit. Nam gravida venenatis  accumsan. In mi massa, tempus",
            "Lorem ipsum dolor sit amet, consectetur  adipiscing elit. Nam gravida venenatis  accumsan. In mi massa, tempus"
    };
    @Override
    public int getCount() {
        return listT.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull View container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view =inflater.inflate(R.layout.activity_welcome, (ViewGroup) container,false);
        LinearLayout linearLayout = view.findViewById(R.id.slider);
        ImageView imageView = view.findViewById(R.id.sliderImage);
        TextView textView = view.findViewById(R.id.text);
        TextView textView1 = view.findViewById(R.id.sliderD);
        imageView.setImageResource(listI[position]);
        textView.setText(listT[position]);
        textView1.setText(listD[position]);
        ((ViewGroup) container).addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
        ((ViewGroup) container).removeView((LinearLayout)object);
    }

}
