package dev.ap5.mtdapi.rss

import com.prof18.rssparser.RssParser
import com.prof18.rssparser.model.RssChannel
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.parse
import kotlin.time.Instant

object RSSFeed {
	val rssParser = RssParser()

	fun getLatestPubDate(channel : RssChannel) : Instant {
		channel.items.ifEmpty { throw IllegalStateException("RSS feed contains no items") }

		val pubDate =
			channel.items.first().pubDate
				?: throw IllegalStateException("RSS feed item has no publication date")

		return Instant.parse(pubDate, DateTimeComponents.Formats.RFC_1123)
	}

	suspend fun getLastFeedUpdate() : Instant {
		val channel : RssChannel = rssParser.getRssChannel(Constants.FEED_URL)

		return getLatestPubDate(channel)
	}
}