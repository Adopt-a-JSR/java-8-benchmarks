/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.java8.prolog;

import java.util.Map;
import java.util.stream.Streams;

/**
 *
 * @author richard
 */
public class Java8Prolog {

    public boolean unify(Term source, Map<String, String> sourceEnv,
                         Term dest, Map<String, String> destEnv) {

        if (!source.unifiable(dest))
            return false;

        Streams.zip(source.arguments(),
                    dest.arguments(),
                    (s, d) -> unifyArgs(s, d, sourceEnv));

        return true;
    }

    private int unifyArgs(String srcArg, String destArg, Map<String, String> sourceEnv) {
        String srcVal = isVariable(srcArg) ? sourceEnv.get(srcArg)
                                           : srcArg;
        return 0;
    }

    private boolean isVariable(String atom) {
        return atom.chars()
                   .mapToObj(Character::isUpperCase)
                   .reduce(true, Boolean::logicalAnd);
    }

}
