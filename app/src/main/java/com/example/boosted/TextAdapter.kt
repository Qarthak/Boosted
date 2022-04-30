package com.example.boosted

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView.*
import androidx.recyclerview.widget.RecyclerView.Adapter

class TextAdapter(private val listOfPosts : List<Post>): Adapter<ViewHolder>() {

    private val mTAG = "TextAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        when(viewType){
            R.layout.text_item_view -> {
                val view = inflater.inflate(R.layout.text_item_view, parent, false)
                return TxtViewHolder(view)
            }
            R.layout.image_item_view -> {
                val view = inflater.inflate(R.layout.image_item_view, parent, false )
                return ImageViewHolder(view)
            }
            R.layout.poll_item_view -> {
                val view = inflater.inflate(R.layout.poll_item_view, parent, false )
                return PollViewHolder(view)
            }
            else->{
                throw Exception("viewType is not valid!")
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content : Any = listOfPosts[position].content
        when(holder) {
            is TxtViewHolder -> {
                holder.postTitle.text = listOfPosts[position].title
                holder.postTxt.text = content as String

                holder.likeBtn.setOnClickListener {
                    flipLikeButtonState(holder.likeBtn)
                }

            }
            is ImageViewHolder -> {
                holder.postTitle.text = listOfPosts[position].title
                holder.postImage.setImageResource(content as Int)

                holder.likeBtn.setOnClickListener {
                    flipLikeButtonState(holder.likeBtn)
                }

            }
            is PollViewHolder -> {
                holder.postTitle.text = listOfPosts[position].title
                content as List<Poll>
                //Populating poll title and poll options to the post
                for (pollContent in content){
                    val pollButton = RadioButton(holder.postTitle.context)
                    pollButton.id = View.generateViewId()
                    pollButton.text = pollContent.choiceTitle
                    pollContent.pollID = pollButton.id
                    pollContent.radioButton = pollButton
                    Log.d(mTAG, "ID for "+pollContent.choiceTitle + " is "+pollButton.id)
                    holder.pollGroup.addView(pollButton, RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT ))
                }


                holder.pollSubmitButton.setOnClickListener{
                    val selectedButtonId = holder.pollGroup.checkedRadioButtonId
                    if(selectedButtonId >= 0){
                        holder.pollSubmitButton.isEnabled = false
                        for(pollContent in content){
                            val pollButton = pollContent.radioButton as RadioButton
                            if(pollButton.id == selectedButtonId){
                                pollContent.count += 1
                            }
                            pollButton.isEnabled = false
//                            pollButtons.text = pollButtons.text.toString() + " : " + content[pollButtons.id].count.toString() + " votes"
                        }
                    }
                }

                holder.likeBtn.setOnClickListener {
                    flipLikeButtonState(holder.likeBtn)
                }
            }


        }
    }

    private fun flipLikeButtonState(likeBtn : ImageView){
        val isSelected = likeBtn.hasTransientState()
        Log.d(mTAG, "isSelected = $isSelected")
        if(isSelected) {
            likeBtn.setImageResource(R.drawable.unpressed_like_button)
            likeBtn.setHasTransientState(false)
        }
        else {
            likeBtn.setImageResource(R.drawable.pressed_like_button)
            likeBtn.setHasTransientState(true)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(listOfPosts[position].type){
            PostType.TEXT -> R.layout.text_item_view
            PostType.IMAGE -> R.layout.image_item_view
            PostType.POLL -> R.layout.poll_item_view
        }
    }

    override fun getItemCount(): Int {
        return listOfPosts.size
    }
}

// ViewHolders for the various types of views that can be created
class TxtViewHolder(txtItemView: View):  ViewHolder(txtItemView) {
    val postTitle: TextView = txtItemView.findViewById(R.id.postTitle)
    val likeBtn: ImageView = txtItemView.findViewById(R.id.likeButton)
    val postTxt: TextView = txtItemView.findViewById(R.id.txtPost)
}

class ImageViewHolder(imageItemView: View): ViewHolder(imageItemView) {
    val postTitle: TextView = imageItemView.findViewById(R.id.postTitle)
    val likeBtn: ImageView = imageItemView.findViewById(R.id.likeButton)
    val postImage: ImageView = imageItemView.findViewById(R.id.imagePost)
}

class PollViewHolder(pollItemView: View): ViewHolder(pollItemView) {
    val postTitle: TextView = pollItemView.findViewById(R.id.postTitle)
    val likeBtn: ImageView = pollItemView.findViewById(R.id.likeButton)
    val pollGroup: RadioGroup = pollItemView.findViewById(R.id.pollPost)
    val pollSubmitButton: Button = pollItemView.findViewById(R.id.pollSubmitButton)
}