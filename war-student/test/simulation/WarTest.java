/*
 * Copyright 2021 Marc Liberatore.
 */

package simulation;

import static org.junit.Assert.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class WarTest {
	// @Rule
	// public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds

	@Test
	public void testCreateWar() {
		War war = new War();
		war.CreateWar();

		// Check if battleCount is initialized to 0
		assertEquals(Integer.valueOf(0), war.battleCount);

		// Check if Player1 and Player2 are instantiated as empty ArrayDeques
		assertNotNull(war.Player1);
		assertTrue(war.Player1.isEmpty());
		assertNotNull(war.Player2);
		assertTrue(war.Player2.isEmpty());
	}

	@Test
	public void testDealCards() {
		War war = new War();
		List<Integer> deck = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		war.CreateWar();
		war.DealCards(deck);

		ArrayDeque<Integer> player1 = war.Player1;
		ArrayDeque<Integer> player2 = war.Player2;

		// Test the size of each player's hand
		assert player1.size() == 5;
		assert player2.size() == 5;

		// Test the contents of player1's hand
		assert player1.contains(1);
		assert player1.contains(3);
		assert player1.contains(5);
		assert player1.contains(7);
		assert player1.contains(9);

		// Test the contents of player2's hand
		assert player2.contains(2);
		assert player2.contains(4);
		assert player2.contains(6);
		assert player2.contains(8);
		assert player2.contains(10);
	}

	@Test
	public void testDealCardsEmptyDeck() {
		War war = new War();
		List<Integer> deck = Arrays.asList();
		war.CreateWar();
		war.DealCards(deck);

		// Both players should have an empty hand
		assert war.Player1.isEmpty();
		assert war.Player2.isEmpty();
	}

	@Test
	public void testDealCardsOddDeckSize() {
		War war = new War();
		war.CreateWar();
		List<Integer> deck = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

		war.DealCards(deck);

		ArrayDeque<Integer> player1 = war.Player1;
		ArrayDeque<Integer> player2 = war.Player2;

		// Test the size of each player's hand
		assert player1.size() == 4;
		assert player2.size() == 3;

		// Test the contents of player1's hand
		assert player1.contains(1);
		assert player1.contains(3);
		assert player1.contains(5);
		assert player1.contains(7);

		// Test the contents of player2's hand
		assert player2.contains(2);
		assert player2.contains(4);
		assert player2.contains(6);
	}

	@Test
	public void testDealCardsDuplicateCards() {
		War war = new War();
		war.CreateWar();
		List<Integer> deck = Arrays.asList(1, 2, 3, 3, 4, 4, 5, 5);

		war.DealCards(deck);

		ArrayDeque<Integer> player1 = war.Player1;
		ArrayDeque<Integer> player2 = war.Player2;

		// Test the size of each player's hand
		assert player1.size() == 4;
		assert player2.size() == 4;

		// Test the contents of player1's hand
		assert player1.contains(1);
		assert player1.contains(3);
		assert player1.contains(4);
		assert player1.contains(5);

		// Test the contents of player2's hand
		assert player2.contains(2);
		assert player2.contains(3);
		assert player2.contains(4);
		assert player2.contains(5);
	}

	@Test
	public void testDealCardsLargeDeck() {
		War war = new War();
		war.CreateWar();
		List<Integer> deck = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

		war.DealCards(deck);

		ArrayDeque<Integer> player1 = war.Player1;
		ArrayDeque<Integer> player2 = war.Player2;

		// Test the size of each player's hand
		assert player1.size() == 8;
		assert player2.size() == 7;

		// Test the contents of player1's hand
		assert player1.contains(1);
		assert player1.contains(3);
		assert player1.contains(5);
		assert player1.contains(7);
		assert player1.contains(9);
		assert player1.contains(11);
		assert player1.contains(13);
		assert player1.contains(15);

		// Test the contents of player2's hand
		assert player2.contains(2);
		assert player2.contains(4);
		assert player2.contains(6);
		assert player2.contains(8);
		assert player2.contains(10);
		assert player2.contains(12);
		assert player2.contains(14);
	}

	@Test
	public void testCheckIfWinnerNoCardsLeft() {
		War war = new War();
		war.CreateWar();
		war.Player1.clear();
		war.Player2.clear();
		war.battleCount = 10;

		assertTrue(war.CheckIfWinner() == (0));
	}

	@Test
	public void testCheckIfWinnerPlayer1Wins() {
		War war = new War();
		war.CreateWar();
		war.Player1.clear();
		war.Player2.add(1);
		war.battleCount = 5;

		assertTrue(-1 == war.CheckIfWinner());
	}

	@Test
	public void testCheckIfWinnerPlayer2Wins() {
		War war = new War();
		war.CreateWar();
		war.Player1.add(1);
		war.Player2.clear();
		war.battleCount = 5;

		assertTrue(1 == war.CheckIfWinner());
	}

	@Test
	public void testCheckIfWinnerBattleCountReached() {
		War war = new War();
		war.CreateWar();
		war.Player1.add(1);
		war.Player2.add(2);
		war.battleCount = 1000;

		assertTrue(0 == war.CheckIfWinner());
	}

	@Test
	public void testCheckIfWinnerContinueGame() {
		War war = new War();
		war.CreateWar();
		war.Player1.add(1);
		war.Player2.add(2);
		war.battleCount = 5;

		assertTrue(2 == war.CheckIfWinner());
	}

	@Test
	public void testBattlePlayer1Wins() {
		War war = new War();
		war.CreateWar();
		war.Player1.addAll(Arrays.asList(5, 8, 3));
		war.Player2.addAll(Arrays.asList(2, 4, 1));
		List<Integer> spoils = new ArrayList<>();

		assertTrue(1 == war.Battle(spoils));
		assertTrue(war.Player1.containsAll(Arrays.asList(5, 8, 3, 2, 4, 1)));
		assertTrue(war.Player2.containsAll(Arrays.asList()));
	}

	@Test
	public void testBattlePlayer2Wins() {
		War war = new War();
		war.CreateWar();
		war.Player1.addAll(Arrays.asList(7, 2, 6));
		war.Player2.addAll(Arrays.asList(9, 4, 8));
		List<Integer> spoils = new ArrayList<>();

		assertTrue(-1 == war.Battle(spoils));
		assertTrue(war.Player1.containsAll(Arrays.asList()));
		assertTrue(war.Player2.containsAll(Arrays.asList(9, 4, 8, 7, 2, 6)));
	}

	@Test
	public void testBattleTie() {
		War war = new War();
		war.CreateWar();
		war.Player1.addAll(Arrays.asList(5, 8, 3));
		war.Player2.addAll(Arrays.asList(5, 8, 1));
		List<Integer> spoils = new ArrayList<>();

		assertTrue(0 == war.Battle(spoils));
		assertTrue(war.Player1.isEmpty());
		assertTrue(war.Player2.isEmpty());
	}

	@Test
	public void testExample() {
		War war = new War();
		war.CreateWar();
		war.Player1.addAll(Arrays.asList(2, 8, 9, 10, 5, 4));
		war.Player2.addAll(Arrays.asList(2, 6, 7, 11, 6, 4));
		List<Integer> spoils = new ArrayList<>();

		assertTrue(-1 == war.Battle(spoils));
		assertTrue(war.Player1.isEmpty());
		System.out.println(war.Player2);
		assertTrue(war.Player2.containsAll(Arrays.asList(9, 10, 6, 7, 11, 5, 6)));
	}

	@Test
	public void testExample2() {
		War war = new War();
		war.CreateWar();
		war.Player1.addAll(Arrays.asList(5, 2, 3));
		war.Player2.addAll(Arrays.asList(5, 4));
		List<Integer> spoils = new ArrayList<>();

		assertTrue(0 == war.Battle(spoils));
		assertTrue(war.Player1.isEmpty());
		System.out.println(war.Player2);
		assertTrue(war.Player2.isEmpty());
	}

	@Test
	public void testEmpty() {
		assertEquals(0, War.findWinner(new ArrayList<Integer>()));
	}

	@Test
	public void testOne() {
		List<Integer> deck = Arrays.asList(2);
		assertEquals(1, War.findWinner(deck));
	}

	@Test
	public void testTwoA() {
		List<Integer> deck = Arrays.asList(3, 2);
		assertEquals(1, War.findWinner(deck));
	}

	@Test
	public void testTwoB() {
		List<Integer> deck = Arrays.asList(2, 3);
		assertEquals(-1, War.findWinner(deck));
	}

}
