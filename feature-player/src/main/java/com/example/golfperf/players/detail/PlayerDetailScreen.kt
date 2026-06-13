package com.example.golfperf.players.detail

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.golfperf.domain.model.Shot
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PlayerDetailScreen(
    uiState: StateFlow<DetailUiState>,
    modifier: Modifier = Modifier,
) {
    val state by uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = "Average Stats",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )

        StatBar(
            label = "Avg Ball Speed",
            value = state.avgSpeed,
            maxValue = 200.0,
            unit = "mph",
            modifier = Modifier.padding(top = 16.dp),
        )

        StatBar(
            label = "Avg Carry",
            value = state.avgDistance,
            maxValue = 350.0,
            unit = "yds",
            modifier = Modifier.padding(top = 12.dp),
        )

        Text(
            text = "Shots",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(
                items = state.shots,
                key = { it.id },
            ) { shot ->
                ShotRow(shot = shot)
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                )
            }
        }
    }
}

@Composable
private fun StatBar(
    label: String,
    value: Double,
    maxValue: Double,
    unit: String,
    modifier: Modifier = Modifier,
) {
    val targetFraction = if (maxValue <= 0.0) {
        0f
    } else {
        (value / maxValue).toFloat().coerceIn(0f, 1f)
    }

    val animatedFraction by animateFloatAsState(
        targetValue = targetFraction,
        animationSpec = tween(
            durationMillis = 900,
            easing = FastOutSlowInEasing,
        ),
        label = "statBarFill",
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "$label: ${"%.1f".format(value)} $unit",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedFraction)
                    .background(MaterialTheme.colorScheme.primary),
            )
        }
    }
}

@Composable
private fun ShotRow(
    shot: Shot,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
    ) {
        Text(
            text = shot.clubType,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = "${"%.1f".format(shot.ballSpeed)} mph · " +
                "${"%.1f".format(shot.launchAngle)}° · " +
                "${"%.1f".format(shot.carryDistance)} yds · " +
                "${shot.spinRate} rpm",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}
