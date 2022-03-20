package ru.liga.database;

import junit.framework.TestCase;
import org.junit.Test;
import ru.liga.parser.DateAndCurrencies;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DAOCurrencyTest extends TestCase {

    @Test
    public void testGetInfoIncorrectCurrency() {
        assertThatThrownBy(() -> new DAOCurrency().getInfo("EEE")).isExactlyInstanceOf(FileNotFoundException.class);
    }

    @Test
    public void testGetInfoEmptyArgument() {
        assertThatThrownBy(() -> new DAOCurrency().getInfo("")).isExactlyInstanceOf(FileNotFoundException.class);
    }

    @Test
    public void testGetInfoEUR() throws FileNotFoundException {
        assertThat(new DAOCurrency().getInfo("EUR")).isExactlyInstanceOf(DateAndCurrencies.class);
    }

    @Test
    public void testGetInfoTRY() throws FileNotFoundException {
        assertThat(new DAOCurrency().getInfo("TRY")).isExactlyInstanceOf(DateAndCurrencies.class);
    }

    @Test
    public void testGetInfoUSD() throws FileNotFoundException {
        assertThat(new DAOCurrency().getInfo("USD")).isExactlyInstanceOf(DateAndCurrencies.class);
    }

    @Test
    public void testGetInfoAMD() throws FileNotFoundException {
        assertThat(new DAOCurrency().getInfo("AMD")).isExactlyInstanceOf(DateAndCurrencies.class);
    }

    @Test
    public void testGetInfoBGN() throws FileNotFoundException {
        assertThat(new DAOCurrency().getInfo("BGN")).isExactlyInstanceOf(DateAndCurrencies.class);
    }
}