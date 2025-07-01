package com.example.mvidemo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvidemo.domain.usecase.GetProductsUseCase
import com.example.mvidemo.presentation.intent.ProductsIntent
import com.example.mvidemo.presentation.intent.UIEvent
import com.example.mvidemo.presentation.state.ProductResult
import com.example.mvidemo.presentation.state.ProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsState())
    val state: StateFlow<ProductsState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow: SharedFlow<UIEvent> = _eventFlow.asSharedFlow()


    init {
        onIntent(ProductsIntent.LoadProducts)
    }

    fun onIntent(intent: ProductsIntent) {
        when (intent) {
            is ProductsIntent.LoadProducts -> loadProducts()
        }
    }


    private fun loadProducts() {
        viewModelScope.launch {
            _state.value = ProductsState(isLoading = true)
            try {
                when (val result = getProductsUseCase()) {
                    else ->
                        result.fold(
                            onSuccess = { products ->
                                _state.value = reduce(_state.value, ProductResult.Success(products))

                            },
                            onFailure = { exception ->
                                _state.value = reduce(
                                    _state.value,
                                    ProductResult.Error(exception.message ?: "Unknown Error")
                                )
                                _eventFlow.emit(UIEvent.showToast(exception.message ?: "Unknown Error"))
                                return@launch
                            }
                        )
                }
            } catch (e: Exception) {
                _state.value = ProductsState(error = e.localizedMessage)
            }
        }
    }

    // Reducer function
    private fun reduce(currentState: ProductsState, productResult: ProductResult): ProductsState {
        return when (productResult) {
            is ProductResult.loading -> currentState.copy(isLoading = true)
            is ProductResult.Success -> currentState.copy(
                isLoading = false,
                products = productResult.products
            )

            is ProductResult.Error -> currentState.copy(
                isLoading = false,
                error = productResult.message
            )
        }
    }
}
