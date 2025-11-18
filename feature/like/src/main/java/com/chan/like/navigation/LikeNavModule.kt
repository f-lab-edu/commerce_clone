package com.chan.like.navigation

import com.chan.navigation.NavDestinationProvider
import com.chan.navigation.NavGraphProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Inject

class LikeNavModuleProvider @Inject constructor() : NavDestinationProvider {
    override fun getDestination() = listOf(LikeDestination)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class LikeNavModule {
    @Binds
    @IntoSet
    abstract fun bindLikeNavGraph(graph: LikeNavGraph): NavGraphProvider

    @Binds
    @IntoSet
    abstract fun bindLikeDestinationProvider(provider: LikeNavModuleProvider): NavDestinationProvider
}