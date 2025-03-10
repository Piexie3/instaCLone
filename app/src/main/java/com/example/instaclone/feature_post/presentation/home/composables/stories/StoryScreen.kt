package com.example.instaclone.feature_post.presentation.home.composables.stories

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bettol.feature_posts.presentation.stories.LinearIndicator
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.instaclone.R

//@Composable
//fun StoryScreen() {
//    var running by remember { mutableStateOf(false) }
//    val progress: Float by animateFloatAsState(
//        if (running) 1f else 0f,
//        animationSpec = tween(
//            durationMillis = 10_000,
//            easing = LinearEasing
//        )
//    )
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            SegmentedProgressIndicator(
//                progress = progress,
//                modifier = Modifier
//                    .padding(top = 64.dp, start = 32.dp, end = 32.dp)
//                    .fillMaxWidth(),
//            )
//
//            Button(
//                onClick = { running = !running },
//                modifier = Modifier.padding(top = 32.dp)
//            ) {
//                Text(
//                    text = if (running) "Reverse Animation" else "Start Animation"
//                )
//            }
//        }
//}
@Composable
fun StoryScreen(
    navController: NavController
) {
    val listOfImages = listOf(
        R.drawable.background,
        R.drawable.post,
        R.drawable.post2,
    )

    Stories(numberOfPages = listOfImages.size,
        onEveryStoryChange = { position ->
            Log.i("DATA", "Story Change $position")
        },
        onComplete = {
            navController.popBackStack()
        }) { index ->
        Image(
            painter = painterResource(id = listOfImages[index]), contentDescription = null,
            contentScale = ContentScale.Fit, modifier = Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Stories(
    numberOfPages: Int,
    indicatorModifier: Modifier = Modifier
        .padding(top = 12.dp, bottom = 12.dp)
        .clip(RoundedCornerShape(12.dp)),
    spaceBetweenIndicator: Dp = 4.dp,
    indicatorBackgroundColor: Color = Color.LightGray,
    indicatorProgressColor: Color = Color.White,
    indicatorBackgroundGradientColors: List<Color> = emptyList(),
    slideDurationInSeconds: Long = 5,
    touchToPause: Boolean = true,
    hideIndicators: Boolean = false,
    onEveryStoryChange: ((Int) -> Unit)? = null,
    onComplete: () -> Unit,
    content: @Composable (Int) -> Unit,
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    var pauseTimer by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        //Full screen content behind the indicator
        StoryImage(pagerState = pagerState, onTap = {
            if (touchToPause)
                pauseTimer = true
        }, content)

        //Indicator based on the number of items
        val modifier =
            if (hideIndicators) {
                Modifier.fillMaxWidth()
            } else {
                Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            if (indicatorBackgroundGradientColors.isEmpty()) listOf(
                                Color.Black,
                                Color.Transparent
                            ) else indicatorBackgroundGradientColors
                        )
                    )
            }

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.padding(spaceBetweenIndicator))

            ListOfIndicators(
                numberOfPages,
                indicatorModifier,
                indicatorBackgroundColor,
                indicatorProgressColor,
                slideDurationInSeconds,
                pauseTimer,
                hideIndicators,
                coroutineScope,
                pagerState,
                spaceBetweenIndicator,
                onEveryStoryChange = onEveryStoryChange,
                onComplete = onComplete,
            )
        }
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun RowScope.ListOfIndicators(
    numberOfPages: Int,
    indicatorModifier: Modifier,
    indicatorBackgroundColor: Color,
    indicatorProgressColor: Color,
    slideDurationInSeconds: Long,
    pauseTimer: Boolean,
    hideIndicators: Boolean,
    coroutineScope: CoroutineScope,
    pagerState: PagerState,
    spaceBetweenIndicator: Dp,
    onEveryStoryChange: ((Int) -> Unit)? = null,
    onComplete: () -> Unit,
) {
    var currentPage by remember {
        mutableStateOf(0)
    }

    for (index in 0 until numberOfPages) {
        LinearIndicator(
            modifier = indicatorModifier.weight(1f),
            index == currentPage,
            indicatorBackgroundColor,
            indicatorProgressColor,
            slideDurationInSeconds,
            pauseTimer,
            hideIndicators
        ) {
            coroutineScope.launch {

                currentPage++

                if (currentPage < numberOfPages) {
                    onEveryStoryChange?.invoke(currentPage)
                    pagerState.animateScrollToPage(currentPage)
                }

                if (currentPage == numberOfPages) {
                    onComplete()
                }
            }
        }

        Spacer(modifier = Modifier.padding(spaceBetweenIndicator))
    }
}