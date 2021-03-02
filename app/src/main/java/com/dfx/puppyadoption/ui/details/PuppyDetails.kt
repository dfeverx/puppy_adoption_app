package com.dfx.puppyadoption.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import coil.transform.CircleCropTransformation
import com.dfx.puppyadoption.MyApplication
import com.dfx.puppyadoption.R
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
class PuppyDetails : Fragment() {
    @Inject
    lateinit var application: MyApplication


    private val activityVM: ActivityVM by activityViewModels()
    private val viewModelPuppyDetails: PuppyDetailsVM by viewModels()

    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                ComposeStarterTheme(darkTheme = application.isDarkTheme.value) {
                    SystemUi(requireActivity().window, application.isDarkTheme.value)
                    PuppyDetailsContent(activityVM.findOneWithId(viewModelPuppyDetails.puppyId())) {
                        requireActivity().onBackPressed()
                    }
                }
            }
        }
    }
}


@ExperimentalAnimationApi
@Composable
fun PuppyDetailsContent(puppy: Puppy?, onBackPressed: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)
    ) {
        val listState = rememberLazyListState()
        val showInList = listState.firstVisibleItemIndex > 0
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                MyToolbar(
                    onBackPressed = onBackPressed,
                )
            }
            item {
                PuppyImage(puppy!!)
            }
            item {

                PuppyDetails(puppy!!)
            }
            item {
                AnimatedVisibility(!showInList) {
                    AdoptionLayout()
                }
            }
            item {
                BoxRow(
                    listOf(
                        Pair("Weight", "3.5kg"),
                        Pair("Height", "60cm"),
                        Pair("Age", "2year"),
                    )
                )
            }
            item {

                PuppyOwnerInfo()
            }
            item {

                PuppyBio()
            }


        }
        /*Fixed Layout at bottom while scroll*/
        AnimatedVisibility(showInList) {
            AdoptionLayout()
        }

    }

}

@ExperimentalAnimationApi
@Composable
fun AdoptionLayout() {
    var isFav by remember { mutableStateOf(false) }
    Box(
        Modifier
            .fillMaxWidth()
            .height(82.dp)
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {


        Row {

            ToggleHeart(modifier = Modifier.size(56.dp), state = isFav,
                update = {
                    isFav = it
                })
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.onBackground,
                    contentColor = MaterialTheme.colors.background,

                    ),
                shape = RoundedCornerShape(24.dp),
                onClick = { },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxSize()
                    .weight(1f),
//                                .background(Color.Black, RoundedCornerShape(32.dp)

            ) {
                Text(text = "Adopt now")
            }
        }
    }

}


@Composable
fun PuppyImage(puppy: Puppy) {
    var dynamicColor by remember { mutableStateOf<DynamicColor?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Box(
        Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
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
                .fillMaxSize()
//                .height(150.dp)
//                    .border(2.dp, Color.Cyan)


                .background(
                    if (dynamicColor == null) MaterialTheme.colors.surface else Color(dynamicColor!!.backgroundLight),
                    RoundedCornerShape(
                        topStart = 600.dp,
                        topEnd = 8.dp,
                        bottomEnd = 8.dp,
                        bottomStart = 8.dp
                    )
                )
        )
        /*Main Img*/
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
        ) {
            CoilImage(
                data = puppy.img,
                contentDescription = "My content description"
            )
        }
    }
}

@Composable
fun PuppyDetails(puppy: Puppy) {
    Column(Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
        Text(
            text = puppy.title,
            Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme
                .colors.onBackground
        )
        Text(
            text = "Gender: Male",
            Modifier.padding(bottom = 8.dp),
            style = Typography.caption,
            color = MaterialTheme
                .colors.onBackground
        )
        Text(
            text = "$ ${puppy.price}",
            Modifier,
            style = MaterialTheme.typography.h1,
            color = MaterialTheme
                .colors.onBackground
        )

    }
}

@Composable
fun PuppyOwnerInfo(
    ownerName: String = "John doe",
    ownerImg: String = "https://images.pexels.com/users/avatars/215051/elle-hughes-607.jpeg?auto=compress&fit=crop&h=256&w=256"
) {

    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)

    ) {
        Text(
            text = stringResource(R.string.owner_info),
            Modifier.padding(bottom = 8.dp), style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onBackground
        )
        Row(
            Modifier
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    color = Color.LightGray
                )
                .clickable { }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CoilImage(
                data = ownerImg,
                contentDescription = null,
                Modifier
                    .size(42.dp)
                    .padding(4.dp),
                requestBuilder = {
                    transformations(CircleCropTransformation())
                },
                loading = {
                    Box(Modifier.matchParentSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }

            )
            Text(
                text = ownerName,
                Modifier
                    .padding(vertical = 16.dp)
                    .padding(start = 8.dp),
                color = MaterialTheme.colors.onBackground, style = MaterialTheme.typography.h3

            )
            Box(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.Call,
                contentDescription = null,
                Modifier
                    .size(50.dp)
                    .padding(8.dp),
                tint = MaterialTheme.colors.onBackground,
            )

        }
    }

}

@Composable
fun BoxItemDetails(label: String, value: String, modifier: Modifier) {

    Box(
        modifier = modifier


    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = label,
                style = Typography.caption,
                color = MaterialTheme.colors.onBackground
            )
        }
    }

}

@Composable
fun BoxRow(boxDetails: List<Pair<String, String>>) {
    Row(
        Modifier
            .padding(12.dp)
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        boxDetails.forEach {
            BoxItemDetails(
                label = it.first, value = it.second, modifier = Modifier
                    .padding(4.dp)
                    .weight(1f)
                    .fillMaxSize()
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colors.surface
                    )

            )
        }
    }
}


@Composable
fun PuppyBio() {

    Column(Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "About my puppy",
            Modifier.padding(bottom = 8.dp), style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onBackground
        )
        Row(
            Modifier,

            verticalAlignment = Alignment.CenterVertically,
        ) {

            Text(
                text = "The puppy looks like he is " +
                        "smiling and you can imagine he is having a " +
                        "nice puppy dream. 2) A puppy is standing on her hind-legs and is using" +
                        " her front-paws to lean on a small puppy bar. 3) A puppy is standing on her hind-legsA puppy is standing on her hind-legs and is using" +
                        " her front-paws to lean on a small puppy bar.The puppy looks like he is " +
                        " her front-paws to lean on a small puppy bar.The puppy looks like he is " +
                        " her front-paws to lean on a small puppy bar.The puppy looks like he is " +
                        "smiling and you can imagine he is having a " +
                        "nice puppy dream. 2) A puppy is standing on her hind-legs and is using" +
                        " her front-paws to lean on a small puppy bar. 3) A puppy is standing on her hind-legsA puppy is standing on her hind-legs and is using" +
                        " her front-paws to lean on a small puppy bar."/*, Modifier.padding(vertical = 16.dp)*/,
                color = MaterialTheme.colors.onBackground
            )

        }
    }

}

@Composable
fun OwnerLocation() {

    Column(Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "About my puppy",
            Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colors.onBackground
        )
        Row(
            Modifier,

            verticalAlignment = Alignment.CenterVertically,
        ) {

            CoilImage(
                data = "ownerImg",
                contentDescription = null,
                Modifier
                    .size(42.dp)
                    .padding(4.dp),
                requestBuilder = {
                    transformations(CircleCropTransformation())
                },
                loading = {
                    Box(Modifier.matchParentSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }

            )

        }
    }

}