package com.chris.catgif

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.chris.catgif.ui.theme.CatGifTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatGifTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CatHolders()
                }
            }
        }
    }
}

    private val retrofit = RetrofitClient.getInstance() // Initialises Retrofit client

    private var _catResponse = MutableLiveData<MutableList<Cat>>() // Initialises a variable to hold the response from the API
    private val catApiInterface: CatApiInterface = retrofit.create(CatApiInterface::class.java) // Initialises the API
    private val catResponse = catApiInterface.getAllBreeds() // Requests the API to get all Cat Breeds

    fun getCats(): LiveData<MutableList<Cat>> { // Function to retrieve the response from the API as LiveData
        catResponse.clone().enqueue( // Queues the API response
            object : Callback<List<Cat>> {
                override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                    if (!response.isSuccessful) {
                        Log.e("Error", "code: " + response.code())
                        return
                    } else {
                        val items = response.body()
                        if (items != null) {
                            for (i in 0 until items.count()) {
                                _catResponse.value = items as MutableList<Cat>? // Adds the data retrieved from the API to response
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                    _catResponse
                }
            }
        )
        return _catResponse // Returns the response
    }

    @Composable
    fun CatHolders() {
        var expanded by remember { mutableStateOf(false) }
        var selectedIndex by remember { mutableStateOf(0) }
        val cats by getCats().observeAsState()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopCenter)
            ) {
                cats?.let {
                    Text(it[selectedIndex].name,
                        fontSize = 36.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().clickable(
                            onClick = { expanded = true })) }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    cats?.forEachIndexed { index, cat ->
                        DropdownMenuItem(onClick = {
                            selectedIndex = index
                            expanded = false
                        }, text = { Text(cat.name) }
                        )
                    }
                }
            }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                cats?.get(selectedIndex)?.temperament?.let { Text(it,
                    fontSize = 15.sp) }
            }

            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cats?.get(selectedIndex)?.image?.url)
                    .crossfade(true)
                    .build(),
                contentDescription = cats?.get(selectedIndex)?.name,
                contentScale = ContentScale.Fit,
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                    CircularProgressIndicator()
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
                cats?.get(selectedIndex)?.description?.let { Text(it,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,) }
        }
    }