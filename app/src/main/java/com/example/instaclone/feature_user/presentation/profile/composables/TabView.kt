package com.example.instaclone.feature_user.presentation.profile.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.instaclone.feature_user.domain.models.TabModel
import com.example.instaclone.ui.theme.lighBlue

@Composable
fun TabView(
    tabModels: List<TabModel>,
    modifier: Modifier = Modifier,
    onTabSelected: (selectedTabIndex: Int) -> Unit
) {
    val inactiveColor = Color(0xFF777777)
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.Transparent,
        modifier = modifier
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
            ){
                Icon(
                    painter = item.image,
                    contentDescription = item.text,
                    tint = if(selectedTabIndex == index) lighBlue else inactiveColor,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(20.dp)
                )
            }
        }
    }
}