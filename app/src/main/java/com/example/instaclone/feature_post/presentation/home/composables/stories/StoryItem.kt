package com.example.bettol.feature_posts.presentation.stories

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StoryItem() {

}


@Composable
fun ProgressBarPreview() {
    Row(
        modifier = Modifier
            .height(4.dp)
            .clip(RoundedCornerShape(10)) // (1)
            .background(Color.White.copy(alpha = 0.4f)) // (2)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxHeight()
                .fillMaxWidth(0.5f), // (3)
        ) {

        }
    }
}

@Preview(widthDp = 600)
@Composable
fun InstagramSlicedProgressBar(
    steps: Int = 3,
    currentStep: Int = 2,
    paused: Boolean = false,
    onFinished: () -> Unit = {}
) {

    val percent = remember { Animatable(0f) } // (1)
    LaunchedEffect(paused) { // (2)
        if (paused) percent.stop()
        else {
            percent.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = (5000 * (1f - percent.value)).toInt(), // (3)
                    easing = LinearEasing
                )
            )
            onFinished() // (4)
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(48.dp)
            .padding(24.dp, 0.dp),
    ) {
        for (index in 1..steps) { // (1)
            // We use our previous code :
            Row(
                modifier = Modifier
                    .height(4.dp)
                    .clip(RoundedCornerShape(50, 50, 50, 50)) // (1)
                    .background(Color.White.copy(alpha = 0.4f)) // (2)
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxHeight()
                        .fillMaxWidth(0.5f) // (3),
                        .fillMaxHeight().let {
                            // Here we check if we need to fill the progress bar of not :
                            when (index) { // (2)
                                currentStep -> it.fillMaxWidth(percent.value)
                                in 0..currentStep -> it.fillMaxWidth(1f)
                                else -> it
                            }
                        },
                ) {}
            }
            if (index != steps) {
                Spacer(modifier = Modifier.width(4.dp)) // (3)
            }
        }
    }
}


private const val BackgroundOpacity = 0.25f
private const val NumberOfSegments = 8
private val StrokeWidth = 4.dp
private val SegmentGap = 8.dp

@Composable
fun SegmentedProgressIndicator(
    /*@FloatRange(from = 0.0, to = 1.0)*/
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = color.copy(alpha = BackgroundOpacity),
    strokeWidth: Dp = StrokeWidth,
    numberOfSegments: Int = NumberOfSegments,
    segmentGap: Dp = SegmentGap
) {
    val gap: Float
    val stroke: Float
    with(LocalDensity.current) {
        gap = segmentGap.toPx()
        stroke = strokeWidth.toPx()
    }
    Canvas(
        modifier
            .progressSemantics(progress)
            .fillMaxWidth()
            .height(strokeWidth)
            .focusable()
    ) {
        drawSegments(1f, backgroundColor, stroke, numberOfSegments, gap)
        drawSegments(progress, color, stroke, numberOfSegments, gap)
    }
}

private fun DrawScope.drawSegments(
    progress: Float,
    color: Color,
    strokeWidth: Float,
    segments: Int,
    segmentGap: Float,
) {
    val width = size.width
    val start = 0f
    val gaps = (segments - 1) * segmentGap
    val segmentWidth = (width - gaps) / segments
    val barsWidth = segmentWidth * segments
    val end = barsWidth * progress + (progress * segments).toInt()* segmentGap

    repeat(segments) { index ->
        val offset = index * (segmentWidth + segmentGap)
        if (offset < end) {
            val barEnd = (offset + segmentWidth).coerceAtMost(end)
            drawLine(
                color,
                Offset(start + offset, 0f),
                Offset(barEnd, 0f),
                strokeWidth
            )
        }
    }
}


