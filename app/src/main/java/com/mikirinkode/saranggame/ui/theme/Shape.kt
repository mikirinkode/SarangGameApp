package com.mikirinkode.saranggame.ui.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = CutCornerShape(
        topStart = 8.dp,
        topEnd = 16.dp,
        bottomEnd = 8.dp,
        bottomStart = 16.dp
    ),
    medium = CutCornerShape(
        topStart = 16.dp,
        topEnd = 32.dp,
        bottomEnd = 16.dp,
        bottomStart = 32.dp
    ),
    large = CutCornerShape(
        topStart = 32.dp,
        topEnd = 64.dp,
        bottomEnd = 32.dp,
        bottomStart = 64.dp
    )
)