/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.java8;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.adoptajsr.runners.CharacterEncodings;

/**
 *
 * @author richard
 */
public class Java8CharacterEncodings extends CharacterEncodings {

    @Override
    public List<ByteBuffer> encodeAll(List<String> strings) {
        Collection<Charset> charsets = Charset.availableCharsets()
                                              .values();

        return strings.parallelStream()
                      .flatMap(string ->
                          charsets.stream().map(charset ->
                              charset.encode(string)))
                      .collect(Collectors.toList());
    }

}
