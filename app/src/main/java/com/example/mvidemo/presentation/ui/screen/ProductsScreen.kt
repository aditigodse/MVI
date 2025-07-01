package com.example.mviapp.presentation.post

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.assignment.ui.model.Products
import com.example.mvidemo.R
import com.example.mvidemo.presentation.viewmodel.ProductsViewModel
import com.example.mvidemo.presentation.intent.UIEvent

@Composable
fun ProductsScreen(innerPadding: PaddingValues) {
    val productsViewModel: ProductsViewModel = viewModel()
    val state by productsViewModel.state.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        productsViewModel.eventFlow.collect{
            event ->
            when(event)
            {
                is UIEvent.showToast ->
                    Toast.makeText(context,event.message,Toast.LENGTH_LONG).show()
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)) {
        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    Text(
                        "Error: ${state.error}",
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Default
                    )
                    //Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
                }
            }

            else -> {
                LazyColumn {
                    items(state.products) { products ->
                        ProductListItem(products)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductListItem(product: Products) {
    Card(
        modifier = Modifier.padding(5.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.image)
                    .crossfade(true)
                    .build(),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Column(modifier = Modifier.padding(2.dp)) {
                Text(
                    text = product.title,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = product.description,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black
                )
            }

        }
    }
    VerticalDivider(modifier = Modifier.fillMaxWidth())
}
