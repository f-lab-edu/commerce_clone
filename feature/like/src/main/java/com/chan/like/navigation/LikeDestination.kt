package com.chan.like.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.navigation.NamedNavArgument
import com.chan.like.R
import com.chan.navigation.BottomNavDestination
import com.chan.navigation.Routes

object LikeDestination : BottomNavDestination{
    override val title = R.string.like
    override val icon = Icons.Default.Favorite
    override val route = Routes.Like.route
    override val arguments: List<NamedNavArgument> = emptyList()
}