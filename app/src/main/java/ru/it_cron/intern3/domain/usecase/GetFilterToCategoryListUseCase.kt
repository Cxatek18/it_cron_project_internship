package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.CategoryFilter
import ru.it_cron.intern3.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow

class GetFilterToCategoryListUseCase(private val repository: FilterRepository) {

    fun getFilterToCategoryList(): Flow<List<CategoryFilter>> {
        return repository.getFilterToCategoryList()
    }
}