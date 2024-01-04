package nl.skindustries.testapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.skindustries.testapp.ui.theme.TestAppTheme
import java.util.logging.Logger

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val scrollState = rememberScrollState()
            val horizontalScrollState = rememberScrollState()

            val lazyRowState = rememberLazyListState()

            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()

            var clickCount by rememberSaveable { mutableIntStateOf(0) }

            TestAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(52.dp))

                        Text(
                            "Clicks: $clickCount",
                            style = MaterialTheme.typography.displayLarge,
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = {
                                clickCount += 1
                                Log.d("MainActivity", "Button clicked")
                            },
                            interactionSource = interactionSource,
                            modifier = Modifier.fillMaxWidth()
                                .pointerInput(Unit) {
                                    Log.d("MainActivity", "pointerInput Button pressed")
                                }
                        ) {
                            Text("Click me")
                        }

                        Spacer(modifier = Modifier.height(32.dp))


                        Text(
                            text = "Click me text",
                            modifier = Modifier.fillMaxWidth()
                                .height(50.dp)
                                .border(2.dp, Color.Red)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = {
                                            clickCount += 1
                                        }
                                    )
                                }
                        )

                        Spacer(modifier = Modifier.height(16.dp))


                        LazyRow(
                            state = lazyRowState,
                        ) {
                            items(100) {
                                Column(
                                    modifier = Modifier
                                        .height(100.dp)
                                        .border(2.dp, Color.Red)

                                ) {
                                    Text("Item $it")
                                }
                            }
                        }
//
//                        Row(
//                            modifier = Modifier
//                                .horizontalScroll(horizontalScrollState)
//                                .fillMaxWidth(),
//
//                            ) {
//                            repeat(100){
//                                Column(
//                                    modifier = Modifier
//                                        .height(100.dp)
//                                        .border(2.dp, Color.Red)
//
//                                ) {
//                                    Text("Item $it")
//                                }
//                            }
//                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            items(100) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .border(2.dp, Color.Red)

                                ) {
                                    Text("Item $it")
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestAppTheme {
        Greeting("Android")
    }
}