package com.dfx.puppyadoption.ui.home
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun AlignInRow() {
    Row(
        modifier = Modifier
            .size(150.dp)
            .background(Color.Yellow),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(50.dp)
                .background(Color.Red))
        Box(
            Modifier
                .size(50.dp)
                .background(Color.Blue))
    }
}



    @Composable
    fun ConstraintLayoutContent() {
        ConstraintLayout {
            // Create references for the composables to constrain
            val (button, text) = createRefs()

            Button(
                onClick = { /* Do something */ },
                // Assign reference "button" to the Button composable
                // and constrain it to the top of the ConstraintLayout
                modifier = Modifier.constrainAs(button) {
                    top.linkTo(parent.top, margin = 16.dp)
                }
            ) {
                Text("Button")
            }

            // Assign reference "text" to the Text composable
            // and constrain it to the bottom of the Button composable
            Text("Text",
                Modifier
                    .padding(8.dp)
                    .constrainAs(text) {
                        top.linkTo(button.bottom, margin = 16.dp)
                    })
        }

}




@Preview
@Composable
fun PreviewLimitless() {
    ConstraintLayoutContent()
}


