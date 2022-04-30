package com.example.boosted

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


//Alternatively, we could have two classes, one for text and one for image
//But this will not scale well and we'll have to pass object into the adapter which can cause issues
data class Post(
    val type: PostType,
    val title: String,
    val content: Any
)

data class Poll(
    val choiceTitle: String,
    var count: Int,
    var pollID: Int = -1,
    var radioButton: RadioButton? = null
)

enum class PostType(){
    TEXT,
    IMAGE,
    POLL
}

const val BASE_URL = "https://jsonplaceholder.typicode.com/posts/"

class FeedActivity : AppCompatActivity() {
    private val TAG = "FeedActivity"
    val listOfPosts= mutableListOf<Post>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)


        getApiData()

        val pollOptions= mutableListOf<Poll>()
        pollOptions.add(Poll("Java 😃",0))
        pollOptions.add(Poll("Kotlin 💖",0))
        pollOptions.add(Poll("C/C++ 😬",0))
        pollOptions.add(Poll("Others 🤔",0))

        listOfPosts.add(Post(PostType.IMAGE, "Zero Attendance Policy ftw",  R.drawable.hello as Any))
        listOfPosts.add(Post(PostType.POLL, "What is your favorite language for android dev?", pollOptions as Any) )
        listOfPosts.add(Post(PostType.IMAGE, "Toppr Office", R.drawable.toppr_jpg as Any) )
        listOfPosts.add(Post(PostType.TEXT, "Tell me why?", "Ain't nothing but a heartbreak" as Any) )
        listOfPosts.add(Post(PostType.TEXT, "Tell me why?", "Ain't nothing but a mistake" as Any) )
        listOfPosts.add(Post(PostType.TEXT, "Tell me why?", "I never wanna hear you say" as Any) )
        listOfPosts.add(Post(PostType.TEXT, "Debugging is cool", "6 hours of debugging can save you 5 minutes of reading the documentation" as Any) )
        listOfPosts.add(Post(PostType.TEXT, "Empty String", "" as Any) )
        listOfPosts.add(Post(PostType.TEXT, "Integer", "343" as Any) )
        listOfPosts.add(Post(PostType.TEXT, "Decimal number", "123.832" as Any) )

//        attachView()

    }

    //Attaching the recycler view to this activity
    private fun attachView(){
        val feedRecyclerView = findViewById<RecyclerView> (R.id.feedRecyclerView)
        val dividerItemDecoration = DividerItemDecoration(feedRecyclerView.context, DividerItemDecoration.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
            ?.let { dividerItemDecoration.setDrawable(it) }
        feedRecyclerView.addItemDecoration(dividerItemDecoration)
        feedRecyclerView.adapter = TextAdapter(listOfPosts)
        feedRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun getApiData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofit = retrofitBuilder.getData()
        listOfPosts.add(Post(PostType.TEXT, "Interesting title", "Interesting post" as Any))

        retrofit.enqueue(object : Callback<List<ApiItem>> {

            override fun onResponse(call: Call<List<ApiItem>>, response: Response<List<ApiItem>>) {

                listOfPosts.add(Post(PostType.TEXT, "Interesting title 2", "Interesting post 2" as Any))
                Log.d(TAG, "List contains $listOfPosts")
                Log.d(TAG, "API Call worked! We have the following stuff ="+ response.body())
                for(apiItem in response.body()!!){
                    listOfPosts.add(Post(PostType.TEXT, apiItem.title, apiItem.body as Any))
                }
                attachView()
            }

            override fun onFailure(call: Call<List<ApiItem>>, t: Throwable) {
                Log.d(TAG, "API call failed!")
                attachView()
            }

        })
    }
}