package com.ethanhaque.lyric.UpdateLyrics;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class LyricScraper {
	private static final int timeout = 7 * 1000;

	/**
	 * Main method for LyricScraper class
	 * 
	 * @param args - arguments passed to command line.
	 * @throws IOException
	 * 
	 */
	public static void main(String[] args) throws IOException {
		LyricScraper lyricScraper = new LyricScraper();
		System.out.println(lyricScraper.generateLyrics("Ozzy Osbourne", "Bark at the Moon"));
	}

	/**
	 * Constructor for LyricScraper class
	 */
	public LyricScraper() {

	}

	/**
	 * 
	 * Puts everything together by getting links and parsed HTML for given artist
	 * and song title.
	 * 
	 * @param artist - name of the artist.
	 * @param title  - name of the song.
	 * @return String containing lyrics for given artist and song.
	 * 
	 * 
	 */
	public String generateLyrics(String artist, String title) {
		HashMap<String, String> links = buildLinks(artist, title);
		String lyrics = grabLyrics(links);
		return lyrics.trim();
	}

	/**
	 * Creates ArrayList with strings of all the links from working sites as of the
	 * end of May 2020. Doesn't work with chars outside of lower and upper a-z and
	 * numbers 0-9.
	 * 
	 * @param artist - name of the artist.
	 * @param title  - name of the song
	 * @return ArrayList of type string with links
	 * 
	 */
	private HashMap<String, String> buildLinks(String artist, String title) {
		artist.trim();
		title.trim();
		HashMap<String, String> links = new HashMap<String, String>();
		boolean tryAZLyrics = true;
		boolean tryLyricsFandom = true;
		boolean trySeekALyric = true;
		boolean tryELyrics = true;
		boolean tryMetroLyrics = true;
		boolean trySongColeta = true;

		if (tryAZLyrics) {
			links.put("AZLyrics", buildAZLyricsLink(artist, title));
		}
		if (tryLyricsFandom) {
			links.put("LyricsFandom", buildLyricsFandom(artist, title));
		}
		if (trySeekALyric) {
			links.put("SeekALyric", buildSeekALyric(artist, title));
		}
		if (tryELyrics) {
			links.put("ELyrics", buildELyrics(artist, title));
		}
		if (tryMetroLyrics) {
			links.put("MetroLyrics", buildMetroLyrics(artist, title));
		}
		if (trySongColeta) {
			links.put("SongColeta", buildSongColeta(artist, title));
		}
		return links;
	}
	
	/**
	 * Builds link given artist title.
	 * 
	 * @param artist
	 * @param title
	 * @return link for website.
	 */
	private String buildAZLyricsLink(String artist, String title) {
		String link = "";
		String base = "http://www.azlyrics.com/lyrics";
		String processedArtist = artist.toLowerCase().replaceAll("[^a-z0-9]", "");
		String processedTitle = title.toLowerCase().replaceAll("[^a-z0-9]", "");
		link = base + "/" + processedArtist + "/" + processedTitle + ".html";
		return link;
	}
	
	/**
	 * Builds link given artist title.
	 * 
	 * @param artist
	 * @param title
	 * @return link for website.
	 */
	private String buildLyricsFandom(String artist, String title) {
		String link = "";
		String base = "https://lyrics.fandom.com/wiki";
		String processedArtist = artist.replaceAll("[^a-zA-Z0-9\\s+]", "").replaceAll("\\s+", "_");
		String processedTitle = title.replaceAll("[^a-zA-Z0-9\\s+]", "").replaceAll("\\s+", "_");
		link = base + "/" + processedArtist + ":" + processedTitle;
		return link;
	}
	
	/**
	 * Builds link given artist title.
	 * 
	 * @param artist
	 * @param title
	 * @return link for website.
	 */
	private String buildSeekALyric(String artist, String title) {
		String link = "";
		String base = "http://www.seekalyric.com/song";
		String processedArtist = artist.toLowerCase().replaceAll("[^a-z0-9\\s+-]", "").replaceAll("\\s+-", "_");
		String processedTitle = title.toLowerCase().replaceAll("[^a-z0-9\\s+-]", "").replaceAll("\\s+-", "_");
		link = base + "/" + processedArtist + "/" + processedTitle;
		return link;
	}
	
	/**
	 * Builds link given artist title.
	 * 
	 * @param artist
	 * @param title
	 * @return link for website.
	 */
	private String buildELyrics(String artist, String title) {
		String link = "";
		String base = "http://www.elyrics.net/read";
		String processedArtist = artist.toLowerCase().replaceAll("[^a-z0-9\\s+']", "").replaceAll("'", "_")
				.replaceAll("\\s+", "-");
		String processedTitle = title.toLowerCase().replaceAll("[^a-z0-9\\s+']", "").replaceAll("'", "_")
				.replaceAll("\\s+", "-");
		link = base + "/" + processedArtist.substring(0, 1) + "/" + processedArtist + "-lyrics/" + processedTitle
				+ "-lyrics.html";
		return link;
	}
	
	/**
	 * Builds link given artist title.
	 * 
	 * @param artist
	 * @param title
	 * @return link for website.
	 */
	private String buildMetroLyrics(String artist, String title) {
		String link = "";
		String base = "https://www.metrolyrics.com";
		String processedArtist = artist.toLowerCase().replaceAll("[^a-z0-9\\s+]", "").replaceAll("\\s+", "-");
		String processedTitle = title.toLowerCase().replaceAll("[^a-z0-9\\s+]", "").replaceAll("\\s+", "-");
		link = base + "/" + processedTitle + "-lyrics-" + processedArtist + ".html";
		return link;
	}
	
	/**
	 * Builds link given artist title.
	 * 
	 * @param artist
	 * @param title
	 * @return link for website.
	 */
	private String buildSongColeta(String artist, String title) {
		String link = "";
		String base = "http://www.songcoleta.com/lyrics";
		String processedArtist = artist.toLowerCase().replaceAll("[^a-z0-9\\s+]", "").replaceAll("\\s+", "_");
		String processedTitle = title.toLowerCase().replaceAll("[^a-z0-9\\s+]", "").replaceAll("\\s+", "_");
		link = base + "/" + processedTitle + "_(" + processedArtist + ")";
		return link;
	}

	/**
	 * 
	 * @param links - map of links containing the name of the site and the link to
	 *              the site.
	 * @return String containing parsed lyrics for a set of links.
	 * 
	 *         Takes set of links and cleans up the HTML responses. Stops at first
	 *         instance proper response.
	 */
	private String grabLyrics(HashMap<String, String> links) {
		for (HashMap.Entry<String, String> entry : links.entrySet()) {
			if (entry.getKey().equals("AZLyrics"))
				try {
					return cleanAZLyricsHTML(grabAZLyricsHTML(entry.getValue()));
				} catch (Exception e) {
					continue;
				}
			if (entry.getKey().equals("MetroLyrics"))
				try {
					return cleanMetroLyricsHTML(grabMetroLyricsHTML(entry.getValue()));
				} catch (Exception e) {
					continue;
				}
			if (entry.getKey().equals("SeekALyric"))
				try {
					return cleanSeekALyricHTML(grabSeekALyricHTML(entry.getValue()));
				} catch (Exception e) {
					continue;
				}
			if (entry.getKey().equals("ELyrics"))
				try {
					return cleanELyricsHTML(grabELyricsHTML(entry.getValue()));
				} catch (Exception e) {
					continue;
				}
			if (entry.getKey().equals("SongColeta"))
				try {
					return cleanSongColetaHTML(grabSongColetaHTML(entry.getValue()));
				} catch (Exception e) {
					continue;
				}
			if (entry.getKey().equals("LyricsFandom"))
				try {
					return cleanLyricsFandomHTML(grabLyricsFandomHTML(entry.getValue()));
				} catch (Exception e) {
					continue;
				}
		}
		return null;
	}

	/**
	 * Gets the HTML for a song lyric page on AZLyrics
	 * 
	 * @param link - link to a page on AZLyrics
	 * @return HTML page for AZLyrics
	 * @throws IOException
	 * 
	 */
	private Element grabAZLyricsHTML(String link) throws IOException {
		Document doc = Jsoup.connect(link).timeout(timeout).get();
		Element lyricsHTML = doc
				.select("body > div.container.main-page > div > div.col-xs-12.col-lg-8.text-center > div:nth-child(8)")
				.first();
		return lyricsHTML;
	}

	/**
	 * Takes HTML page from AZLyrics and removes everything but the lyrics.
	 * 
	 * @param HTML - HTML page from AZLyrics to get tags removed.
	 * @return String of lyrics without HTML tags.
	 * 
	 */
	private String cleanAZLyricsHTML(Element HTML) {
		String outString = "";
		List<TextNode> nodes = HTML.textNodes();
		for (TextNode node : nodes) {
			outString += node.text().trim() + "\n";
		}
		return outString;
	}

	/**
	 * Gets the HTML for a song lyric page on MetroLyrics
	 * 
	 * @param link - link to a page on MetroLyrics
	 * @return HTML page for MetroLyrics
	 * @throws IOException
	 * 
	 */
	private Element grabMetroLyricsHTML(String link) throws IOException {
		Document doc = Jsoup.connect(link).timeout(timeout).get();
		Element lyricsHTML = doc.select("#lyrics-body-text").first();
		return lyricsHTML;
	}

	/**
	 * Takes HTML page from MetroLyrics and removes everything but the lyrics.
	 * 
	 * @param HTML - HTML page from MetroLyrics to get tags removed.
	 * @return String of lyrics without HTML tags.
	 * 
	 */
	private String cleanMetroLyricsHTML(Element HTML) {
		String outString = "";
		Elements elements = HTML.getElementsByClass("verse");
		for (Element element : elements) {
			for (TextNode node : element.textNodes()) {
				outString += node.text().trim() + "\n";
			}
		}
		return outString;
	}

	/**
	 * Gets the HTML for a song lyric page on SeekALyric
	 * 
	 * @param link - link to a page on SeekALyric
	 * @return HTML page for SeekALyric
	 * @throws IOException
	 * 
	 */
	private Element grabSeekALyricHTML(String link) throws IOException {
		Document doc = Jsoup.connect(link).timeout(timeout).get();
		Element lyricsHTML = doc.select("#contentt").first();
		return lyricsHTML;
	}

	/**
	 * Takes HTML page from SeekALyric and removes everything but the lyrics.
	 * 
	 * @param HTML - HTML page from SeekALyric to get tags removed.
	 * @return String of lyrics without HTML tags.
	 * 
	 */
	private String cleanSeekALyricHTML(Element HTML) {
		String outString = "";
		List<TextNode> nodes = HTML.textNodes();
		for (TextNode node : nodes) {
			outString += node.text().trim() + "\n";
		}
		return outString;
	}

	/**
	 * Gets the HTML for a song lyric page on ELyrics
	 * 
	 * @param link - link to a page on ELyrics
	 * @return HTML page for ELyrics
	 * @throws IOException
	 * 
	 */
	private Element grabELyricsHTML(String link) throws IOException {
		Document doc = Jsoup.connect(link).timeout(timeout).get();
		Element lyricsHTML = doc.select("#inlyr").first();
		return lyricsHTML;
	}

	/**
	 * Takes HTML page from ELyrics and removes everything but the lyrics.
	 * 
	 * @param HTML - HTML page from ELyrics to get tags removed.
	 * @return String of lyrics without HTML tags.
	 * 
	 */
	private String cleanELyricsHTML(Element HTML) {
		String outString = "";
		List<TextNode> nodes = HTML.textNodes();
		for (TextNode node : nodes) {
			outString += node.text().trim() + "\n";
		}
		return outString;
	}

	/**
	 * Gets the HTML for a song lyric page on SongColeta
	 * 
	 * @param link - link to a page on SongColeta
	 * @return HTML page for SongColeta
	 * @throws IOException
	 * 
	 */
	private Element grabSongColetaHTML(String link) throws IOException {
		Document doc = Jsoup.connect(link).timeout(timeout).get();
		Element lyricsHTML = doc.select("#lyrics").first();
		return lyricsHTML;
	}

	/**
	 * Takes HTML page from SongColeta and removes everything but the lyrics.
	 * 
	 * @param HTML - HTML page from SongColeta to get tags removed.
	 * @return String of lyrics without HTML tags.
	 * 
	 */
	private String cleanSongColetaHTML(Element HTML) {
		String outString = "";
		List<TextNode> nodes = HTML.textNodes();
		for (TextNode node : nodes) {
			outString += node.text() + "\n";
		}
		return outString;
	}

	/**
	 * Gets the HTML for a song lyric page on LyricsFandom
	 * 
	 * @param link - link to a page on LyricsFandom
	 * @return HTML page for LyricsFandom
	 * @throws IOException
	 * 
	 */
	private Element grabLyricsFandomHTML(String link) throws IOException {
		Document doc = Jsoup.connect(link).timeout(timeout).get();
		Element lyricsHTML = doc.select("#mw-content-text > div.lyricbox").first();
		return lyricsHTML;
	}

	/**
	 * Takes HTML page from LyricsFandom and removes everything but the lyrics.
	 * 
	 * 
	 * @param HTML - HTML page from LyricsFandom to get tags removed.
	 * @return String of lyrics without HTML tags.
	 * 
	 */
	private String cleanLyricsFandomHTML(Element HTML) {
		String outString = "";
		List<TextNode> nodes = HTML.textNodes();
		for (TextNode node : nodes) {
			outString += node.text().trim() + "\n";
		}
		return outString;
	}

}
