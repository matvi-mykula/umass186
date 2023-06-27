/*
 * Copyright 2021 Marc Liberatore.
 */

package simulation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 
 * A Java class to simulate the card game War. See assignment writeup for
 * details.
 * 
 * @author liberato
 *
 */
public class War {
	// battle count

	Integer battleCount;
	// instantiate players
	ArrayDeque<Integer> Player1;
	ArrayDeque<Integer> Player2;

	// create 2 objects with key as playername and value as the hand theyve been
	// dealt as a queue
	public void CreateWar() {
		this.battleCount = 0;
		this.Player1 = new ArrayDeque<>();
		this.Player2 = new ArrayDeque<>();
	}

	// deal out cards
	// looks liek first cards dealt out are first cards in hand
	// first in first out
	public void DealCards(List<Integer> deck) {

		for (int i = 0; i < deck.size(); i++) {
			if (i % 2 == 0) {
				this.Player1.add(deck.get(i));
			} else {
				this.Player2.add(deck.get(i));
			}

		}
	}

	public Integer CheckIfWinner() {

		if (this.Player1.size() == 0 && this.Player2.size() == 0) {
			return 0;
		} else if (this.Player1.size() <= 0) {
			return -1;
		} else if (this.Player2.size() <= 0) {
			return 1;
		} else if (this.battleCount >= 1000) {
			return 0;
		}
		// if return 2 then continue with game
		return 2;
	}

	public List<Integer> add3IfTie() {
		List<Integer> spoils = new ArrayList<>();
		for (ArrayDeque<Integer> player : Arrays.asList(Player1, Player2)) {
			// List<Integer> removedIntegers = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				Integer element = player.pollFirst();
				if (element != null) {
					spoils.add(element);
				}

			}
		}
		return spoils;
	}

	// play hand// battle

	public Integer Battle(List<Integer> spoils) {

		// play top card what happens
		// whoever has greater card wins
		if (CheckIfWinner() != 2) {
			return CheckIfWinner();
		}

		Integer player1card = this.Player1.poll();
		Integer player2card = this.Player2.poll();

		if (player1card > player2card) {
			this.Player1.addAll(spoils);
			this.Player1.add(player1card);
			this.Player1.add(player2card);
			return Battle(new ArrayList<>());
		} else if (player1card < player2card) {
			this.Player2.addAll(spoils);
			this.Player2.add(player1card);
			this.Player2.add(player2card);
			return Battle(new ArrayList<>());
		} else if (player1card == player2card) {

			// exactly how are spoils added

			spoils.add(player1card);
			spoils.add(player2card);
			spoils.addAll(add3IfTie());

			return Battle(spoils);
		}

		return CheckIfWinner();

	}

	public Integer simulate(List<Integer> deck) {
		if (deck.size() == 0) {
			return 0;
		}

		// insantiate game and deal cards
		CreateWar();
		DealCards(deck);
		// run battle
		return Battle(new ArrayList<>());
	}

	/**
	 * Determines the winner of a game of War, returning 1 if player A wins, -1 if
	 * player B wins, 0 if a draw.
	 * 
	 * The rules of the game are defined in the assignment writeup.
	 * 
	 * @param deck
	 * @return 1 if player A wins, -1 if player B wins, 0 if a draw
	 */
	public static int findWinner(List<Integer> deck) {

		War war = new War();
		return war.simulate(deck);
	}
}
