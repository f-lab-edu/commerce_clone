package com.chan.category.ui

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.android.LoadingState
import com.chan.category.domain.CategoryRepository
import com.chan.category.ui.CategoryContract.Effect.Navigation.*
import com.chan.category.ui.mapper.toCategoryUIModels
import com.chan.category.ui.model.CategoriesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseViewModel<CategoryContract.Event, CategoryContract.State, CategoryContract.Effect>() {

    private var categoriesCache: List<CategoriesModel>? = null
    private var headerPositionsCache: List<Pair<Int, String>>? = null


    override fun setInitialState() = CategoryContract.State()

    override fun handleEvent(event: CategoryContract.Event) {
        when (event) {
            is CategoryContract.Event.OnCategorySidebarClick -> {
                updateSelectedCategoryId(event.categoryId)
                onScrollToSidebar(event.categoryId)
                onScrollToContent(event.categoryId)
            }

            is CategoryContract.Event.OnCategoryClick -> setEffect { ToCategoryDetail(event.categoryId) }
            is CategoryContract.Event.OnContentScroll -> handleContentScroll(event.firstVisibleIndex)
            is CategoryContract.Event.OnTabSelected -> {
                setState { copy(selectedTabIndex = event.index) }
                fetchCategoriesForTab(event.index)
            }

            CategoryContract.Event.OnSearchClick -> setEffect { ToSearchRoute }
        }
    }

    init {
        fetchCategoriesForTab(0)
    }

    private fun fetchCategoriesForTab(index: Int) {
        when (index) {
            0 -> getAllCategories()
            1 -> getHealthPlusCategories()
            2 -> getLuxeEditCategories()
        }
    }

    fun getAllCategories() {
        categoriesCache?.let { cachedCategories ->
            setState {
                copy(
                    categories = cachedCategories,
                    headerPositions = headerPositionsCache ?: emptyList(),
                    selectedCategoryId = cachedCategories.firstOrNull()?.id
                )
            }
            return
        }
        handleRepositoryCall(
            call = {
                categoryRepository.getAllCategories().toCategoryUIModels()
            },
            onSuccess = { categories ->
                val headerPositions = categoryHeaderMapping(categories)
                categoriesCache = categories
                headerPositionsCache = headerPositions
                copy(
                    categories = categories,
                    selectedCategoryId = categories.firstOrNull()?.id,
                    headerPositions = headerPositions
                )
            }
        )
    }

    private fun getHealthPlusCategories() {
        viewModelScope.launch {
            setState { copy(loadingState = LoadingState.Loading) }
            val dummyCategories = listOf(
                CategoriesModel(id = "health_food", name = "건강식품", subCategories = emptyList()),
                CategoriesModel(id = "food", name = "푸드", subCategories = emptyList()),
                CategoriesModel(id = "oral_health_supplies", name = "구강/건강용품", subCategories = emptyList()),
                CategoriesModel(id = "women_s_products", name = "여성/위생용품", subCategories = emptyList()),
                CategoriesModel(id = "fashion", name = "패션", subCategories = emptyList()),
            )
            setState {
                copy(
                    categories = dummyCategories,
                    selectedCategoryId = dummyCategories.firstOrNull()?.id,
                    headerPositions = emptyList(), // Recalculate if needed
                    loadingState = LoadingState.Idle
                )
            }
        }
    }

    private fun getLuxeEditCategories() {
        viewModelScope.launch {
            setState { copy(loadingState = LoadingState.Loading) }
            val dummyCategories = listOf(
                CategoriesModel(id = "luxe_category", name = "카테고리", subCategories = emptyList()),
                CategoriesModel(id = "luxe_brand", name = "브랜드", subCategories = emptyList()),
            )
            setState {
                copy(
                    categories = dummyCategories,
                    selectedCategoryId = dummyCategories.firstOrNull()?.id,
                    headerPositions = emptyList(),
                    loadingState = LoadingState.Idle
                )
            }
        }
    }


    private fun <T> handleRepositoryCall(
        call: suspend () -> T,
        onSuccess: CategoryContract.State.(T) -> CategoryContract.State,
        onFinally: suspend (T) -> Unit = {}
    ) {
        viewModelScope.launch {
            setState { copy(loadingState = LoadingState.Loading) }

            try {
                val result = call()
                setState { onSuccess(result) }
                onFinally(result)

            } catch (e: Exception) {
                setState { copy(loadingState = LoadingState.Error) }
                setEffect { CategoryContract.Effect.ShowError(e.message.toString()) }
            }
        }
    }


    private fun updateSelectedCategoryId(categoryId: String) {
        setState { copy(selectedCategoryId = categoryId) }
    }

    private fun onScrollToSidebar(categoryId: String) {
        val index = viewState.value.categories.indexOfFirst { it.id == categoryId }
        if (index != -1) {
            setEffect { CategoryContract.Effect.ScrollToSidebar(index) }
        }
    }

    private fun onScrollToContent(categoryId: String) {
        val targetIndex = viewState.value.headerPositions
            .firstOrNull { it.second == categoryId }
            ?.first

        if (targetIndex != null) {
            setEffect { CategoryContract.Effect.ScrollToContent(targetIndex) }
        }
    }

    private fun handleContentScroll(firstVisibleIndex: Int) {
        val newCategoryId = viewState.value.headerPositions
            .filter { it.first <= firstVisibleIndex }
            .maxByOrNull { it.first }
            ?.second

        newCategoryId?.let { id ->
            if (id != viewState.value.selectedCategoryId) {
                updateSelectedCategoryId(id)
            }

            onScrollToSidebar(id)
        }
    }

    private fun categoryHeaderMapping(categories: List<CategoriesModel>): List<Pair<Int, String>> {
        var index = 0
        val positions = mutableListOf<Pair<Int, String>>()

        categories.forEach { category ->
            positions.add(index to category.id)
            index += 1 + category.subCategories.size
        }

        return positions
    }
}