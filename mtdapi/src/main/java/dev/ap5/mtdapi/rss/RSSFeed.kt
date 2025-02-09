package dev.ap5.mtdapi.rss

import com.prof18.rssparser.RssParser
import com.prof18.rssparser.model.RssChannel
import kotlinx.datetime.Instant
import kotlinx.datetime.format.DateTimeComponents

object RSSFeed {
    val rssParser = RssParser()

    fun getLatestPubDate(channel: RssChannel) : Instant {
        val item = channel.items.firstOrNull()
        if (item == null) throw IllegalStateException("RSS feed contains no items")

        val pubDate = item.pubDate
        if (pubDate == null) throw IllegalStateException("RSS feed item has no publication date")

        return Instant.parse(pubDate, DateTimeComponents.Formats.RFC_1123)
    }

    suspend fun getLastFeedUpdate() : Instant {
        val channel : RssChannel = rssParser.getRssChannel(Constants.FEED_URL)

        return getLatestPubDate(channel)
    }
}