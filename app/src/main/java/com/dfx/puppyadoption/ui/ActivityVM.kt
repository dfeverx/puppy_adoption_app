package com.dfx.puppyadoption.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dfx.puppyadoption.entity.Feed
import com.dfx.puppyadoption.entity.Puppy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityVM @Inject constructor(val state: SavedStateHandle) : ViewModel() {
    val feed: LiveData<FeedAll> get() = _feed

    companion object {

        private val _feed: MutableLiveData<FeedAll>
                by lazy {
                    MutableLiveData<FeedAll>()
                }

        val puppies = listOf(
            Puppy(
                1,
                "Redfish white little puppy ,cool!",
                "https://freepngimg.com/thumb/dog/40-dog-png-image.png",
                "Lorem test new test ",
                12,
            ),
            Puppy(
                2,
                "Yellowish white cute puppy",
                "https://freepngimg.com/thumb/dog/1-2-dog-png-10.png",

                "Lorem test new test ",
                15,
            ),
            Puppy(
                3,
                "White and yellow mixed color puppy",
                "https://freepngimg.com/thumb/dog/27-dog-png-image.png",
                "Lorem test new test ",
                50,
            ),
            Puppy(
                4,
                "Mr cool teddy bear like puppy",
                "https://freepngimg.com/thumb/dog/3-dog-png-image-picture-download-dogs.png",
                "Lorem test new test ",
                65,
            ),
            Puppy(
                5,
                "Beautiful sweet puppy",
                "https://freepngimg.com/thumb/dog/31-dog-png-image-picture-download-dogs.png",
                "Lorem test new test ",
                50,
            ),
            Puppy(
                6,
                "Lovely cool puppy",
                "https://freepngimg.com/thumb/dog/59-dog-png-image.png",
                "Lorem test new test ",
                50,
            ),
            Puppy(
                7,
                "Baby puppy sweet heart",
                "https://freepngimg.com/thumb/dog/9-dog-png-image-picture-download-dogs.png",
                "Lorem test new test ",
                48,
            ),
            Puppy(
                7,
                "Beautiful sweet puppy",
                "https://freepngimg.com/thumb/dog/59-dog-png-image.png",
                "Lorem test new test ",
                22,
            ),
        )
    }

    init {
        _feed.postValue(demoData())
    }


    private fun demoData(): FeedAll {
        return FeedAll(
            Feed.MainHeader("Cute puppy of the day winner Shylie ", puppies[0]),
            Feed.Recommended(puppies.subList(1, 6)),
            Feed.More(puppies.subList(0, 3)),
            Feed.Liked(puppies.subList(2, 4)),
        )
    }

    data class FeedAll(
        val header: Feed.MainHeader,
        val recommended: Feed.Recommended,
        val more: Feed.More,
        val liked: Feed.Liked,

        )

    fun findOneWithId(id: Long = 1): Puppy? {
        return puppies.find {
            it.id == id
        }
    }
}


