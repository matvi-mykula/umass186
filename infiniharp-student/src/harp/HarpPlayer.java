/*
 * Copyright 2021 Marc Liberatore.
 */
package harp;

import standard.StdAudio;
import standard.StdDraw;

/**
 * 
 * A driver program for the HarpString class; it turns some of the keys of your 
 * keyboard into a sythesizer that sounds something like a harp.
 * 
 * The `qwerty` row and the `zxcvbn` row correspond to the white keys on a piano;
 * the keys above them are the black keys.
 * 
 * @author liberato
 *
 */
public class HarpPlayer {

	public static void main(String[] args) {
		final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
		HarpString[] strings = new HarpString[keyboard.length()];

		// Create 37 strings, one for each key in the `keyboard`
		for (int i = 0; i < strings.length; i++) {
			strings[i] = new HarpString(440 * Math.pow(1.05956, i - 24));
		}

		// the main input loop
		while (true) {

			// check if the user has typed a key, and, if so, process it
			if (StdDraw.hasNextKeyTyped()) {

				// the user types this character
				char key = StdDraw.nextKeyTyped();

				// pluck the corresponding string, if there is one
				final int index = keyboard.indexOf(key);
				if (index != -1) {
					strings[index].pluck();
				}
			}

			// compute the superposition of the samples
			double sample = 0.0;
			for (int i = 0; i < strings.length; i++) {
				sample += strings[i].sample();
			}

			// send the result to standard audio
			StdAudio.play(sample);

			// advance the simulation of each harp string by one step
			for (int i = 0; i < strings.length; i++) {
				strings[i].tic();
			}
		}
	}

}