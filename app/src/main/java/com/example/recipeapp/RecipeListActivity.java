package com.example.recipeapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.recipeapp.models.Recipe;
import com.example.recipeapp.requests.RecipeApi;
import com.example.recipeapp.requests.ServiceGenerator;
import com.example.recipeapp.requests.responses.RecipeResponse;
import com.example.recipeapp.requests.responses.RecipeSearchResponse;
import com.example.recipeapp.util.Constants;
import com.example.recipeapp.viewmodels.RecipeListViewModel;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {
    private static final String TAG = "RecipeListActivity";

    private RecipeListViewModel mRecipeListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);

        subscribeObservers();

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testRetrofitRequest();
            }
        });
    }

    private void subscribeObservers(){
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if(recipes!=null){
                    for(Recipe recipe : recipes){
                        Log.d(TAG, "onChanged: " + recipe.getTitle());
                    }
                }
            }
        });
    }

    private void searchRecipesApi(String query, int pageNumber){
        mRecipeListViewModel.searchRecipesApi(query, pageNumber);
    }

    private void testRetrofitRequest(){
        searchRecipesApi("chicken breast", 1);
    }
}