package com.i4creed.bakingapp.ui;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.i4creed.bakingapp.MyUtil;
import com.i4creed.bakingapp.R;
import com.i4creed.bakingapp.model.Recipe;
import com.i4creed.bakingapp.model.RecipeStep;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailStepFragment extends Fragment {

    public static final String CURRENT_RECIPE_STEP_INDEX = "current_recipe_step_index";
    public static final String RECIPE = "recipe";

    SimpleExoPlayer player;

    @Nullable
    @BindView(R.id.next)
    Button next;

    @Nullable
    @BindView(R.id.previous)
    Button previous;

    @Nullable
    @BindView(R.id.description)
    TextView descriptionTv;

    @BindView(R.id.video_view)
    PlayerView playerView;

    RecipeStep recipeStep;
    Recipe recipe;
    int stepIndex;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    public DetailStepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE);
            stepIndex = savedInstanceState.getInt(CURRENT_RECIPE_STEP_INDEX);
        }

        recipeStep = recipe.getSteps()[stepIndex];

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detail_step, container, false);
        ButterKnife.bind(this, root);

        //descriptionTv == null --> In Landscape mode
        if (descriptionTv != null) {
            descriptionTv.setText(recipeStep.getDescription());
            if(stepIndex == 0) {
                previous.setEnabled(false);
            }

            if(stepIndex == recipe.getSteps().length - 1) {
                next.setEnabled(false);
            }
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stepIndex += 1;
                    getFragmentManager().beginTransaction().detach(DetailStepFragment.this).attach(DetailStepFragment.this).commit();
                }
            });
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stepIndex -= 1;
                    getFragmentManager().beginTransaction().detach(DetailStepFragment.this).attach(DetailStepFragment.this).commit();
                }
            });
        }

        return root;
    }


    private void initializePlayer() {

        if (MyUtil.empty(recipeStep.getThumnailURL()) && MyUtil.empty(recipeStep.getVideoURL())) {
            playerView.setVisibility(View.GONE);
        } else {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            playerView.setPlayer(player);

            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
            String url = null;
            if (!MyUtil.empty(recipeStep.getThumnailURL())) {
                url = recipeStep.getThumnailURL();
            } else if (!MyUtil.empty(recipeStep.getVideoURL())) {
                url = recipeStep.getVideoURL();
            }
            Uri uri = Uri.parse(url);
            MediaSource mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource, true, false);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE, recipe);
        outState.putInt(CURRENT_RECIPE_STEP_INDEX, stepIndex);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    public RecipeStep getRecipeStep() {
        return recipeStep;
    }

    public void setRecipeStep(Recipe recipe, int index) {
        this.recipe = recipe;
        this.stepIndex = index;
    }
}

