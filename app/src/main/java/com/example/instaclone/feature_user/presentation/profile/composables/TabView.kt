package com.example.instaclone.feature_user.presentation.profile.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.instaclone.feature_user.domain.models.TabModel

@Composable
fun TabView(
    tabModels: List<TabModel>,
    modifier: Modifier = Modifier,
    onTabSelected: (selectedTabIndex: Int) -> Unit
) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.Transparent,
        contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    ) {
        tabModels.forEachIndexed  {index, item ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(index)
                },
                selectedContentColor = MaterialTheme.colors.onBackground,
                unselectedContentColor = Color.Gray
            )
        }
    }
}