/*******************************************************************************
 * Copyright (c) 2010 Maciej Kaniewski (mk@firegnom.com).
 * 
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 * 
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 * 
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software Foundation,
 *    Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 * 
 *    Contributors:
 *     Maciej Kaniewski (mk@firegnom.com) - initial API and implementation
 ******************************************************************************/
package com.firegnom.valkyrie.net;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

// TODO: Auto-generated Javadoc
// This class downloads a file from a URL.
/**
 * The Class Download.
 */
public class Download extends Observable {

	// Max size of download buffer.
	/** The Constant MAX_BUFFER_SIZE. */
	private static final int MAX_BUFFER_SIZE = 10024;

	// These are the status names.
	/** The Constant STATUSES. */
	public static final String STATUSES[] = { "Downloading", "Paused",
			"Complete", "Cancelled", "Error", "File already exist" };

	// These are the status codes.
	/** The Constant DOWNLOADING. */
	public static final int DOWNLOADING = 0;

	/** The Constant PAUSED. */
	public static final int PAUSED = 1;

	/** The Constant COMPLETE. */
	public static final int COMPLETE = 2;

	/** The Constant CANCELLED. */
	public static final int CANCELLED = 3;

	/** The Constant ERROR. */
	public static final int ERROR = 4;

	/** The Constant ERROR_FILE_EXIST. */
	public static final int ERROR_FILE_EXIST = 5;

	/** The url. */
	private final URL url; // download URL

	/** The size. */
	private int size; // size of download in bytes

	/** The downloaded. */
	private int downloaded; // number of bytes downloaded

	/** The status. */
	private int status; // current status of download

	/** The overwrite. */
	private final boolean overwrite; // current status of download

	/** The path. */
	private String path;

	/**
	 * Instantiates a new download.
	 * 
	 * @param url
	 *            the url
	 * @param path
	 *            the path
	 * @param overwrite
	 *            the overwrite
	 */
	public Download(final URL url, final String path, final boolean overwrite) {
		this.url = url;
		this.path = path;
		size = -1;
		downloaded = 0;
		status = DOWNLOADING;
		this.overwrite = overwrite;
	}

	// Constructor for Download.
	/**
	 * Instantiates a new download.
	 * 
	 * @param url
	 *            the url
	 * @param path
	 *            the path
	 * @param overwrite
	 *            the overwrite
	 * @param o
	 *            the o
	 */
	public Download(final URL url, final String path, final boolean overwrite,
			final Observer o) {
		addObserver(o);
		this.url = url;
		this.path = path;
		size = -1;
		downloaded = 0;
		status = DOWNLOADING;
		this.overwrite = overwrite;
	}

	// Cancel this download.
	/**
	 * Cancel.
	 */
	public void cancel() {
		status = CANCELLED;
		stateChanged();
	}

	// Download file.
	/**
	 * Download.
	 * 
	 * @return true, if successful
	 */
	public boolean download() {
		RandomAccessFile file = null;
		InputStream stream = null;

		try {
			final File newf = new File(path + getFileName(url));
			if (newf.exists()) {
				if (overwrite) {
					newf.delete();
				} else {
					status = ERROR_FILE_EXIST;
					stateChanged();
					return false;
				}
			}
			// Open connection to URL.
			final HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setUseCaches(false);
			// Specify what portion of file to download.
			connection.setRequestProperty("Range", "bytes=" + downloaded + "-");

			// Connect to server.
			connection.connect();

			// Make sure response code is in the 200 range.
			if (connection.getResponseCode() / 100 != 2) {
				error();
			}

			// Check for valid content length.
			final int contentLength = connection.getContentLength();
			if (contentLength < 1) {
				error();
			}

			/*
			 * Set the size for this download if it hasn't been already set.
			 */
			if (size == -1) {
				size = contentLength;
				stateChanged();
			}

			// Open file and seek to the end of it.
			file = new RandomAccessFile(path + getFileName(url) + ".tmp", "rw");
			file.seek(downloaded);

			stream = connection.getInputStream();
			while (status == DOWNLOADING) {
				/*
				 * Size buffer according to how much of the file is left to
				 * download.
				 */
				byte buffer[];
				if (size - downloaded > MAX_BUFFER_SIZE) {
					buffer = new byte[MAX_BUFFER_SIZE];
				} else {
					buffer = new byte[size - downloaded];
				}

				// Read from server into buffer.
				final int read = stream.read(buffer);
				if (read == -1) {
					break;
				}

				// Write buffer to file.
				file.write(buffer, 0, read);
				downloaded += read;
				stateChanged();
			}

			final File f = new File(path + getFileName(url) + ".tmp");

			f.renameTo(newf);
			/*
			 * Change status to complete if this point was reached because
			 * downloading has finished.
			 */
			if (status == DOWNLOADING) {
				status = COMPLETE;
				stateChanged();
			}
		} catch (final Exception e) {
			error();
		} finally {
			// Close file.
			if (file != null) {
				try {
					file.close();
				} catch (final Exception e) {
				}
			}

			// Close connection to server.
			if (stream != null) {
				try {
					stream.close();
				} catch (final Exception e) {
				}
			}
		}
		return status == COMPLETE;
	}

	// Mark this download as having an error.
	/**
	 * Error.
	 */
	private void error() {
		status = ERROR;
		stateChanged();
	}

	// Get file name portion of URL.
	/**
	 * Gets the file name.
	 * 
	 * @param url
	 *            the url
	 * @return the file name
	 */
	private String getFileName(final URL url) {
		final String fileName = url.getFile();
		return fileName.substring(fileName.lastIndexOf('/') + 1);
	}

	/**
	 * Gets the path.
	 * 
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	// Get this download's progress.
	/**
	 * Gets the progress.
	 * 
	 * @return the progress
	 */
	public float getProgress() {
		return ((float) downloaded / size) * 100;
	}

	// Get this download's size.
	/**
	 * Gets the size.
	 * 
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	// Get this download's status.
	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	// Get this download's URL.
	/**
	 * Gets the url.
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return url.toString();
	}

	// Pause this download.
	/**
	 * Pause.
	 */
	public void pause() {
		status = PAUSED;
		stateChanged();
	}

	// Resume this download.
	/**
	 * Resume.
	 */
	public void resume() {
		status = DOWNLOADING;
		stateChanged();
		download();
	}

	/**
	 * Sets the path.
	 * 
	 * @param path
	 *            the new path
	 */
	public void setPath(final String path) {
		this.path = path;
	}

	// Notify observers that this download's status has changed.
	/**
	 * State changed.
	 */
	private void stateChanged() {
		setChanged();
		notifyObservers();
	}
}
