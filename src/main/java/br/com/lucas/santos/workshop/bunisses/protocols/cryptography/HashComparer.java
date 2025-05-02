package br.com.lucas.santos.workshop.bunisses.protocols.cryptography;

public interface HashComparer {

    boolean compare(String value, String hash);
}
