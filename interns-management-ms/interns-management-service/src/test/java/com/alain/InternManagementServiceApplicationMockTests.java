package com.alain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class InternManagementServiceApplicationMockTests {
    @Mock
    Calculator calculator;

    @InjectMocks
    CalculatorUser calculatorUser = new CalculatorUser();

    @BeforeEach
    void setMockOutput() {
        when(calculator.addTwoNumbers(anyInt(),anyInt())).thenReturn(505);
    }
;
    @Test
    void itShouldAddTwoNumbers() {
        //given
        int x = 15;
        int y = 35;

        //when
        int result = calculatorUser.add(x,y);

        //then
        int expected = 50;
        assertThat(result).isNotEqualTo(expected);
    }

    class Calculator{
        public int addTwoNumbers(int x, int y){
            return x + y;
        }
    }

    class CalculatorUser{
        @Autowired
        Calculator calculator;
        public int add(int x, int y){
            return this.calculator.addTwoNumbers(x,y);
        }
    }

}
