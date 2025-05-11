package br.com.lucas.santos.workshop.bunisses.contractors.externalibs.cryptography;

public interface HashComparer {

    boolean compare(String value, String hash);
}
