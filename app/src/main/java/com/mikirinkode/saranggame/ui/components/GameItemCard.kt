package com.mikirinkode.saranggame.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mikirinkode.saranggame.data.remote.response.GenresItem
import com.mikirinkode.saranggame.ui.theme.SarangGameTheme

@Composable
fun GameItemCard(
    imageUrl: String,
    title: String,
    rating: String,
    genres: String,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .background(
                color = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.medium.copy(bottomEnd = CornerSize(0.dp))
            )
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .clickable {
                    onItemClick()
                }
        ) {
            Box(modifier = Modifier) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "$title image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .background(
                            color = Color.Black.copy(alpha = 0.6f),
                            shape = CutCornerShape(topEnd = 32.dp, bottomStart = 16.dp)
                        )
                        .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating Icon",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = rating,
                        color = Color.White,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                0f to Color.Black.copy(alpha = 0.4f),
                                1000f to MaterialTheme.colors.background.copy(alpha = 0.9f),
                            ),
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(vertical = 8.dp, horizontal = 32.dp)
                ) {
                    Text(
                        text = genres,
                        color = MaterialTheme.colors.primary,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GameItemCardPreview() {
    SarangGameTheme {
        GameItemCard("", "GTA V", "8.3", "Action, Adventure", {})
    }
}