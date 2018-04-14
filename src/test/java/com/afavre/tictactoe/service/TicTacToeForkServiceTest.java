package com.afavre.tictactoe.service;

import com.afavre.tictactoe.domain.Box;
import com.afavre.tictactoe.domain.Symbol;
import com.afavre.tictactoe.domain.TicTacToeGame;
import com.afavre.tictactoe.domain.request.NewGameRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class TicTacToeForkServiceTest {

    private List<Box> input;
    private Box expected;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { Arrays.asList(new Box(Symbol.O, 0,0),
                                new Box(Symbol.X, 1,1),
                                new Box(Symbol.X, 0,2),
                                new Box(Symbol.O, 2,2)),
                  new Box(Symbol.O, 2,0) }, // Fork with diagonal blocked
                { Arrays.asList(new Box(Symbol.O, 0,0),
                                new Box(Symbol.X, 1,1),
                                new Box(Symbol.O, 1,2)),
                  new Box(Symbol.O, 0,2) },
        });
    }

    public TicTacToeForkServiceTest(List<Box> input, Box expected) {
        this.input = input;
        this.expected = expected;
    }

    @Test
    public void testNextMoveFork() {
        TicTacToeService ticTacToeService = new TicTacToeService();
        TicTacToeGame newGame = ticTacToeService.createNewGame(new NewGameRequest("Test",
                                                                                  Symbol.O));

        for (Box box : this.input)
            newGame.getGrid().putBox(box.getSymbol(), box.getX(),box.getY());

        Optional<Box> box = ticTacToeService.strategyFork(newGame, Symbol.O);
        assertTrue(box.isPresent());
        assertEquals(this.expected, box.get());
    }

}
