/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.java8.prolog;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author richard
 */
public class Term {
    
    private String predicate;
    private List<String> arguments;
    
    public boolean unifiable(Term other) {
        return arguments.size() == other.arguments.size()
            && predicate.equals(other.predicate);
    }

    /**
     * @return the predicate
     */
    public String predicate() {
        return predicate;
    }

    /**
     * @return the arguments
     */
    public Stream<String> arguments() {
        return arguments.stream();
    }

}
