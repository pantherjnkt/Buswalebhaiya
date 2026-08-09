package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BusAmberPrimary
import com.example.ui.theme.BusBorderColor
import com.example.ui.theme.BusPeachHighlight
import com.example.ui.theme.BusTextLight
import kotlinx.coroutines.launch

@Composable
fun BusHornButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    clickCount: Int = 0
) {
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .testTag("bus_horn_button")
            .scale(scale.value)
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(BusAmberPrimary, Color(0xFFA05537), BusAmberPrimary)
                )
            )
            .border(2.dp, BusBorderColor, RoundedCornerShape(24.dp))
            .clickable {
                scope.launch {
                    scale.animateTo(0.9f, tween(80))
                    scale.animateTo(1.05f, tween(100))
                    scale.animateTo(1f, tween(80))
                }
                onClick()
            }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Campaign,
                contentDescription = "Bus Horn",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "HORN OK PLEASE 📯",
                fontWeight = FontWeight.Black,
                fontSize = 13.sp,
                color = Color.White,
                letterSpacing = 1.sp
            )
            if (clickCount > 0) {
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(BusPeachHighlight)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "x$clickCount",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = BusAmberPrimary
                    )
                }
            }
        }
    }
}
