package com.example.recipeapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.requests.RecipeApi;
import com.example.recipeapp.requests.ServiceGenerator;
import com.example.recipeapp.requests.responses.RecipeResponse;
import com.example.recipeapp.requests.responses.RecipeSearchResponse;
import com.example.recipeapp.util.Constants;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {
    private static final String TAG = "RecipeListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testRetrofitRequest();
            }
        });

    }

    private void testRetrofitRequest(){
        RecipeApi recipeApi = ServiceGenerator.getRecipeApi();

        Call<RecipeResponse> responseCall =
                recipeApi.getRecipe(Constants.API_KEY, "35742");

        responseCall.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                Log.d(TAG, "onResponse: server response " + response.toString());
                if(response.code() == 200){
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    Recipe recipe = response.body().getRecipe();
                    Log.d(TAG, "onResponse: RETRIEVED RECIPE: " + recipe.toString());
                }else {
                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string());
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {

            }
        });
    }
}