package com.nuvio.app.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nuvio.app.core.ui.NuvioShelfSection
import com.nuvio.app.features.home.HomeCatalogSection
import com.nuvio.app.features.home.MetaPreview
import com.nuvio.app.features.home.stableKey
import nuvio.composeapp.generated.resources.Res
import nuvio.composeapp.generated.resources.media_anime
import nuvio.composeapp.generated.resources.media_movies
import nuvio.composeapp.generated.resources.media_series
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

private enum class MediaShelf(val title: StringResource, val icon: ImageVector) {
    Movies(Res.string.media_movies, Icons.Rounded.Movie),
    Series(Res.string.media_series, Icons.Rounded.Tv),
    Anime(Res.string.media_anime, Icons.Rounded.AutoAwesome),
}

@Composable
internal fun HomeMediaOverview(
    sections: List<HomeCatalogSection>,
    sectionPadding: Dp,
    onPosterClick: ((MetaPreview) -> Unit)?,
    onPosterLongClick: ((MetaPreview) -> Unit)?,
) {
    val media = remember(sections) {
        sections.asSequence().flatMap { it.items.asSequence() }
            .distinctBy(MetaPreview::stableKey)
            .groupBy(MetaPreview::mediaShelf)
    }

    Column(
        modifier = Modifier.padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        for (shelf in MediaShelf.entries) {
            val entries = media[shelf].orEmpty().take(24)
            if (entries.isEmpty()) continue

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.padding(horizontal = sectionPadding),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(shelf.icon, contentDescription = null)
                    Text(stringResource(shelf.title), style = MaterialTheme.typography.titleLarge)
                }
                NuvioShelfSection(
                    title = "",
                    entries = entries,
                    rowContentPadding = PaddingValues(horizontal = sectionPadding),
                    key = MetaPreview::stableKey,
                ) { item ->
                    HomePosterCard(
                        item = item,
                        useLandscapeBackdropMode = true,
                        onClick = onPosterClick?.let { { it(item) } },
                        onLongClick = onPosterLongClick?.let { { it(item) } },
                    )
                }
            }
        }
    }
}

private fun MetaPreview.mediaShelf(): MediaShelf? = when {
    type.equals("anime", ignoreCase = true) || genres.any {
        it.equals("anime", ignoreCase = true) || it == "أنمي"
    } -> MediaShelf.Anime
    type.equals("movie", ignoreCase = true) -> MediaShelf.Movies
    type.equals("series", ignoreCase = true) || type.equals("tv", ignoreCase = true) -> MediaShelf.Series
    else -> null
}
