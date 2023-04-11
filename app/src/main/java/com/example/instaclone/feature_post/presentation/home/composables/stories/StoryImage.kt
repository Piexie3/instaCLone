package com.example.instaclone.feature_post.presentation.home.composables.stories

import android.view.MotionEvent
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class)
@Composable
fun StoryImage(
    pagerState: PagerState,
    onTap: (Boolean) -> Unit,
    content: @Composable (Int) -> Unit
) {
    HorizontalPager(
        count = 3,
        state = pagerState,
        modifier = Modifier.pointerInteropFilter {
            when (it.action) {
                MotionEvent.ACTION_BUTTON_PRESS -> {
                    onTap(true)
                }
                MotionEvent.ACTION_BUTTON_RELEASE -> {
                    onTap(false)
                }
            }
            true
        }

    ) {
        content(it)
    }
}