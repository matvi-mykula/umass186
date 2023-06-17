/*
 * Copyright 2021 Marc Liberatore.
 */

package mosaic;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import images.ImageUtils;

public class TileFactory {
	public final int tileWidth;
	public final int tileHeight;
	// TODO: you will NOT be keeping this array in your final code;
	// see assignment description for details
	private final Map<Integer, List<BufferedImage>> hueImageMap;

	/**
	 * 
	 * @param colors     the palette of RGB colors for this TileFactory
	 * @param tileWidth  width (in pixels) for each tile
	 * @param tileHeight height (in pixels) for each tile
	 */
	public TileFactory(int[] colors, int tileWidth, int tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		// TODO: when you replace the int[] hues, be sure to replace this code, as well
		this.hueImageMap = new HashMap<>();
		initializeHueImageMap(colors);

		// new int[colors.length];
		// for (int i = 0; i < hues.length; i++) {
		// hues[i] = Math.round(ImageUtils.hue(colors[i]));
		// }
	}

	private void initializeHueImageMap(int[] colors) {
		// initialize key for each color
		for (int color : colors) {
			int hue = ImageUtils.hue(color);
			List<BufferedImage> images = new ArrayList<>();
			this.hueImageMap.put(hue, images);

		}
	}

	/**
	 * Returns the distance between two hues on the circle [0,256).
	 * 
	 * @param hue1
	 * @param hue2
	 * @return the distance between two hues.
	 */
	// hue is value 0 to 255 but the numbers wrap around like 1-12 on a clock
	static int hueDistance(int hue1, int hue2) {
		// calc distance in both directions
		// distance within range, max - min
		int withinRange = Math.abs(hue1 - hue2);
		// min to 0 and max to 255
		int min = Math.min(hue1, hue2);
		int max = Math.max(hue1, hue2);
		int outOfRange = min + (256 - max); // 256 to accoutn for zero
		// return the greater of these
		return Math.min(withinRange, outOfRange);

	}

	/**
	 * Returns the closest hue from the fixed palette this TileFactory contains.
	 * 
	 * @param hue
	 * @return the closest hue from the palette
	 */
	Integer closestHue(int hue) {
		/// get list of keys in map
		List<Integer> keys = new ArrayList<>(hueImageMap.keySet());

		int smallest = 1000; // so big that the first hue distance will overwrite it
		int closest = 0; // to be overwritten
		// iterate and keep track of smalles huedistance
		for (Integer hue2 : keys) {
			if (hueDistance(hue, hue2) < smallest) {
				smallest = hueDistance(hue, hue2);
				closest = hue2;
			}
		}
		// return smallest
		// TODO
		return closest;
	}

	/**
	 * Adds an image to this TileFactory for later use.
	 * 
	 * @param image the image to add
	 */
	public void addImage(BufferedImage image) {
		image = ImageUtils.resize(image, tileWidth, tileHeight);

		int avgHue = ImageUtils.averageHue(image);

		List<BufferedImage> list = hueImageMap.get(avgHue);
		// TODO: add the image to the appropriate place in your map from hues to lists
		// of images
		if (list.size() == 0 || list == null) {
			list = new ArrayList<>(Arrays.asList(image));
		} else {
			list.add(image);
		}
	}

	/**
	 * Returns the next tile from the list associated with the hue most closely
	 * matching the input hue.
	 * 
	 * The returned values should cycle through the list. Each time this method is
	 * called, the next
	 * tile in the list will be returned; when the end of the list is reached, the
	 * cycle starts over.
	 * 
	 * @param hue the color to match
	 * @return a tile matching hue
	 */
	public BufferedImage getTile(int hue) {
		// TODO: return an appropriate image from your map of hues to lists of images;
		// see assignment description for details

		// get list of images by the hue key
		List<BufferedImage> list = hueImageMap.get(hue);
		if (list.size() == 0) {
			return null;
		}
		// pull out index 0
		BufferedImage nextImage = list.get(0);
		list.remove(0);
		list.add(nextImage);
		// then shift list
		// return index 0

		return nextImage;
	}
}
