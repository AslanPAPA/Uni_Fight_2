package com.asl.unifight2

import android.media.MediaPlayer
import android.media.session.MediaController
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices.NEXUS_10
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.asl.unifight2.ui.theme.UniFight2Theme
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        setContent {
            ShowScreen()
        }
    }
}



@Composable
fun ShowScreen() {
    var currentScreen by remember { mutableStateOf("intro") }

    when (currentScreen) {
        "intro" -> IntroScreen(onFinish = { currentScreen = "title"})
        "title" -> TitleScreen(onFinish = { currentScreen = "game"})
        "game" -> ZagruzkaIgri()
    }
}




@Composable
fun LookDialog(onClose: () -> Unit)
{

Box(  modifier = Modifier
    .fillMaxSize(),
    contentAlignment = Alignment.Center) {
    Box(
        modifier = Modifier
            .size(width = 400.dp, height = 411.dp)
            .offset(x = (-40).dp, y = (-30).dp)
            .background(Color.Transparent)


    ) {
        Image(
            painter = painterResource(id = R.drawable.dialog),
            contentDescription = "dialog",
            modifier = Modifier.fillMaxSize()
        )
        Image(
            painter = painterResource(id = R.drawable.btn),
            contentDescription = "btn",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 57.dp, end = 20.dp)
                .clickable {
                    onClose()
                }
        )

        Text(
            text = "OK", modifier = Modifier.align(Alignment.BottomEnd)
                .padding(bottom = 60.dp, end = 60.dp),
            fontWeight = FontWeight.Bold
        )

    }
}
}

@Preview(
    showBackground = true,
    device = "spec:width=1280dp,height=650dp,orientation=landscape"
)


@Composable
fun ZagruzkaIgri()
{
    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.training) }

    LaunchedEffect(Unit) {
        mediaPlayer.start()
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .offset(0.dp))
    {



        Image(
        painter = painterResource(id = R.drawable.main_game),
        contentDescription = "StartGme",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
        )




        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End)
        {
        Box() {
            Image(
                painter = painterResource(id = R.drawable.left),
                contentDescription = "leftBar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxHeight()
            )
            Image(
                painter = painterResource(id = R.drawable.btn),
                contentDescription = "btn",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .size(150.dp)
                    .padding(top = 30.dp)

            )


            Text(text="БОЙ",
            fontSize=25.sp,
            modifier = Modifier
            .align(Alignment.BottomCenter)
                .padding(bottom = 43.dp)
                .clickable {
                    showDialog = true
                },
                fontWeight = FontWeight.Bold
            )

        }

        }



        Column(modifier = Modifier
            .offset(x = (240).dp, y = (150).dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.glskr),
                contentDescription = "StartGme",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .clickable {}
            )
            Text(text="glusker",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 10.dp)
            )

        }



        // Диалог поверх всего
        if (showDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x88000000)) // затемнение фона
                    .clickable(enabled = false) {} // блокирует клики под ним
            ) {
                LookDialog(onClose = { showDialog = false })
            }
        }
    }
}


@Composable
fun TitleScreen(onFinish: () -> Unit)
{
    LaunchedEffect(Unit) {
        delay(4000)
        onFinish()
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black),
        contentAlignment = Alignment.Center
        )
    {
        Image(
            painter = painterResource(id = R.drawable.ukitt_fight_404),
            contentDescription = "Game Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}



@Composable
fun IntroScreen(onFinish: () -> Unit) {

    val video = "android.resource://com.asl.unifight2/raw/intro".toUri()

    Box(
    modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .clickable {onFinish()})
    {


        AndroidView(
            factory = {context->
                VideoView(context).apply {
                    setVideoURI(video)
                    setOnPreparedListener { mp ->
                        mp.isLooping = false
                        start()
                    }
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black))
    }

}