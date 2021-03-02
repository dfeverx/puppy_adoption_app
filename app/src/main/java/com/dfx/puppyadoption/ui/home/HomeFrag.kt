package com.dfx.puppyadoption.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asFlow
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dfx.puppyadoption.MyApplication
import com.dfx.puppyadoption.R
import com.dfx.puppyadoption.entity.Feed
import com.dfx.puppyadoption.entity.Puppy
import com.dfx.puppyadoption.ui.ActivityVM
import com.dfx.puppyadoption.ui.theme.ComposeStarterTheme
import com.dfx.puppyadoption.ui.theme.Typography
import com.dfx.puppyadoption.ui.utils.*
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeFrag : Fragment() {
    @Inject
    lateinit var application: MyApplication

        private val mainViewModel: ActivityVM by activityViewModels()


    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                ComposeStarterTheme(darkTheme = application.isDarkTheme.value) {
                    SystemUi( requireActivity().window,application.isDarkTheme.value)
                    val feeds: State<ActivityVM.FeedAll?> =
                        mainViewModel.feed.asFlow().collectAsState(initial = null)
                    feeds.value?.let {
                        val header = it.header
                        val recommended = it.recommended
                        val more = it.more

                        PuppyList(header, recommended, more, changeTheme = {
                            application.changeTheme()
                        })

                    }
                }
            }
        }
    }

    @ExperimentalAnimationApi
    @Composable
    fun PuppyList(
        header: Feed.MainHeader,
        recommended: Feed.Recommended,
        more: Feed.More, changeTheme: (() -> Unit)? = null,
    ) {
        LazyColumn(Modifier.background(MaterialTheme.colors.secondary)) {
            item {

                WelcomeUser(
                    "John doe",
                    "Good Morning",
                    isHome = true,
                    changeTheme = changeTheme
                )
            }
            item {
                HeaderItemCard(
                    findNavController(),
                    header.title,
                    header.puppy
                )
            }
            item {
                HScrollRecommended(
                    row = recommended,
                    navController = findNavController(),
                )
            }

            item {
                Text(
                    text = stringResource(R.string.more_puppies),
                    Modifier.padding(bottom = 8.dp, start = 16.dp, top = 8.dp),
                    color = MaterialTheme.colors.onBackground
                )
            }
            items(more.pappies) { puppy ->

                ItemFullWidthItem(puppy, findNavController())
            }

        }
    }
}

@Composable
fun HeaderItemCard(findNavController: NavController, title: String, puppy: Puppy) {
    var dynamicColor by remember { mutableStateOf<DynamicColor?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Box(
        Modifier
            .fillMaxWidth()
            .height(170.dp)
    ) {

        /*Main container*/
        Box(
            Modifier
                .fillMaxWidth()
                .height(170.dp)

        )
        {
            scope.launch(Dispatchers.Default) {

                val _dynamicColor = calculateSwatchesInImage(
                    context,
                    puppy.img
                )

                dynamicColor = _dynamicColor


            }
/*BG*/
            Box(
                Modifier
                    .padding(16.dp)
                    .clickable {
                        findNavController.navigate(
                            HomeFragDirections.actionHomeFragToPuppyDetails(
                                puppy.id
                            )
                        )
                    }
                    .fillMaxSize()
                    .background(
                        if (dynamicColor == null) MaterialTheme.colors.surface else Color(
                            dynamicColor!!.backgroundLight
                        ),
                        RoundedCornerShape(8.dp)
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()

            ) {/*Left*/
                Box(
                    Modifier
                        .weight(2f)
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(
                            text = title,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            color = if (dynamicColor == null) Color.Black else Color(dynamicColor!!.textLight),
                            style = Typography.h6
                        )

                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Black,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp),
                            onClick = { },
                            modifier = Modifier
                                .padding(top = 16.dp),
//                                .background(Color.Black, RoundedCornerShape(32.dp)

                        ) {
                            Text(text = stringResource(R.string.view))
                        }
                    }
                }
                /*Right*/
                Box(
                    Modifier
                        .padding(top = 76.dp)
                        .width(120.dp)
                        .height(170.dp)

                ) {


                    CoilImage(
                        data = puppy.img,
                        contentDescription = "Puppy image"
                    )
                }
            }
        }

    }
}

