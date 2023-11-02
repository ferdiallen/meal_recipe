package com.ferdialif.mealrecipe.core.utils

fun String.toYoutubeEmbedded(): String {
    val findValidVideoId = this.substringAfterLast("v=")
    return "<iframe width=\"100%\" height=\"100%\" " +
            "src=\"https://www.youtube.com/embed/$findValidVideoId\" " +
            "title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; " +
            "clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" " +
            "allowfullscreen></iframe>"
}