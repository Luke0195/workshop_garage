package br.com.lucas.santos.workshop.factories;

import net.datafaker.Faker;

public class FakerFactory {

    private FakerFactory(){}


    public static Faker makeFaker(){
        return new Faker();
    }

}
