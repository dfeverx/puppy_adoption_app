package com.dfx.puppyadoption.ui.utils

import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.Coil
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Scale
import com.dfx.puppyadoption.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Immutable
private data class DominantColors(val bg: Color, val text: Color)


/**
 * Fetches the given [imageUrl] with [Coil], then uses [Palette] to calculate the dominant color.
 * this is inspired from jetcaster
 */
suspend fun calculateSwatchesInImage(
    context: Context,
    imageUrl: String
): DynamicColor {
    val r = ImageRequest.Builder(context)
        .data(imageUrl)
        // We scale the image to cover 128px x 128px (i.e. min dimension == 128px)
        .size(128).scale(Scale.FILL)
        // Disable hardware bitmaps, since Palette uses Bitmap.getPixels()
        .allowHardware(false)
        .build()

    val bitmap = when (val result = Coil.execute(r)) {
        is SuccessResult -> result.drawable.toBitmap()
        else -> null
    }

    return bitmap?.let {
        withContext(Dispatchers.Default) {
            val palette = Palette.Builder(bitmap)
                // Disable any bitmap resizing in Palette. We've already loaded an appropriately
                // sized bitmap through Coil
                .resizeBitmapArea(0)
                // Clear any built-in filters. We want the unfiltered dominant color
                .clearFilters()
                // We reduce the maximum color count down to 8
                .maximumColorCount(16)
                .generate()
            DynamicColor(
                palette.getLightVibrantColor(
                    ContextCompat.getColor(
                        context,
                        R.color.default_card_bg
                    )
                ),
                palette.getDarkVibrantColor(
                    ContextCompat.getColor(
                        context,
                        R.color.default_card_text
                    )
                ),
                palette.getDarkVibrantColor(ContextCompat.getColor(context, R.color.black)),
                palette.getDarkVibrantColor(ContextCompat.getColor(context, R.color.black))
            )
        }
    } ?: DynamicColor(
        ContextCompat.getColor(context, R.color.white),
        ContextCompat.getColor(context, R.color.black),
        ContextCompat.getColor(context, R.color.black),
        ContextCompat.getColor(context, R.color.black)
    )
}

@Immutable
class DynamicColor(
    /*Light*/
    val backgroundLight: Int,
    val textLight: Int,

    /*Dark*/
    val backgroundDark: Int,
    val textDark: Int
)
