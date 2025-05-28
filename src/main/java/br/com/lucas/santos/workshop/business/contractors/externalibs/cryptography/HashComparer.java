package br.com.lucas.santos.workshop.business.contractors.externalibs.cryptography;

public interface HashComparer {

    boolean compare(String value, String hash);
}
