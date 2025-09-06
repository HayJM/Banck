package br.com.dio.model;

public record Investment(
    long id,
    long tax,
    long initialFounds) 
    {
        @Override
        public String toString() {
            return "Investment{" +
                    "id=" + id +
                    ", tax=" + tax + "% " +
                    ", intialFunds=" + (initialFounds/100) + "," + (initialFounds %100) +
                    '}';
        }
                
    }
