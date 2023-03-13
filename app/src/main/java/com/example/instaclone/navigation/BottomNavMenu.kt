package com.example.instaclone.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.instaclone.R
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest

enum class BottomNavItem(val icon: Int, val route: String){
    HOME(R.drawable.home, Screens.HomeScreen.route),
    SEARCH(R.drawable.search, Screens.SearchScreen.route),
    REELS(R.drawable.video, Screens.ReelsScreen.route),
    SHOPPING(R.drawable.buy, Screens.ShoppingScreen.route),

}

@Composable
fun BottomNavMenu(
    selectedItem: BottomNavItem,
    navController: NavController
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
        for (item in BottomNavItem.values()) {
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = "Image Item",
                modifier = Modifier
                    .size(33.dp)
                    .weight(.8f)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate(item.route)
                    },
                colorFilter = if (item == selectedItem)
                    ColorFilter.tint(Color.Cyan) else
                    ColorFilter.tint(Color.DarkGray)
            )
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.profile_image)
                .crossfade(true)
                .build(),
            contentDescription = "Profile",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(33.dp)
                .clickable {
                    navController.navigate(Screens.ProfileScreen.route)
                }
        )
    }
}