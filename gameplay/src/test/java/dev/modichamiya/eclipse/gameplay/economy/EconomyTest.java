package dev.modichamiya.eclipse.gameplay.economy;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class EconomyTest {@Test void transfersAtomicallyAndMatchesOrders(){LedgerService ledger=new LedgerService();UUID a=UUID.randomUUID(),b=UUID.randomUUID();ledger.mint(a,"gold",100);ledger.transfer(a,b,"gold",40);assertEquals(60,ledger.balance(a,"gold"));assertEquals(40,ledger.balance(b,"gold"));OrderBook book=new OrderBook();assertTrue(book.place(new OrderBook.Order(a,OrderBook.Side.BUY,10,5,Instant.EPOCH)).isEmpty());var fills=book.place(new OrderBook.Order(b,OrderBook.Side.SELL,9,3,Instant.EPOCH));assertEquals(3,fills.getFirst().quantity());}}