@ExperimentalAnimationApi
@Composable
fun SmallItemCard(findNavController: NavController, puppy: Puppy) {
    var isFav by remember { mutableStateOf(false) }
    var dynamicColor by remember { mutableStateOf<DynamicColor?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Box(
        Modifier
            .width(164.dp)
            .height(230.dp)
    ) {
        scope.launch(Dispatchers.Default) {

            val _dynamicColor = calculateSwatchesInImage(
                context,
                puppy.img
            )

            dynamicColor = _dynamicColor


        }

        /*Main container*/
        Box(
            Modifier
                .fillMaxSize()

        )
        {

            /*BG*/
            Box(
                Modifier
                    .padding(end = 16.dp, top = 8.dp, bottom = 16.dp)
                    .fillMaxSize()

                    .background(
                        if (dynamicColor == null) MaterialTheme.colors.surface else Color(
                            dynamicColor!!.backgroundLight
                        ),
                        RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        findNavController.navigate(
                            HomeFragDirections.actionHomeFragToPuppyDetails(
                                puppy.id
                            )
                        )
                    }
            )
            Column {
                /*top*/
                Box(
                    Modifier
                        .weight(1.6f)
                        .fillMaxSize()
                        .padding(top = 32.dp, start = 16.dp, end = 32.dp)
//                        .border(2.dp, Color.Red)
                ) {
                    Text(
                        text = puppy.title,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = Typography.h2,
                        color = if (dynamicColor == null) MaterialTheme.colors.onSurface else Color(
                            dynamicColor!!.textLight
                        )
                    )
                }
                /*Bottom*/
                Box(
                    Modifier
                        .weight(2f)
                        .fillMaxSize()
//                        .border(2.dp, Color.Green)
                ) {
                    Row {
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .padding(/*start = 16.dp, top = 32.dp, end = 32.dp, bottom = 32.dp*/
                                    16.dp
                                )
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Bottom
                        ) {

                            ToggleHeart(state = isFav,
                                update = {
                                    isFav = it
                                })
                        }


                        /*Img*/
                        Box(
                            Modifier
                                .weight(1f)
                                .fillMaxSize()
//                                .border(2.dp, Color.Magenta)
                        ) {
                            CoilImage(
                                data = puppy.img,
                                contentDescription = null
                            )
                        }
                    }
                }
            }

        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ItemFullWidthItem(puppy: Puppy, findNavController: NavController) {
    var isFav by remember { mutableStateOf(false) }
    Row(
        Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(MaterialTheme.colors.surface, RoundedCornerShape(8.dp))
            .clickable {
                findNavController.navigate(
                    HomeFragDirections.actionHomeFragToPuppyDetails(
                        puppy.id
                    )
                )
            }
            .padding(16.dp))
    {
        /*Img*/
        Box(
            Modifier
                .width(76.dp)
                .height(76.dp)
        ) {
            CoilImage(

                data = puppy.img,
                contentDescription = null,
                Modifier
                    .fillMaxSize()
                    .padding(4.dp),
            )
        }
        /*Texts*/
        Box(
            Modifier
                .weight(1f)
                .height(76.dp)

        ) {
            Column {
                Text(
                    text = puppy.title,
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    text = "$${puppy.price}",
                    style = Typography.h6,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
        /*action*/
        Box(
            Modifier
                .width(76.dp)
                .height(76.dp)

        ) {
            ToggleHeart(modifier = Modifier.padding(16.dp), state = isFav,
                update = {
                    isFav = it
                })
        }

    }

}


@ExperimentalAnimationApi
@Composable
fun HScrollRecommended(row: Feed.Recommended, navController: NavController) {
    Column {
        Text(
            text = stringResource(R.string.only_for_you),
            Modifier.padding(bottom = 8.dp, start = 16.dp),
            color = MaterialTheme.colors.onBackground
        )
        LazyRow(contentPadding = PaddingValues(start = 16.dp)) {
            items(row.pappies) { puppy ->
                SmallItemCard(
                    findNavController = navController,
                    puppy
                )
            }
        }
    }


}

