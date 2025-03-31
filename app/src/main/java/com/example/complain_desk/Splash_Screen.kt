package com.example.complain_desk


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.provider.FontsContractCompat.Columns


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun splashScreen() {

    Card(
        modifier = Modifier
            .fillMaxSize()
//            .background(color = Color.Gray)
    ) {
        Column(
            modifier = Modifier.padding(top = 250.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(330.dp)
            )
            Text(text = "Complain Desk", fontSize = 30.sp, color = Color.DarkGray)
        }

        Box(
            modifier = Modifier.fillMaxSize().padding(bottom = 10.dp),
            contentAlignment = Alignment.BottomCenter,

        ) {

            Text(text = "If no One here\n", fontSize = 25.sp)
            Text(text = "We are hear for You", fontSize = 25.sp)
        }


    }


}