package com.i4creed.bakingapp.ui;


import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.i4creed.bakingapp.R;
import com.i4creed.bakingapp.model.Recipe;
import com.i4creed.bakingapp.model.RecipeStep;
import com.i4creed.bakingapp.util.MyUtil;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailStepFragment extends Fragment {

    private static final String CURRENT_RECIPE_STEP_INDEX = "current_recipe_step_index";
    private static final String RECIPE = "recipe";
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
    MainActivity main;
    private SimpleExoPlayer player;
    private RecipeStep recipeStep;
    private Recipe recipe;
    private int stepIndex;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;


    public DetailStepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE);
            stepIndex = savedInstanceState.getInt(CURRENT_RECIPE_STEP_INDEX);
        }
        main = (MainActivity) getActivity();
        main.showUpButton();

        recipeStep = recipe.getSteps()[stepIndex];

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detail_step, container, false);
        ButterKnife.bind(this, root);

        //descriptionTv == null --> In Landscape mode
        if (descriptionTv != null) {
            descriptionTv.setText(recipeStep.getDescription());
            if (stepIndex == 0) {
                Objects.requireNonNull(previous).setEnabled(false);
            }

            if (stepIndex == recipe.getSteps().length - 1) {
                Objects.requireNonNull(next).setEnabled(false);
            }
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stepIndex += 1;
                    Objects.requireNonNull(getFragmentManager()).beginTransaction().detach(DetailStepFragment.this).attach(DetailStepFragment.this).commit();
                }
            });
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stepIndex -= 1;
                    Objects.requireNonNull(getFragmentManager()).beginTransaction().detach(DetailStepFragment.this).attach(DetailStepFragment.this).commit();
                }
            });
        }

        return root;
    }


    /**
     * Initializes the Exoplayer.
     */
    private void initializePlayer() {

        if (MyUtil.empty(recipeStep.getThumbnailURL()) && MyUtil.empty(recipeStep.getVideoURL())) {
            playerView.setVisibility(View.GONE);
        } else {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            playerView.setPlayer(player);

            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
            String url = null;
            if (!MyUtil.empty(recipeStep.getThumbnailURL())) {
                url = recipeStep.getThumbnailURL();
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
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !main.isTwoPane() ) {
            hideSystemUi();
        }
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

    /**
     * Releases the Exoplayer.
     */
    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    /**
     * Builds the media source.
     *
     * @param uri to build upon.
     * @return media source.
     */
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    /**
     * Returns the recipe step.
     *
     * @return recipe step.
     */
    public RecipeStep getRecipeStep() {
        return recipeStep;
    }

    /**
     * Sets the recipe step
     *
     * @param recipe
     * @param index
     */
    public void setRecipeStep(Recipe recipe, int index) {
        this.recipe = recipe;
        this.stepIndex = index;
    }

}

