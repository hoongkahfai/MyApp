package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity
{
    private ImageView nike,adidas,puma;
    private ImageView vans,gucci,chanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        nike = (ImageView) findViewById(R.id.nike);
        adidas = (ImageView) findViewById(R.id.adidas);
        puma = (ImageView) findViewById(R.id.puma);

        vans = (ImageView) findViewById(R.id.vans);
        gucci = (ImageView) findViewById(R.id.gucci);
        chanel = (ImageView) findViewById(R.id.chanel);

        nike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","nike");
                startActivity(intent);
            }
        });

        adidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","adidas");
                startActivity(intent);
            }
        });

        puma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","puma");
                startActivity(intent);
            }
        });

        vans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","vans");
                startActivity(intent);
            }
        });

        gucci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","gucci");
                startActivity(intent);
            }
        });

        chanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","chanel");
                startActivity(intent);
            }
        });



    }
}
