package br.com.lucas.santos.workshop.business.contractors.externallibs.cryptography;

public interface HashComparer {

    boolean compare(String value, String hash);
}
