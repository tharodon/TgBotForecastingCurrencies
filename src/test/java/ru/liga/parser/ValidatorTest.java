package ru.liga.parser;

import junit.framework.TestCase;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValidatorTest extends TestCase {

    @Test
    public void testIsAllOkayAlgorithmOkay() throws Exception {
        Validator valid = new Validator("rate EUR -date 22.04.2022 -alg actual");
        boolean result = valid.isAllOkay();
        assertTrue(result);
    }

    @Test
    public void testIsAllOkayAlgorithmNotValid() {
        assertThatThrownBy(() -> new Validator("rate EUR -date 22.04.2022 -alg").isAllOkay()).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testIsAllOkayDate() {
        assertTrue(new Validator("rate EUR -date 22.04.2022 -alg actual").isAllOkay());
    }

    @Test
    public void testIsAllOkayDateTomorrow() {
        assertTrue(new Validator("rate EUR -date tomorrow -alg actual").isAllOkay());
    }

    @Test
    public void testIsAllOkayDateNotValid() {
        assertThatThrownBy(() -> new Validator("rate EUR -date no_date -alg actual").isAllOkay()).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testIsGraphTrue() {
        assertTrue(new Validator("rate EUR -date tomorrow -alg actual -output graph").isGraph());
    }

    @Test
    public void testIsGraphFalse() {
        assertFalse(new Validator("rate EUR -date tomorrow -alg actual").isGraph());
    }
}
