package com.example.instaclone.feature_post.presentation.home.composables

import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.instaclone.feature_post.domain.models.Story
import com.example.instaclone.ui.theme.InstaCloneTheme

@Composable
fun InstagramStories(
    stories: List<Story>,
    onStoryClicked: (Story) -> Unit,
    navController: NavController
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (story in stories) {
                InstagramStoryItem(story, onStoryClicked)
            }
        }
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
        InstagramStoryContent(stories.first()) { navController.navigateUp() }
    }
}

@Composable
fun InstagramStoryItem(story: Story, onClick: (Story) -> Unit) {
    Column(
        modifier = Modifier.clickable { onClick(story) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(story.imageUrl)
                .build(),
            contentDescription = story.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = if (story.isSeen) Color.LightGray else Color.White,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = story.title,
            style = MaterialTheme.typography.caption,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun InstagramStoryContent(story: Story, onCloseClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(9 / 16f)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(story.imageUrl)
                .build(),
            contentDescription = story.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        IconButton(
            onClick = onCloseClicked,
            modifier = Modifier
                .padding(16.dp)
                .size(32.dp)
                .background(Color.White, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.Black,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun InstagramStoriesPrev(
    navController: NavController = rememberNavController()
) {
    InstaCloneTheme {
        InstagramStories(
            stories = listOf(
                Story(
                    title = "jonte",
                    imageUrl = "https://www.vibe.com/wp-content/uploads/2017/09/XXXTentacion-mugshot-orange-county-jail-1504911983-640x5601-1505432825.jpg?w=640&h=511&crop=1",
                    isSeen = false
                ),
                Story(
                    title = "manu",
                    imageUrl = "https://www.vibe.com/wp-content/uploads/2017/09/XXXTentacion-mugshot-orange-county-jail-1504911983-640x5601-1505432825.jpg?w=640&h=511&crop=1",
                    isSeen = false
                ),
                Story(
                    title = "manu",
                    imageUrl = "https://www.vibe.com/wp-content/uploads/2017/09/XXXTentacion-mugshot-orange-county-jail-1504911983-640x5601-1505432825.jpg?w=640&h=511&crop=1",
                    isSeen = false
                ),
                Story(
                    title = "manu",
                    imageUrl = "https://www.vibe.com/wp-content/uploads/2017/09/XXXTentacion-mugshot-orange-county-jail-1504911983-640x5601-1505432825.jpg?w=640&h=511&crop=1",
                    isSeen = false
                ),
                Story(
                    title = "manu",
                    imageUrl = "https://www.vibe.com/wp-content/uploads/2017/09/XXXTentacion-mugshot-orange-county-jail-1504911983-640x5601-1505432825.jpg?w=640&h=511&crop=1",
                    isSeen = false
                ),
                Story(
                    title = "manu",
                    imageUrl = "https://www.vibe.com/wp-content/uploads/2017/09/XXXTentacion-mugshot-orange-county-jail-1504911983-640x5601-1505432825.jpg?w=640&h=511&crop=1",
                    isSeen = false
                ),
                Story(
                    title = "manu",
                    imageUrl = "https://www.vibe.com/wp-content/uploads/2017/09/XXXTentacion-mugshot-orange-county-jail-1504911983-640x5601-1505432825.jpg?w=640&h=511&crop=1",
                    isSeen = false
                ),
                Story(
                    title = "manu",
                    imageUrl = "https://www.vibe.com/wp-content/uploads/2017/09/XXXTentacion-mugshot-orange-county-jail-1504911983-640x5601-1505432825.jpg?w=640&h=511&crop=1",
                    isSeen = false
                )
            ),
            onStoryClicked = {

            },
            navController
        )
    }
}
