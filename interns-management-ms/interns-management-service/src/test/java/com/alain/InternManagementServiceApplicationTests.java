package com.alain;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class InternManagementServiceApplicationTests {
    private Calculator calculator;
    CalculatorUser calculatorUser;

    @BeforeEach
    public void init(){
        calculator = new Calculator();
        calculatorUser = new CalculatorUser(calculator);
    }

    @Test
    void itShouldAddTwoNumbers() {
        //given
        int x = 15;
        int y = 35;

        //when
        int result = calculatorUser.add(x,y);

        //then
        int expected = 50;
        assertThat(result).isEqualTo(expected);
    }

    class Calculator{
        public int addTwoNumbers(int x, int y){
            return x + y;
        }
    }

    @RequiredArgsConstructor
    class CalculatorUser{
        final Calculator calculator;

        public int add(int x, int y){
            return this.calculator.addTwoNumbers(x,y);
        }
    }

}
