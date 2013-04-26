/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.old;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.adoptajsr.runners.CharacterEncodings;

/**
 *
 * @author richard
 */
public class ImperativeCharacterEncodings extends CharacterEncodings {

    @Override
    public List<ByteBuffer> encodeAll(List<String> strings) {
        List<ByteBuffer> buffers = new ArrayList<>();
        Collection<Charset> charsets = Charset.availableCharsets().values();
        
        for (String str : strings) {
            for (Charset charset : charsets) {
                buffers.add(charset.encode(str));
            }
        }
        
        return buffers;
    }

}
