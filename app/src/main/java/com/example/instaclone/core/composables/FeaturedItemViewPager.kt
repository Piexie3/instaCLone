package com.example.instaclone.core.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.example.instaclone.R
import com.example.instaclone.feature_user.data.dto.PostItemDto
import com.google.accompanist.pager.HorizontalPagerIndicator

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeaturedProductsViewPager(
    featuredPost: ArrayList<PostItemDto>,
    onNavigate: (product: PostItemDto) -> Unit,
    image:String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val pagerState = rememberPagerState()

        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            count = featuredPost.size,
            state = pagerState
        ) { currentPage ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.insta_logo),
                error = painterResource(R.drawable.ic_broken_image),
                fallback = painterResource(R.drawable.insta_logo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(12))
                    .fillMaxWidth()
                    .height(180.dp)
                    .aspectRatio(1F)
                    .clickable {
                        onNavigate(featuredPost[currentPage])
                    }
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            activeColor = MaterialTheme.colors.primary,
            inactiveColor = MaterialTheme.colors.primary
        )
    }
}
