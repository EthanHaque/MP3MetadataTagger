package com.ethanhaque.lyric.UpdateLyrics;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class AddLyricsToMP3 {

	/**
	 * Main method for AddLyricsToMP3 class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		AddLyricsToMP3 lyrics = new AddLyricsToMP3();
		System.out.println(lyrics.getMP3Lyrics(
				"C:\\Users\\Ethan_H_Laptop\\base\\programs\\JAVA\\LyricSearch\\assets\\MP3\\no lyrics\\01 Bark At The Moon.mp3"));
		lyrics.addLyrics(
				"C:\\Users\\Ethan_H_Laptop\\base\\programs\\JAVA\\LyricSearch\\assets\\MP3\\no lyrics\\01 Bark At The Moon.mp3",
				true);
		System.out.println(lyrics.getMP3Lyrics(
				"C:\\Users\\Ethan_H_Laptop\\base\\programs\\JAVA\\LyricSearch\\assets\\MP3\\no lyrics\\01 Bark At The Moon.mp3"));
	}

	/**
	 * Constructor for AddLyricsToMP3 class.
	 */
	public AddLyricsToMP3() {
		Logger logger = Logger.getLogger("org.jaudiotagger.tag.datatype");
		logger.setLevel(Level.SEVERE);
	}

	/**
	 * Adds lyrics to MP3 file.
	 * 
	 * @param path    - path to MP3 file.
	 * @param replace - if true, replaces any lyrics that are already in the file.
	 */
	public boolean addLyrics(String path, boolean replace) {
		String artist = getMP3Artist(path);
		String title = getMP3Title(path);
		String lyrics = getMP3Lyrics(path);
		if (replace) {
			LyricScraper fetcher = new LyricScraper();
			String fetchedLyrics = fetcher.generateLyrics(artist, title);
			if (fetchedLyrics != null) {
				removeMP3Lyrics(path);
				setMP3Lyrics(path, fetchedLyrics);
				return true;
			}
		} else {
			if (lyrics == null || lyrics.equals("")) {
				LyricScraper fetcher = new LyricScraper();
				String fetchedLyrics = fetcher.generateLyrics(artist, title);
				if (fetchedLyrics != null) {
					removeMP3Lyrics(path);
					setMP3Lyrics(path, fetchedLyrics);
					return true;
				}
			}

		}
		return false;

	}

	/**
	 * Removes lyrics from MP3 file
	 * 
	 * @param path - path to MP3 file.
	 * @return
	 */
	private boolean removeMP3Lyrics(String path) {
		if (path != null) {
			File file = new File(path);
			AudioFile audioFile = null;
			Tag tag = null;
			if (file.exists()) {
				try {
					audioFile = AudioFileIO.read(file);
					if (audioFile != null) {
						tag = audioFile.getTag();
						if (tag != null) {
							tag.deleteField(FieldKey.LYRICS);
							audioFile.setTag(tag);
							try {
								AudioFileIO.write(audioFile);
							} catch (Exception e) {
								return false;
							}
							return true;
						}
					}
				} catch (CannotReadException | ReadOnlyFileException | InvalidAudioFrameException | TagException
						| IOException e) {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * Sets lyrics metadata to specified string.
	 * 
	 * @param path   - path to MP3 file.
	 * @param lyrics - string to write into lyrics metadata.
	 * @return
	 */
	private boolean setMP3Lyrics(String path, String lyrics) {
		if (path != null && lyrics != null) {
			File file = new File(path);
			AudioFile audioFile = null;
			Tag tag = null;
			if (file.exists()) {
				try {
					audioFile = AudioFileIO.read(file);
					if (audioFile != null) {
						tag = audioFile.getTag();
						if (tag != null) {
							tag.setField(FieldKey.LYRICS, lyrics);
							audioFile.setTag(tag);
							try {
								AudioFileIO.write(audioFile);
							} catch (Exception e) {
								return false;
							}
							return true;
						}
					}
				} catch (CannotReadException | ReadOnlyFileException | InvalidAudioFrameException | TagException
						| IOException e) {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * Get lyrics from metadata.
	 *
	 * @param path - path to the file.
	 * @return
	 */
	private String getMP3Lyrics(String path) {
		String lyrics;
		if (path != null) {
			File file = new File(path);
			AudioFile audioFile = null;
			Tag tag = null;
			if (file.exists()) {
				try {
					audioFile = AudioFileIO.read(file);
					if (audioFile != null) {
						tag = audioFile.getTag();
						if (tag != null) {
							lyrics = tag.getFirst(FieldKey.LYRICS);
							if (!lyrics.equals("")) {
								return lyrics;
							}
						} else {
							return null;
						}
					}
				} catch (CannotReadException | ReadOnlyFileException | InvalidAudioFrameException | TagException
						| IOException e) {
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Get artist from metadata.
	 *
	 * @param path - path to the file.
	 * @return
	 */
	private String getMP3Artist(String path) {
		String artist;
		if (path != null) {
			File file = new File(path);
			AudioFile audioFile = null;
			Tag tag = null;
			if (file.exists()) {
				try {
					audioFile = AudioFileIO.read(file);
					if (audioFile != null) {
						tag = audioFile.getTag();
						if (tag != null) {
							artist = tag.getFirst(FieldKey.ARTIST);
							if (!artist.equals("")) {
								return artist;
							}
						} else {
							return null;
						}

					}
				} catch (CannotReadException | ReadOnlyFileException | InvalidAudioFrameException | TagException
						| IOException e) {
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Get title from metadata
	 *
	 * @param path - path to the file.
	 * @return
	 */
	private String getMP3Title(String path) {
		String title;
		if (path != null) {
			File file = new File(path);
			AudioFile audioFile = null;
			Tag tag = null;
			if (file.exists()) {
				try {
					audioFile = AudioFileIO.read(file);
					if (audioFile != null) {
						tag = audioFile.getTag();
						if (tag != null) {
							title = tag.getFirst(FieldKey.TITLE);
							if (!title.equals("")) {
								return title;
							}
						} else {
							return null;
						}

					}
				} catch (CannotReadException | ReadOnlyFileException | InvalidAudioFrameException | TagException
						| IOException e) {
					return null;
				}
			}
		}
		return null;
	}

}
