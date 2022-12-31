package com.mikirinkode.saranggame.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mikirinkode.saranggame.ui.theme.Dark400
import com.mikirinkode.saranggame.ui.theme.Dark500
import com.mikirinkode.saranggame.ui.theme.Primary500

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    val shimmerColors = listOf(
        Color.Black.copy(alpha = 0.7f),
        Primary500.copy(alpha = 0.15f),
        Color.Black.copy(alpha = 0.7f),
    )

    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .background(
                brush = brush,
                shape = MaterialTheme.shapes.medium.copy(bottomEnd = CornerSize(0.dp))
            )
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .background(
                    brush = brush,
                    shape = MaterialTheme.shapes.medium.copy(bottomEnd = CornerSize(0.dp))
                )
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Box(modifier = Modifier) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            brush = brush,
                            shape = MaterialTheme.shapes.medium.copy(bottomEnd = CornerSize(0.dp))
                        ),
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .background(
                            brush = brush,
                            shape = CutCornerShape(topEnd = 32.dp, bottomStart = 16.dp)
                        )
                        .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(24.dp)
                            .width(64.dp)
                            .background(
                                brush = brush,
                                shape = MaterialTheme.shapes.medium
                            )
                    )
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(
                            brush = brush,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(vertical = 8.dp, horizontal = 32.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .height(18.dp)
                            .fillMaxWidth()
                            .background(
                                brush = brush,
                                shape = MaterialTheme.shapes.medium
                            )
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .height(24.dp)
                            .fillMaxWidth()
                            .background(
                                brush = brush,
                                shape = MaterialTheme.shapes.medium
                            )
                    )
                }
            }
        }
    }
}