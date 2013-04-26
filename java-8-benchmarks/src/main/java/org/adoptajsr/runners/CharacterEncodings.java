/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.runners;

import java.nio.ByteBuffer;
import java.util.List;

/**
 *
 * @author richard
 */
public abstract class CharacterEncodings {
    
    public abstract List<ByteBuffer> encodeAll(List<String> strings);

}
